package com.donlaiq.main;

import com.donlaiq.controller.StartController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SetupScreen extends Stage{
	private Stage primaryStage;
	
	public SetupScreen(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	
	
	public void display()
	{

		try {
			
			StartController startController = new StartController(primaryStage);
			FXMLLoader fxmlLoaderGeneral = new FXMLLoader();
			fxmlLoaderGeneral.setLocation(getClass().getResource("/resources/fxml/start.fxml"));
			fxmlLoaderGeneral.setController(startController);
	        Pane paneGeneral = (Pane)fxmlLoaderGeneral.load();
	
	        Scene sceneGeneral = new Scene(paneGeneral);
	        sceneGeneral.getStylesheets().add(SetupScreen.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
	        
	        primaryStage.setTitle("Crypto FX Wallet");
	        primaryStage.setScene(sceneGeneral);
	        primaryStage.show();
	        
	       
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
