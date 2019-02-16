package com.donlaiq.controller.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.donlaiq.coin.CryptoCoin;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GeneralController extends SetupController{
		
	@FXML
	private TextField timeZone, coinCode, encode, foreignLanguage, donateAddress;
	
	@FXML
	private ComboBox isTwoKindOfAddresses, cryptocoin;
	
	@FXML
	private Button cancel, next;
	
	@FXML
	private Label titleLabel, donateAddressLabel, timeZoneLabel, coinCodeLabel, encodeLabel, foreignLanguageLabel, twoKindOfAddressesLabel, cryptocoinLabel;
	
	private boolean isSomethingChanged;
	
	public GeneralController(Stage stage)
	{
		super(stage);
		isSomethingChanged = false;
	}
	
	public void loadProperties()
	{
		if(choseProperties != null && choseProperties.containsKey("time.zone"))
		{
			cryptocoin.getItems().setAll("Bitcoin Core", "BitcoinZ", "Qtum", "ZCash");
			cryptocoin.setValue(choseProperties.get("cryptocoin"));
			
			cryptocoin.getSelectionModel().selectedItemProperty().addListener(e -> {
				isSomethingChanged = false;
				setPresetCoin();
			});
			
			timeZone.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			coinCode.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			encode.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			foreignLanguage.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			donateAddress.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			timeZone.setText(choseProperties.getOrDefault("time.zone", ""));
			coinCode.setText(choseProperties.getOrDefault("coin.code", ""));
			encode.setText(choseProperties.getOrDefault("encode", ""));
			foreignLanguage.setText(choseProperties.getOrDefault("foreign.language", ""));
			donateAddress.setText(choseProperties.getOrDefault("donate.address", ""));
			
			isTwoKindOfAddresses.getItems().setAll("True", "False");
			
			if(choseProperties.get("two.kind.of.addresses").equals("true"))
				isTwoKindOfAddresses.getSelectionModel().select(0);
			else
				isTwoKindOfAddresses.getSelectionModel().select(1);
			
			
		}
		else
		{
			cryptocoin.getItems().setAll("Bitcoin Core", "BitcoinZ", "Qtum", "ZCash");
			cryptocoin.getSelectionModel().select(globalProperties.get("cryptocoin"));
			
			cryptocoin.getSelectionModel().selectedItemProperty().addListener(e -> {
				isSomethingChanged = false;
				setPresetCoin();
			});
			
			timeZone.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			
			coinCode.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			encode.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			foreignLanguage.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			donateAddress.textProperty().addListener(e -> {
				isSomethingChanged = true;
			});
			
			timeZone.setText(globalProperties.get("time.zone"));
			coinCode.setText(globalProperties.get("coin.code"));
			encode.setText(globalProperties.get("encode"));
			foreignLanguage.setText(globalProperties.get("foreign.language"));
			donateAddress.setText(globalProperties.get("donate.address"));
			
			isTwoKindOfAddresses.getItems().setAll("True", "False");
			
			if(globalProperties.get("two.kind.of.addresses").equals("false"))
				isTwoKindOfAddresses.getSelectionModel().select(1);
			else
				isTwoKindOfAddresses.getSelectionModel().select(0);
		}
	}
	
	
	@FXML
	public void initialize()
	{		
		// At this point, the nodes are not still ready to call requestFocus to change the focus, but it will instruct to do it as soon as possible.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				titleLabel.requestFocus();
			}
		});
		
		cancel.setEffect(lighting);
		next.setEffect(lighting);
		
		startGlowing();
		
	}
	
	@FXML
	protected void cancel(ActionEvent event)
	{
		stage.close();
	}
	
	
	@Override
	public void updateMap() {
		if(choseProperties == null)
			choseProperties = new HashMap<String, String>();
		
		choseProperties.put("javafx.sdk.path", globalProperties.get("javafx.sdk.path"));
		
		choseProperties.put("cryptocoin", String.valueOf(cryptocoin.getSelectionModel().getSelectedItem()));
			
		choseProperties.put("time.zone", timeZone.getText());
		choseProperties.put("coin.code", coinCode.getText());
		choseProperties.put("encode", encode.getText());
		choseProperties.put("foreign.language", foreignLanguage.getText());
		choseProperties.put("donate.address", donateAddress.getText());
		
		if(isTwoKindOfAddresses.getSelectionModel().getSelectedItem().equals("True"))
			choseProperties.put("two.kind.of.addresses", "true");
		else
			choseProperties.put("two.kind.of.addresses", "false");
		
	}
	
	@Override
	protected void setPaths()
	{
		fxmlPathNext = "/resources/fxml/node_setup.fxml";
		fxmlPathPrevious = null;
	}

	@Override
	protected void setControllers() 
	{
		nextController = new NodeController(stage);
		previousController = null;
	}
	
	
	/*
	 * Set the default values for all the fields associated with the chosen cryptocoin
	 */
	private void setPresetCoin()
	{

		if(choseProperties == null)
			choseProperties = new HashMap<String, String>();
		
		
		CryptoCoin coin = CryptoCoin.getCryptoCoin((String)cryptocoin.getSelectionModel().getSelectedItem());
		Map<String, String> coinMap = new HashMap<String, String>();
		coin.getCryptoProperties().setCryptoProperties(coinMap);
		Set<String> keys = coinMap.keySet();
		for(String k : keys)
		{
			choseProperties.put(k, coinMap.get(k));
		}
		
		choseProperties.put("node.path", "");
		
		timeZone.setText(choseProperties.get("time.zone"));
		coinCode.setText(choseProperties.get("coin.code"));
		encode.setText(choseProperties.get("encode"));
		foreignLanguage.setText(choseProperties.get("foreign.language"));
		donateAddress.setText(choseProperties.get("donate.address"));
		
		if(choseProperties.get("two.kind.of.addresses").equals("false"))
			isTwoKindOfAddresses.getSelectionModel().select(1);
		else
			isTwoKindOfAddresses.getSelectionModel().select(0);
			
			
			
	}

	@Override
	protected boolean isEmptyTextField() 
	{
		if(timeZone.getText().isBlank() || coinCode.getText().isBlank() || 
				encode.getText().isBlank() || foreignLanguage.getText().isBlank() || donateAddress.getText().isBlank())
			return true;
		return false;
	}

	@Override
	protected void setRequiredPlaceholders() 
	{
		if(timeZone.getText().isEmpty())
			timeZone.setPromptText(FIELD_REQUIRED);
		if(coinCode.getText().isEmpty())
			coinCode.setPromptText(FIELD_REQUIRED);
		if(encode.getText().isEmpty())
			encode.setPromptText(FIELD_REQUIRED);
		if(foreignLanguage.getText().isEmpty())
			foreignLanguage.setPromptText(FIELD_REQUIRED);
		if(donateAddress.getText().isEmpty())
			donateAddress.setPromptText(FIELD_REQUIRED);
	}
	

}
