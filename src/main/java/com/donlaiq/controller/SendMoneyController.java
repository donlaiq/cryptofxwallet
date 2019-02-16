/**
 * JavaFX popup window asking to the user to confirm the transaction of coins.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import com.donlaiq.command.ProcessHandlerWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SendMoneyController {

	private Stage parentStage, grandparentStage;
	//private Properties popupProperties;
	private String addressFrom, addressTo, amount;
	
	private Properties walletProperties, stringProperties;

	@FXML
	private Label label1, label2;
	
	public SendMoneyController(Stage parentStage, String addressFrom, String addressTo, String amount)
	{
		this.parentStage = parentStage;
		this.addressFrom = addressFrom;
		this.addressTo = addressTo;
		this.amount = amount;
		
		try
		{
			walletProperties = new Properties();
			walletProperties.load(WalletController.class.getClassLoader().getResourceAsStream("resources/setup.properties"));
			
			stringProperties = new Properties();
			BufferedReader in = new BufferedReader(new InputStreamReader(WalletController.class.getClassLoader().getResourceAsStream("resources/english.properties"), walletProperties.getProperty("encode")));
			stringProperties.load(in);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void initialize()
	{
		label1.setText(stringProperties.getProperty("popup.send.money.first.line").replace("${coin.code}", amount + " " + walletProperties.getProperty("coin.code")));
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
		
		MessagePopup mp = new MessagePopup(grandparentStage, stringProperties.getProperty("popup.money.sent.title"), stringProperties.getProperty("popup.money.sent.first.line"), stringProperties.getProperty("popup.money.sent.second.line"));
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
