package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage; 

public class Main extends Application {

	SceneController controller;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		controller = new SceneController();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
