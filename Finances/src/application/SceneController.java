package application;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import analyzer.FileProcessor;
import analyzer.Record;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Menu menuFile;
	private MenuBar menuBar;
	private MenuItem menuItemImport;
	private BorderPane root;

	SceneController() {
		menuBar = new MenuBar();
		menuItemImport = new MenuItem("Import");
		menuFile = new Menu("File");
		menuItemImport.setOnAction(e -> {
			browse();
		});
		menuFile.getItems().add(menuItemImport);
		menuBar.getMenus().add(menuFile);

		root = new BorderPane();
		root.setTop(menuBar);

		Color c = Color.rgb(74, 21, 21);
		root.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
		scene = new Scene(root, 1000, 700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		createDefaultScene();
	}

	public void createDefaultScene() {
		VBox box = new VBox();
		Button lineChartButton = new Button("Line Chart");
		Button barChartButton = new Button("Bar Chart");
		// action event
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Handle Event
				System.out.println("Button Clicked");
			}
		};
		lineChartButton.setOnAction(event);
		barChartButton.setOnAction(event);
		lineChartButton.setTranslateX(15);
		lineChartButton.setTranslateY(30);
		barChartButton.setTranslateX(15);
		barChartButton.setTranslateY(50);
		box.getChildren().add(lineChartButton);
		box.getChildren().add(barChartButton);
		box.setFillWidth(true);
		box.setMinWidth(100);

		root.setLeft(box);
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
		TimeSeries series = populateLineChart();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Transactions", // title
				"Date", // x-axis label
				"Amount", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
		);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setDefaultShapesVisible(true);
			renderer.setDefaultShapesFilled(true);
		}

		ChartPanel panel = new ChartPanel(chart);
		SwingNode node = new SwingNode();
		try {
			SwingUtilities.invokeAndWait(() -> {
				// hangs inside here...
				node.setContent(panel);
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		root.setCenter(node);
	}

	TimeSeries populateLineChart() {
		TimeSeries series = new TimeSeries("Data");
		// populating the series with data
		ArrayList<Record> records = FileProcessor.getTransactions();

		for (int i = 0; i < records.size(); i++) {
			Record record = records.get(i);
			Date date = record.getDate();
			float value = record.getValue();
			Number n = series.getValue(new Day(date));
			if (n != null) {
				value += n.floatValue();
			}
			if (value >= 0)
				series.addOrUpdate(new Day(date), value);
		}
		return series;
	}
}