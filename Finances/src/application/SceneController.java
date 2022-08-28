package application;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import analyzer.FileProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneController {

 private Stage stage;
 private Scene scene;
 private Menu menu;
 private MenuBar menuBar;
 private BorderPane root;
 
 void SceneController() {
	 MenuItem menuItem1 = new MenuItem("Import");
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
	 stage.setScene(scene);
	 stage.show();
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
	    //createLineChart();
	}
 
	
 public void switchToScene1(ActionEvent event) throws IOException {
  root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
 
 public void switchToScene2(ActionEvent event) throws IOException {
  Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
}