/**
 * JavaFX popup window showing useful messages to the user.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessagePopup extends Stage{
	private String firstLine, secondLine, title;
	private Stage parentStage;

	public MessagePopup(Stage parentStage, String title, String firstLine, String secondLine)
	{
		this.title = title;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.parentStage = parentStage;
		
		initialize();
	}
	
	private void initialize()
	{
		initModality(Modality.WINDOW_MODAL);
		initOwner(parentStage);
		setTitle(title);
		
		FXMLLoader fxmlLoader = null;
    	fxmlLoader = new FXMLLoader(getClass().getResource("messagePopup.fxml"));
    	
    	fxmlLoader.setController(new MessageController(this, firstLine, secondLine));
    	try 
    	{
    		Pane mainPane = (Pane)fxmlLoader.load();
    		
    		Scene scene = new Scene(mainPane);
    		mainPane.getStylesheets().add(MessagePopup.class.getClassLoader().getResource("resources/messagePopupStyle.css").toExternalForm());
    		setScene(scene);
    	}
    	catch(Exception e) 
    	{
    		e.printStackTrace();
    	}		
	}
	
	public void display()
	{
		show();
	}
}
