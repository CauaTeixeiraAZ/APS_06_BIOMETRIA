package com.unip.biometria.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class mainFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterScene.fxml"));
			HBox root = loader.load();
			
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e){
			System.err.println(e);
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
