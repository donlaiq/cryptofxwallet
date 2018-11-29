/**
 * JavaFX popup window asking to the user to confirm the transaction of coins.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;

import java.util.Properties;

import com.donlaiq.command.ProcessHandlerWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SendMoneyController {

	private Stage parentStage, grandparentStage;
	private Properties popupProperties;
	private String addressFrom, addressTo, amount;

	@FXML
	private Label label1, label2;
	
	public SendMoneyController(Stage parentStage, Properties popupProperties, String addressFrom, String addressTo, String amount)
	{
		this.parentStage = parentStage;
		this.popupProperties = popupProperties;
		this.addressFrom = addressFrom;
		this.addressTo = addressTo;
		this.amount = amount;
	}
	
	@FXML
	protected void initialize()
	{
		label1.setText(popupProperties.getProperty("popup.send.money.first.line").replace("${coin.code}", amount + " " + popupProperties.getProperty("coin.code")));
		label2.setText(addressTo);
	}
	
	/*
	 * The user proceeds with the transaction.
	 */
	@FXML
	public void doAction(ActionEvent event)
	{
		ProcessHandlerWrapper processHandlerWrapper = new ProcessHandlerWrapper();
		processHandlerWrapper.sendMoney(addressFrom, addressTo, amount);
		parentStage.close();
		
		MessagePopup mp = new MessagePopup(grandparentStage, popupProperties.getProperty("popup.money.sent.title"), popupProperties.getProperty("popup.money.sent.first.line"), popupProperties.getProperty("popup.money.sent.second.line"));
		mp.display();
	}

	/*
	 * The user cancels the transaction.
	 */
	@FXML
	public void cancel(ActionEvent event)
	{
		parentStage.close();
	}

}
