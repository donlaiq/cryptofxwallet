/**
 * JavaFX controller for the popup window showing useful messages to the user.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MessageController {
	
	private Stage stage;
	private String firstLabel, secondLabel;
	
	@FXML
	private Label label1, label2;
	
	MessageController(Stage stage, String firstLabel, String secondLabel)
	{
		this.stage = stage;
		this.firstLabel = firstLabel;
		this.secondLabel = secondLabel;
	}
	
	
	/*
	 * Set the message using two labels in a stack
	 */
	@FXML
	protected void initialize()
	{
		label1.setText(firstLabel);
		label2.setText(secondLabel);
	}
	

	/*
	 * After press the Ok button
	 */
	@FXML
	public void closeAlert(ActionEvent event)
	{
		stage.close();
		WalletController.isMessageShown = false;
	}

}
