package com.donlaiq.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageRestart extends Stage{
	
	
	
	private Stage parentStage;
	private MessageRestartController messageRestartController;
	
	public MessageRestart(Stage parentStage)
	{
		this.parentStage = parentStage;
		initialize();
	}
	
	
	private void initialize()
	{
		initModality(Modality.WINDOW_MODAL);
		initOwner(parentStage);
		setTitle("Importing addresses...");
		
		FXMLLoader fxmlLoader = null;
    	fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/messagePopup.fxml"));
    	
    	messageRestartController = new MessageRestartController(this);
    	fxmlLoader.setController(messageRestartController);
    	try 
    	{
    		Pane mainPane = (Pane)fxmlLoader.load();
    		
    		Scene scene = new Scene(mainPane);
    		mainPane.getStylesheets().add(MessageRestart.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
    		setScene(scene);
    	}
    	catch(Exception e) 
    	{
    		e.printStackTrace();
    	}		
	}
	
	
	public void setRescanDone()
	{
		messageRestartController.setRescanDone();
	}
}
