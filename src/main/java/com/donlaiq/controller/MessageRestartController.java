package com.donlaiq.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MessageRestartController {
	
	@FXML
	private Label label1, label2;
	
	@FXML
	private Button okButton;
	
	private Stage stage;



	public MessageRestartController(Stage stage)
	{
		this.stage = stage;
	}
	
	
	@FXML
	private void initialize()
	{
		okButton.setDisable(true);
		label1.setText("Please wait for a few minutes");
		label2.setText("Rescaning blockchain...");

	}
	
	
	public void setRescanDone()
	{
		okButton.setDisable(false);
		label1.setText("The addresses were!");
		label2.setText("import successfully!");
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
