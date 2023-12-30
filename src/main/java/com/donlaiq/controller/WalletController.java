/**
 * JavaFX controller for the main GUI of the application.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.donlaiq.command.ProcessHandlerWrapper;
import com.donlaiq.command.service.LastTransactionService;
import com.donlaiq.command.service.NodeTask;
import com.donlaiq.controller.model.Address;
import com.donlaiq.controller.model.Transaction;

public class WalletController {
	
	private NodeTask nodeTask;
	//private NodeStarter nodeStarter;
	private ProcessHandlerWrapper processHandlerWrapper;
	private ObservableList<Address> tAddressList, zAddressList;
	private ObservableList<Transaction> transactionList;
	
	private boolean isImportingAddresses = false;
	private boolean isTryingToUpdate = false;
	
	private TimerTask updateTask;
	private Timer timer;
	private Stage stage;
	
	// Workaround to avoid multiple popup windows after double click.
	public static boolean isMessageShown;
	
	private boolean isTwoKindOfAddresses;
	
	private Properties stringProperties, walletProperties;
	
	private Lighting lighting;
	//private Glow glow;
	
	@FXML
	private TextField destinationAddress, amountToSend;
	
	@FXML
	private Button sendButton, donateButton/*, backupButton, importButton, newTButton, newZButton*/;
	
	@FXML
	private Label progressLabel, tBalanceLabel, zBalanceLabel, totalBalanceLabel, percentageLabel, donateLabel, btczLabel, 
					tB, zB, totalB, myAddressesLabel, destinationAddressLabel, amountToSendLabel, codeLabel;
	
	@FXML 
	private TableView<Transaction> tableView; 
	
	@FXML
	private TableView<Address> addressTable;
	
	@FXML
	private TableColumn<Transaction, String> dateColumn, amountColumn, addressColumn, txidColumn;
	
	@FXML
	private TableColumn<Address, String> myAddressColumn, myBalanceColumn;
	
	@FXML
	private ImageView arrow, mainLogo, background;
	
	@FXML
	private GridPane donateGrid;
	
	@FXML
	private BorderPane sendBorderPane;
	
	@FXML
	private StackPane iconP;
	
	@FXML
	private Menu mainMenu, languageMenu, walletMenu;
	
	@FXML
	private MenuItem /*aboutMenu,*/ quitMenu, backupMenu, importMenu, newTMenu, newZMenu;
	
	@FXML
	private VBox leftVBox;
	
	@FXML
	private AnchorPane leftAnchorPane;
	
	
	public WalletController(NodeTask nodeTask, Stage stage)
	{
		this.nodeTask = nodeTask;
		this.stage = stage;
		this.processHandlerWrapper = new ProcessHandlerWrapper();
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-90.0);
		light.setColor(new Color(0.95, 0.95, 0.95, 1));
		
		lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(2.0);
		
		/*glow = new Glow();
		glow.setLevel(0.6);*/
		
		try
		{
			walletProperties = new Properties();
			BufferedReader in = new BufferedReader(new InputStreamReader(WalletController.class.getClassLoader().getResourceAsStream("resources/setup.properties"), "UTF-8"));
			//walletProperties.load(WalletController.class.getClassLoader().getResourceAsStream("resources/setup.properties"));
			walletProperties.load(in);
			
			stringProperties = new Properties();
			BufferedReader in2 = new BufferedReader(new InputStreamReader(WalletController.class.getClassLoader().getResourceAsStream("resources/english.properties"), walletProperties.getProperty("encode")));
			stringProperties.load(in2);
			
			isTwoKindOfAddresses = Boolean.valueOf(walletProperties.getProperty("two.kind.of.addresses"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void initialize()
	{
		try
		{
			background.setImage(new Image(WalletController.class.getClassLoader().getResourceAsStream("resources/background.png")));
			mainLogo.setImage(new Image(WalletController.class.getClassLoader().getResourceAsStream("resources/cryptoFXWallet.png")));
			arrow.setImage(new Image(WalletController.class.getClassLoader().getResourceAsStream("resources/arrow.png")));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		sendButton.setEffect(lighting); 
		donateButton.setEffect(lighting);
		
		loadWidgetsStrings();
		
		dateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("amount"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("address"));
		txidColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("txid"));
		
		Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>> defaultTableViewCellFactory = TextFieldTableCell.forTableColumn();

		Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>> tableViewCellFactory = col -> {
            TableCell<Transaction, String> cell = defaultTableViewCellFactory.call(col);
            cell.itemProperty().addListener((obs, oldValue, newValue) -> {
            	if(newValue != null)
            	{           			
           			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 2 && !WalletController.isMessageShown) 
                            {
                            	TableCell c = (TableCell) event.getSource();                          

                            	Clipboard clipboard = Clipboard.getSystemClipboard();
                            	ClipboardContent content = new ClipboardContent();
                            	content.putString(c.getText());
                            	clipboard.setContent(content);
                            	
                            	createAndDisplayPopupMessage(stringProperties.getProperty("popup.to.clipboard.title"), c.getText(), stringProperties.getProperty("popup.to.clipboard.second.line"));
                            	
                            	WalletController.isMessageShown = true;
                            }
                        }
                    });
            	}
            });
            return cell;
        };
        dateColumn.setCellFactory(tableViewCellFactory);
        amountColumn.setCellFactory(tableViewCellFactory);
        addressColumn.setCellFactory(tableViewCellFactory);
        txidColumn.setCellFactory(tableViewCellFactory);
        
        
        
        myAddressColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("addressString"));
		myBalanceColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("balance"));
		Callback<TableColumn<Address, String>, TableCell<Address, String>> defaultTableViewCellFactoryMyAddresses = TextFieldTableCell.forTableColumn();

		Callback<TableColumn<Address, String>, TableCell<Address, String>> tableViewCellFactoryMyAddresses = col -> {
            TableCell<Address, String> cell = defaultTableViewCellFactoryMyAddresses.call(col);
            cell.itemProperty().addListener((obs, oldValue, newValue) -> {
            	if(newValue != null)
            	{          			
           			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 2 && !WalletController.isMessageShown) {
                            	TableCell c = (TableCell) event.getSource();                          

                            	Clipboard clipboard = Clipboard.getSystemClipboard();
                            	ClipboardContent content = new ClipboardContent();
                            	content.putString(c.getText());
                            	clipboard.setContent(content);
                            	
                            	createAndDisplayPopupMessage(stringProperties.getProperty("popup.to.clipboard.title"), c.getText(), stringProperties.getProperty("popup.to.clipboard.second.line"));
                            	
                            	WalletController.isMessageShown = true;
                            }
                        }
                    });
            	}
            });
            return cell;
        };
        myAddressColumn.setCellFactory(tableViewCellFactoryMyAddresses);
        myBalanceColumn.setCellFactory(tableViewCellFactoryMyAddresses);
        
        

		progressLabel.setText("Synchronized");

		arrow.setFitHeight(40);
		
		// Wait a few miliseconds to get a shining arrow just before to touch the Send button. 
		Timer glowingTimer = new Timer();
		glowingTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				arrow.setEffect(startGlowing(1, 0.9));
			}
			
		}, 1000l);
		
		
		mainLogo.setEffect(startGlowing(5, 0.6));
		
		ToggleGroup languageGroup = new ToggleGroup();
		
		RadioMenuItem englishItem = new RadioMenuItem();
		RadioMenuItem foreignItem = new RadioMenuItem();
		englishItem.setToggleGroup(languageGroup);
		englishItem.setSelected(true);
		englishItem.setText(stringProperties.getProperty("english.language"));
		englishItem.setOnAction(e -> {
			try 
			{
				englishItem.setSelected(true);
				stringProperties = new Properties();
				BufferedReader in = new BufferedReader(new InputStreamReader(WalletController.class.getClassLoader().getResourceAsStream("resources/english.properties"), walletProperties.getProperty("encode")));
				stringProperties.load(in);
				loadWidgetsStrings();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		
		foreignItem.setToggleGroup(languageGroup);
		foreignItem.setText(walletProperties.getProperty("foreign.language"));
		foreignItem.setOnAction(e -> {
			try 
			{
				foreignItem.setSelected(true);
				stringProperties = new Properties();
				BufferedReader in = new BufferedReader(new InputStreamReader(WalletController.class.getClassLoader().getResourceAsStream("resources/foreign.properties"), walletProperties.getProperty("encode")));
				stringProperties.load(in);
				loadWidgetsStrings();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		languageMenu.getItems().add(englishItem);
		languageMenu.getItems().add(foreignItem);
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		walletMenu.getItems().add(2, separator);
		
		
		updateFromBlockchain();
		//loadAdresseses();
		
		
		stage.setOnShown(e -> {
			animateLogo();
		});
		
		stage.setOnCloseRequest(e -> {
			try
			{
				// destroy the process running the node
				nodeTask.releaseResources();
	        		
				timer.cancel();
				
				Platform.exit();
			}
			catch(Exception ex) {
				ex.printStackTrace();
		}});
		
		
		// this task is runned every minute to look for new updates
		updateTask = new TimerTask() {

			@Override
			public void run() 
			{
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// The process of import addresses take some time. If this process is activated, then it won't try to look for new updates.
						if(!isImportingAddresses)
						{
							updateFromBlockchain();
						}
					}
					
				});
			}
			
		};
		timer = new Timer();
		timer.schedule(updateTask, 60000, 60000);
		
		if(!isTwoKindOfAddresses)
			newZMenu.setVisible(false);
		
	}
	
	
	/*
	 * Set the new Strings after the language is changed.
	 */
	private void loadWidgetsStrings()
	{
		mainMenu.setText(stringProperties.getProperty("main.menu"));
		walletMenu.setText(stringProperties.getProperty("wallet.menu"));
		languageMenu.setText(stringProperties.getProperty("language.menu"));
		//aboutMenu.setText(stringProperties.getProperty("about.menu"));
		quitMenu.setText(stringProperties.getProperty("quit.menu"));
		backupMenu.setText(stringProperties.getProperty("backup.menu"));
		importMenu.setText(stringProperties.getProperty("import.menu"));
		newTMenu.setText(stringProperties.getProperty("new.t.menu"));
		newZMenu.setText(stringProperties.getProperty("new.z.menu"));
		if(isTwoKindOfAddresses)
		{
			tB.setText(stringProperties.getProperty("t.balance"));
			zB.setText(stringProperties.getProperty("z.balance"));
		}
		else
		{
			tB.setVisible(false);
			zB.setVisible(false);
		}
		totalB.setText(stringProperties.getProperty("total.balance"));	
		dateColumn.setText(stringProperties.getProperty("date.column"));
		amountColumn.setText(stringProperties.getProperty("amount.column"));
		addressColumn.setText(stringProperties.getProperty("address.column"));
		txidColumn.setText(stringProperties.getProperty("txid.column"));
		myAddressesLabel.setText(stringProperties.getProperty("my.addresses"));
		destinationAddressLabel.setText(stringProperties.getProperty("destination.address"));
		amountToSendLabel.setText(stringProperties.getProperty("amount.to.send"));
		codeLabel.setText(walletProperties.getProperty("coin.code"));
		sendButton.setText(stringProperties.getProperty("send"));
		donateButton.setText(stringProperties.getProperty("donate"));
		myAddressColumn.setText(stringProperties.getProperty("my.address.column"));
		myBalanceColumn.setText(stringProperties.getProperty("my.balance.column"));
		
		progressLabel.setText(stringProperties.getProperty("synchronized.node"));
		if(!mainLogo.isVisible())
			progressLabel.setText(stringProperties.getProperty("synchronizing.node"));
	}
	
	/*
	 * Get new updates from the blockchain.
	 */
	private void updateFromBlockchain()
	{
		// If some update is taking so much time, specially when it comes to track the last 100 transactions associated with addresses
		// of the wallet, it could take more than 60 seconds (time by default to look for new updates), then it won't try to update again.
		if(!isTryingToUpdate)
		{
			isTryingToUpdate = true;
			
			// initialize a service to look for the last 100 transactions associated with the addresses of the wallet,
			// but it doesn't block the GUI. It will return the results in the future, when they are ready.
			LastTransactionService lastTransactionService = new LastTransactionService();
			lastTransactionService.setOnSucceeded(e -> {
				transactionList = FXCollections.observableList(lastTransactionService.getValue());				
				tableView.setItems(transactionList);
				
				String percentage = String.valueOf(Double.valueOf(processHandlerWrapper.getBlockchainPercentage()) * 100);
				if(percentage.length() > 5)
					percentage = percentage.substring(0, 5);
				
				if(!"".equals(percentage))
				{
					if(Double.valueOf(percentage) < 99.99)
					{
						percentageLabel.setText(String.valueOf(percentage) + "%");
						progressLabel.setText(stringProperties.getProperty("synchronizing.node"));
						mainLogo.setVisible(false);
						importMenu.setDisable(true);
					}
					else
					{
						progressLabel.setText(stringProperties.getProperty("synchronized.node"));
						percentageLabel.setVisible(false);
						mainLogo.setVisible(true);
						importMenu.setDisable(false);
					}
				}
				
				String[] balances = processHandlerWrapper.getBalances();
				if(isTwoKindOfAddresses)
				{
					tBalanceLabel.setText("   " + balances[0]);
					zBalanceLabel.setText("   " + balances[1]);
					totalBalanceLabel.setText("   " + balances[2]);
				}
				else
				{
					totalBalanceLabel.setText("   " + balances[0]);
					tBalanceLabel.setVisible(false);
					zBalanceLabel.setVisible(false);
				}
				
				
				loadAdresseses();
				
				isTryingToUpdate = false;
			});
			lastTransactionService.start();
			
		}
		
	}
	

	/*
	 * Updates the table with the addresses from the wallet.
	 */
	public void loadAdresseses()
	{
		List<Address> tList = processHandlerWrapper.getTAddressesList();
		List<Address> zList = null;
		if(isTwoKindOfAddresses)
			zList = processHandlerWrapper.getZAddressesList();
		
		List<Address> mergedList = new ArrayList<Address>();
		mergedList.addAll(tList);
		if(isTwoKindOfAddresses)
			mergedList.addAll(zList);
		
		tAddressList = FXCollections.observableList(tList);
		if(isTwoKindOfAddresses)
			zAddressList = FXCollections.observableList(zList);
		
		addressTable.setItems(FXCollections.observableList(mergedList));
		
	}
	
	/*
	 * Animate the arrow after click the donate button
	 */
	public void animateLogo() {
	    TranslateTransition tt = new TranslateTransition(Duration.seconds(1), arrow);

	    tt.setFromX(100);

	    tt.setToX(2);
	    tt.setCycleCount(Timeline.INDEFINITE);
	    tt.setAutoReverse(true);
	    tt.play();
	}
	
	
	/*
	 * Listener of the menu item.
	 */
	@FXML
	protected void createNewAddress(ActionEvent event)
	{
		try
		{
			boolean isT = true;
			
			Stage popup = new Stage();
			popup.initModality(Modality.WINDOW_MODAL);
			popup.initOwner(stage);
			
			FXMLLoader fxmlLoader = null;
	    	fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/messageWithCancelPopup.fxml"));
	    	if(((MenuItem)event.getSource()).getText().contains("Z"))
	    		isT = false;
	    	
	    	NewAddressesController addressController = new NewAddressesController(isT, popup, this, stringProperties);
	    	
	    	fxmlLoader.setController(addressController);
			Pane mainPane = (Pane)fxmlLoader.load();
			mainPane.getStylesheets().add(WalletController.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
			
			Scene scene = new Scene(mainPane);
			popup.setTitle(stringProperties.getProperty("popup.new.t.address.title"));
			if(!isT)
				popup.setTitle(stringProperties.getProperty("popup.new.z.address.title"));
			popup.setScene(scene);
			popup.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Listener of the menu item.
	 * Save a file where every line has the following format:
	 * <address_key>-<private_key>
	 * <address_key> could be a transparent or a private address.
	 */
	@FXML
	protected void backupWallet(ActionEvent event)
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(stringProperties.getProperty("backup.addresses.directory.chooser.title"));
        File directory = directoryChooser.showDialog(stage);
      
		if(directory != null)
		{
			List<String> pkTList = new ArrayList<String>();
			for(Address address : tAddressList)
				pkTList.add(processHandlerWrapper.getTPrivateKey(address.getAddressString()));
			
			List<String> pkZList = new ArrayList<String>();
			if(isTwoKindOfAddresses)
			{
				for(Address address : zAddressList)
					pkZList.add(processHandlerWrapper.getZPrivateKey(address.getAddressString()));
			}
			
			try
			{
				FileOutputStream outputStream = new FileOutputStream(directory + "/cryptoBackup.txt");
	            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
	            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
	            
	            for(int i = 0; i < tAddressList.size(); i++)
	            {	
	            	bufferedWriter.write(tAddressList.get(i).getAddressString() + "-" + pkTList.get(i));
	            	bufferedWriter.newLine();
	            }
	            
	            if(isTwoKindOfAddresses)
	            {
		            for(int i = 0; i < zAddressList.size(); i++)
		            {	
		            	bufferedWriter.write(zAddressList.get(i).getAddressString() + "-" + pkZList.get(i));
		            	bufferedWriter.newLine();
		            }
	            }
	            
	            bufferedWriter.close();
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 * Listener of the menu item.
	 * The file to read the private keys from, should have the following format for each line:
	 * *-<private_key> (1)
	 * z-<private_key> (2)
	 * 
	 * (1) If the wallet allows just transparent addresses and they start (for example) with a 3, the file should have the format *-<private_key> too,
	 * where '*' means everything different to 'z'.
	 * (2) For private addresses like in BitcoinZ, ZCash, ZenCash, etc.
	 */
	@FXML
	protected void importAddresses(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(stringProperties.getProperty("import.address.file.chooser.title").replace("${coin.code}", walletProperties.getProperty("coin.code")));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null)
        {
        	importMenu.setDisable(true);
        	isImportingAddresses = true;
        	
        	try
        	{
        	
	        	List<String> lines = new ArrayList<String>();
	            FileInputStream fileReader = new FileInputStream(file);
	    		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader, "UTF-8"));
	    		String line = bufferedReader.readLine();
	    		while(line != null)
	    		{
	    			lines.add(line);
	    			line = bufferedReader.readLine();	
	    		}
	    		
	    		bufferedReader.close();
	        	
	        	for(int i = 0; i < lines.size(); i++)
	    		{
	    			if(lines.get(i).contains("-"))
	    			{
	    				
	    				// if the user is trying to import an address from a node just with transparent addresses, 
	    				// then the file should follow the format *-<private_key> for every line.
	    				// After the last address to import, it does a rescan to look for the last 100 transactions
	    				// associated the addresses of the wallet.
	    				
	    				if(lines.get(i).startsWith("z"))
	    					processHandlerWrapper.importZPrivateKey(lines.get(i).split("-")[1].trim(), "false");
	    				else
	    					processHandlerWrapper.importTPrivateKey(lines.get(i).split("-")[1].trim(), "false");
	    				
	    				
	    				
    				
	    				// Stop the current node, wait 10 seconds to release completely the RAM memory and 
	    				// then start the node again doing a rescan to look for the balance of the imported addresses
	    				if(i == lines.size() - 1)
	    				{
	    					progressLabel.setText("Rescaning addresses...");
	    					
	    					nodeTask.releaseResources();

	    					// this is a brittle way to restart the node.
	    					// TODO: find a better way to restart the node once the killed process is completely out of the memory.
	    					try
	    					{
	    						Thread.sleep(10000);
	    					}
	    					catch(Exception ex)
	    					{
	    						ex.printStackTrace();
	    					}
	    					
	    					
	    					nodeTask = new NodeTask();
	    					nodeTask.startNode(true);
	    					ExecutorService executorService = Executors.newFixedThreadPool(1);
	    			    	executorService.execute(nodeTask);
	    			    	
	    			    	nodeTask.setOnSucceeded(e -> {
	    			    		updateFromBlockchain();
	    			    		isImportingAddresses = false;
	    			    		importMenu.setDisable(false);
	    			    	});
	    					
	    				}
	    			}
	    			else
	    			{
	    				bufferedReader.close();
	    				throw new Exception();
	    			}
	    			
	    		}
	        	
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
	}
	
	
	/*
	 * Listener of the donate button.
	 */
	@FXML
	protected void donate(MouseEvent event)
	{
		destinationAddress.setText(walletProperties.getProperty("donate.address"));
		if(isCorrectSettingsBeforeSend())
		{
			arrow.setVisible(true);
			donateLabel.setVisible(true);
		}
	}
	
	/*
	 * Listener of the send button.
	 */
	@FXML 
	protected void sendMoney(ActionEvent event)
	{
		try
		{
			if(isCorrectSettingsBeforeSend())
			{
			
				Stage popup = new Stage();
				popup.initModality(Modality.WINDOW_MODAL);
				popup.initOwner(stage);
				
				FXMLLoader fxmlLoader = null;
		    	fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/messageWithCancelPopup.fxml"));
		
		    	SendMoneyController addressController = new SendMoneyController(popup, addressTable.getItems().get(addressTable.getSelectionModel().getSelectedIndex()).getAddressString(), destinationAddress.getText(), amountToSend.getText());
		    	
		    	addressTable.getSelectionModel().clearSelection();
		    	destinationAddress.clear();
		    	amountToSend.clear();
	
		    	
		    	fxmlLoader.setController(addressController);
				Pane mainPane = (Pane)fxmlLoader.load();
				mainPane.getStylesheets().add(WalletController.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
				
				Scene scene = new Scene(mainPane);
				popup.setTitle(stringProperties.getProperty("popup.send.money.title"));
				popup.setScene(scene);
				popup.show();
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void hideDonation(KeyEvent event)
	{
		arrow.setVisible(false);
		donateLabel.setVisible(false);
	}
	
	/*
	 * Create and display a popup window with useful information.
	 */
	private void createAndDisplayPopupMessage(String title, String firstLine, String secondLine)
	{
		MessagePopup mp = new MessagePopup(stage, title, firstLine, secondLine);
		mp.display();
	}
	
	/*
	 * Verify the correct set up before to donate or to send money.
	 */
	private boolean isCorrectSettingsBeforeSend()
	{
		String regex = "[0-9]+(.[0-9]+)?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(amountToSend.getText());
		
		double addressBalance = 0.0;
		if(addressTable.getSelectionModel().getSelectedIndex() >= 0)
		{
			String stringBalance = addressTable.getItems().get(addressTable.getSelectionModel().getSelectedIndex()).getBalance();
			addressBalance = Double.parseDouble(stringBalance);
		}
		
		
		if(addressTable.getSelectionModel().getSelectedIndex() < 0 || addressTable.getItems().get(addressTable.getSelectionModel().getSelectedIndex()).getBalance().equals("0.0"))
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.not.selected.address.title"), stringProperties.getProperty("popup.not.selected.address.first.line"), stringProperties.getProperty("popup.not.selected.address.second.line"));
		else if(destinationAddress.getText().equals(""))
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.not.destination.address.title"), stringProperties.getProperty("popup.not.destination.address.first.line"), stringProperties.getProperty("popup.not.destination.address.second.line").replace("${coin.code}", stringProperties.getProperty("coin.code")));
		else if(amountToSend.getText().equals(""))
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.not.amount.to.send.title"), stringProperties.getProperty("popup.not.amount.to.send.first.line"), stringProperties.getProperty("popup.not.amount.to.send.second.line"));
		else if(!matcher.matches())
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.not.valid.amount.to.send.title"), stringProperties.getProperty("popup.not.valid.amount.to.send.first.line"), stringProperties.getProperty("popup.not.valid.amount.to.send.second.line"));
		else if(addressBalance < Double.parseDouble(amountToSend.getText()))
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.not.enough.title"), stringProperties.getProperty("popup.not.enough.first.line"), stringProperties.getProperty("popup.not.enough.second.line").replace("${coin.code}", stringProperties.getProperty("coin.code")));
		else if(destinationAddress.getText().equals(addressTable.getItems().get(addressTable.getSelectionModel().getSelectedIndex()).getAddressString()))
			createAndDisplayPopupMessage(stringProperties.getProperty("popup.same.address.title"), stringProperties.getProperty("popup.same.address.first.line"), stringProperties.getProperty("popup.same.address.second.line"));
		else
			return true;
		
		return false;
	}
	
	
	/*
	 * Release resources before to close the application.
	 */
	@FXML
	private void exit(ActionEvent event)
	{
		nodeTask.releaseResources();
		timer.cancel();
		Platform.exit();
	}
	
	
	/*
	 * Method to customize the glow of the element and the frame of time to reach both ends.
	 */
	private Glow startGlowing(int frameOfTime, double maxGlow)
	{
		Glow glow = new Glow();
		glow.setLevel(maxGlow);
		
		KeyValue keyValue = new KeyValue(glow.levelProperty(), 0);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameOfTime), keyValue);
		Timeline timeline = new Timeline(keyFrame);
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		return glow;
	}
	

}
