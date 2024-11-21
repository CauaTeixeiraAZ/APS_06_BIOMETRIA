package com.unip.biometria.program;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainFX extends Application {
	
	String path1 = "/views/LoginScene.fxml";
	String path2 = "/views/RegisterScene.fxml";
	String path3 = "/views/DigitalScene.fxml";
	String path4 = "/views/MainScene.fxml";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(path1));
			
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
