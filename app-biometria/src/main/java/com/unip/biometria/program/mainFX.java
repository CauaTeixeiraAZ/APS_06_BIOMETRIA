package com.unip.biometria.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class mainFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
		}
		catch(RuntimeException e){
			System.err.println(e);
		}
	}

	
	public static void main(String[] args) {

		launch(args);
		
		
	}

}