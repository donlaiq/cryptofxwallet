package com.donlaiq.controller.setup;


import com.donlaiq.coin.CryptoCoin;
import com.donlaiq.main.SplashScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CommandController extends SetupController{
	
	@FXML
	private TextField listTransactions, getTotalBalance, getAddressesByAccount, tAddressBalance, zListAddresses, zGetBalance,
		dumpPrivKey, zExportKey, importPrivKey, zImportKey, getNewAddress, zGetNewAddress, getBlockChainInfo, sendMany;
	
	@FXML 
	private Button back, start;
	
	
	@FXML 
	private GridPane gridPane;
	
	@FXML
	private Label commandConfigurationLabel;		
	
	
	public CommandController(Stage stage)
	{
		super(stage);
	}
	

	
	@Override
	public void loadProperties()
	{
			
		if(choseProperties != null && choseProperties.containsKey("list.transactions"))
		{
			listTransactions.setText(choseProperties.get("list.transactions"));
			getTotalBalance.setText(choseProperties.get("get.total.balance"));
			getAddressesByAccount.setText(choseProperties.get("get.addresses.by.account"));
			tAddressBalance.setText(choseProperties.get("t.address.balance"));
			
			dumpPrivKey.setText(choseProperties.get("dump.priv.key"));
			
			importPrivKey.setText(choseProperties.get("import.priv.key"));
			
			getNewAddress.setText(choseProperties.get("get.new.address"));
			
			getBlockChainInfo.setText(choseProperties.get("get.blockchain.info"));
			sendMany.setText(choseProperties.get("send.many"));
			if(choseProperties.get("two.kind.of.addresses").equals("true"))
			{
				if(choseProperties.get("z.list.addresses") != null)
				{
					zListAddresses.setText(choseProperties.get("z.list.addresses"));
					zGetBalance.setText(choseProperties.get("z.get.balance"));
					zExportKey.setText(choseProperties.get("z.export.key"));
					zImportKey.setText(choseProperties.get("z.import.key"));
					zGetNewAddress.setText(choseProperties.get("z.get.new.address"));
				}
				else
				{
					zListAddresses.setText(globalProperties.get("z.list.addresses"));
					zGetBalance.setText(globalProperties.get("z.get.balance"));
					zExportKey.setText(globalProperties.get("z.export.key"));
					zImportKey.setText(globalProperties.get("z.import.key"));
					zGetNewAddress.setText(globalProperties.get("z.get.new.address"));
				}
			}
		}
		else
		{
			listTransactions.setText(globalProperties.get("list.transactions"));
			getTotalBalance.setText(globalProperties.get("get.total.balance"));
			getAddressesByAccount.setText(globalProperties.get("get.addresses.by.account"));
			tAddressBalance.setText(globalProperties.get("t.address.balance"));
			
			dumpPrivKey.setText(globalProperties.get("dump.priv.key"));
			
			importPrivKey.setText(globalProperties.get("import.priv.key"));
			
			getNewAddress.setText(globalProperties.get("get.new.address"));
			
			getBlockChainInfo.setText(globalProperties.get("get.blockchain.info"));
			sendMany.setText(globalProperties.get("send.many"));
			
			if(choseProperties.get("two.kind.of.addresses").equals("true"))
			{
				zListAddresses.setText(globalProperties.get("z.list.addresses"));
				zGetBalance.setText(globalProperties.get("z.get.balance"));
				zExportKey.setText(globalProperties.get("z.export.key"));
				zImportKey.setText(globalProperties.get("z.import.key"));
				zGetNewAddress.setText(globalProperties.get("z.get.new.address"));
			}
		}
	}
	
	@Override
	protected void updateMap()
	{
		
		choseProperties.put("list.transactions", listTransactions.getText());
		choseProperties.put("get.total.balance", getTotalBalance.getText());
		choseProperties.put("get.addresses.by.account", getAddressesByAccount.getText());
		choseProperties.put("t.address.balance", tAddressBalance.getText());
		
		choseProperties.put("dump.priv.key", dumpPrivKey.getText());
		
		choseProperties.put("import.priv.key", importPrivKey.getText());
		
		choseProperties.put("get.new.address", getNewAddress.getText());
		
		choseProperties.put("get.blockchain.info", getBlockChainInfo.getText());
		choseProperties.put("send.many", sendMany.getText());

		if(choseProperties.get("two.kind.of.addresses").equals("true"))
		{
			choseProperties.put("z.list.addresses", zListAddresses.getText());
			choseProperties.put("z.get.balance", zGetBalance.getText());
			choseProperties.put("z.export.key", zExportKey.getText());
			choseProperties.put("z.import.key", zImportKey.getText());
			choseProperties.put("z.get.new.address", zGetNewAddress.getText());
		}
		
	}

	
	@Override
	protected void setPaths()
	{
		fxmlPathNext = null;
		fxmlPathPrevious = "/resources/fxml/node_setup.fxml";
	}

	@Override
	protected void setControllers() 
	{
		nextController = null;
		previousController = new NodeController(stage);
	}
	
	@FXML
	public void initialize()
	{
		
		// At this point, the nodes are not still ready to call requestFocus to change the focus, but it will instruct to do it as soon as possible.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				commandConfigurationLabel.requestFocus();
			}
		});
		
		back.setEffect(lighting);
		start.setEffect(lighting);
		
		startGlowing();
	}
	
	
	@FXML
	protected void start(ActionEvent event)
	{
		updateMap();
		globalProperties.saveDefaultProperties(choseProperties);
		stage.close();
		
		CryptoCoin.getCryptoCoin(choseProperties.get("cryptocoin"));
		SplashScreen splashScreen = new SplashScreen(stage);
		splashScreen.display();
	}



	@Override
	protected boolean isEmptyTextField() 
	{
		if(listTransactions.getText().isBlank() || getTotalBalance.getText().isBlank() || getAddressesByAccount.getText().isBlank() || 
				tAddressBalance.getText().isBlank() || zListAddresses.getText().isBlank() || zGetBalance.getText().isBlank() || dumpPrivKey.getText().isBlank() || 
				zExportKey.getText().isBlank() || importPrivKey.getText().isBlank() || zImportKey.getText().isBlank() || getNewAddress.getText().isBlank() || 
				getBlockChainInfo.getText().isBlank() || sendMany.getText().isBlank())
			return true;
		return false;
	}



	@Override
	protected void setRequiredPlaceholders() 
	{
		if(listTransactions.getText().isBlank())
			listTransactions.setPromptText(FIELD_REQUIRED);
		if(getTotalBalance.getText().isBlank())
			getTotalBalance.setPromptText(FIELD_REQUIRED);
		if(getAddressesByAccount.getText().isBlank())
			getAddressesByAccount.setPromptText(FIELD_REQUIRED);
		if(tAddressBalance.getText().isBlank())
			tAddressBalance.setPromptText(FIELD_REQUIRED);
		if(zListAddresses.getText().isBlank())
			zListAddresses.setPromptText(FIELD_REQUIRED);
		if(zGetBalance.getText().isBlank())
			zGetBalance.setPromptText(FIELD_REQUIRED);
		if(dumpPrivKey.getText().isBlank())
			dumpPrivKey.setPromptText(FIELD_REQUIRED);
		if(zExportKey.getText().isBlank())
			zExportKey.setPromptText(FIELD_REQUIRED);
		if(importPrivKey.getText().isBlank())
			importPrivKey.setPromptText(FIELD_REQUIRED);
		if(zImportKey.getText().isBlank())
			zImportKey.setPromptText(FIELD_REQUIRED);
		if(getNewAddress.getText().isBlank())
			getNewAddress.setPromptText(FIELD_REQUIRED);
		if(zGetNewAddress.getText().isBlank())
			zGetNewAddress.setPromptText(FIELD_REQUIRED);
		if(getBlockChainInfo.getText().isBlank())
			getBlockChainInfo.setPromptText(FIELD_REQUIRED);
		if(sendMany.getText().isBlank())
			sendMany.setPromptText(FIELD_REQUIRED);
	}
}
