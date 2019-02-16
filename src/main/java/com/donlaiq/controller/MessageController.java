/**
 * JavaFX controller for the popup window showing useful messages to the user.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MessageController {
	
	private Stage stage;
	private String firstLabel, secondLabel;
	
	private Lighting lighting;
	
	@FXML
	private Label label1, label2;
	
	@FXML 
	private Button okButton;
	
	MessageController(Stage stage, String firstLabel, String secondLabel)
	{
		this.stage = stage;
		this.firstLabel = firstLabel;
		this.secondLabel = secondLabel;
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-90.0);
		light.setColor(new Color(0.95, 0.95, 0.95, 1));
		
		lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(2.0);
		
	}
	
	
	/*
	 * Set the message using two labels in a stack
	 */
	@FXML
	protected void initialize()
	{
		label1.setText(firstLabel);
		label2.setText(secondLabel);
		
		okButton.setEffect(lighting);
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
