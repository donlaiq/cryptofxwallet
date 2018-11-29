/**
 * JavaFx controller for the popup window to ask if the user really wants to create a new address.
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

public class NewAddressesController {
	private boolean isTAddress;
	private Stage parentStage, grandparentStage;
	private WalletController walletController;
	private Properties popupProperties;

	@FXML
	private Label label1, label2;
	
	public NewAddressesController(boolean isTAddress, Stage parentStage, WalletController walletController, Properties popupProperties)
	{
		this.isTAddress = isTAddress;
		this.parentStage = parentStage;
		this.walletController = walletController;
		this.popupProperties = popupProperties;
	}
	
	@FXML
	protected void initialize()
	{		
		label1.setText(popupProperties.getProperty("popup.new.t.address.first.line"));
		label2.setText(popupProperties.getProperty("popup.new.t.address.second.line"));
		if(!isTAddress)
			label2.setText(popupProperties.getProperty("popup.new.z.address.second.line"));
	}
	
	/*
	 * The user accepts to create a new address.
	 */
	@FXML
	public void doAction(ActionEvent event)
	{
		String newAddress = "";
		ProcessHandlerWrapper processHandlerWrapper = new ProcessHandlerWrapper();
		if(isTAddress)
			newAddress = processHandlerWrapper.newTAddress();
		else
			newAddress = processHandlerWrapper.newZAddress();
		walletController.loadAdresseses();
		parentStage.close();
		
		MessagePopup mp = new MessagePopup(grandparentStage, popupProperties.getProperty("popup.new.address.created.title"), popupProperties.getProperty("popup.new.address.created.first.line"), newAddress);
		mp.display();
	}

	/*
	 * The user cancel the creation of a new address.
	 */
	@FXML
	public void cancel(ActionEvent event)
	{
		parentStage.close();
	}

}
