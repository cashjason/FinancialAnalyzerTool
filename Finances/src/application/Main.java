package application;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import analyzer.FileProcessor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	Menu menu;
	MenuItem menuItem1;
	Scene scene;
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			menu = new Menu("File");
			menuItem1 = new MenuItem("Import");
			menuItem1.setOnAction(e -> {
			    browse();
			});
			menu.getItems().add(menuItem1);
			MenuBar menuBar = new MenuBar();
			menuBar.getMenus().add(menu);

			BorderPane root = new BorderPane();
			root.setTop(menuBar);
			scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void browse() {
		FileChooser fileChooser = new FileChooser();
	    File selectedFile = fileChooser.showOpenDialog(null);
	    try {
			FileProcessor processor = new FileProcessor();
			processor.processFile(selectedFile);
			processor.printRecords();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    createLineChart();
	}
	
	public void createLineChart() {
		//Defining X axis  
		NumberAxis xAxis = new NumberAxis(1960, 2020, 10); 
		xAxis.setLabel("Months"); 
		        
		//Defining y axis 
		NumberAxis yAxis = new NumberAxis(0, 350, 50); 
		yAxis.setLabel("No.of transactions");
		
		LineChart linechart = new LineChart(xAxis, yAxis);
		
		XYChart.Series series = new XYChart.Series(); 
		series.setName("No of transactions per month"); 
		
		series.getData().add(new XYChart.Data(1970, 15)); 
		series.getData().add(new XYChart.Data(1980, 30)); 
		series.getData().add(new XYChart.Data(1990, 60)); 
		series.getData().add(new XYChart.Data(2000, 120)); 
		series.getData().add(new XYChart.Data(2013, 240)); 
		series.getData().add(new XYChart.Data(2014, 300));
		
		//Setting the data to Line chart    
		linechart.getData().add(series);
		Group root = new Group(linechart);
		//Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root ,1000, 700);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
