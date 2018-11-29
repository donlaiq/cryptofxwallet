/**
 * Splash screen showing a video while the node is started
 * 
 * @author donlaiq
 */

package com.donlaiq.main;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.donlaiq.command.service.NodeTask;
import com.donlaiq.controller.MessagePopup;
import com.donlaiq.controller.WalletController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class SplashScreen extends Stage
{
	private MediaPlayer mp;
	private Scene scene;
	private NodeTask task;
	private Stage primaryStage;
	private Label initializaingLabel;
	private ExecutorService executorService;
	private String coinCode;
	private Properties pathProperty, messageProperties;
	
	public SplashScreen(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		setOnCloseRequest(e -> {
			executorService.shutdown();
		});
		
		try
		{
			pathProperty = new Properties();
			pathProperty.load(SplashScreen.class.getClassLoader().getResourceAsStream("resources/wallet.properties"));
			messageProperties = new Properties();
			messageProperties.load(SplashScreen.class.getClassLoader().getResourceAsStream("resources/english.properties"));
			coinCode = pathProperty.getProperty("coin.code");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	public void display()
	{
		task = new NodeTask();
			
		executorService = Executors.newFixedThreadPool(1);
    	executorService.execute(task);
		
		
		initStyle(StageStyle.UNDECORATED);
		    
		VBox root = new VBox();
    	
		try 
		{
			mp = new MediaPlayer(new Media(SplashScreen.class.getClassLoader().getResource("resources/splashScreenVideo.mp4").toURI().toString()));
		}
    	catch(Exception e) 
		{
    		e.printStackTrace();
		}
    	mp.setOnEndOfMedia(()-> {

    		try
    		{
    			// It will block until the node is running.
    			boolean isRunningNode = task.get();
    			if(isRunningNode)
    			{
    				// Wait an extra second and the close the splash screen
	    			try
	    			{
	    				Thread.sleep(1000);
	    			}
	    			catch(Exception e)
	    			{
	    				e.printStackTrace();
	    			}
	    			
	    			loadMainStage();
	    			executorService.shutdown();
	    			this.close();
    			}
    			else
    				throw new Exception();
    		}
    		catch(Exception e)
    		{
    			this.close();
    			executorService.shutdownNow();
    			task.getNodeStarter().releaseResources();
    			MessagePopup mp = new MessagePopup(this, messageProperties.getProperty("popup.path.exception.title"), messageProperties.getProperty("popup.path.exception.first.line"), messageProperties.getProperty("popup.path.exception.second.line"));
    			mp.display();
    		}
    	});
		
		MediaView mediaView = new MediaView(mp);
		mediaView.setPreserveRatio(false);
		mediaView.setFitWidth(600);
		mediaView.setFitHeight(400);
		
		root.getChildren().add(mediaView);
		
		initializaingLabel = new Label("   Starting " + coinCode + " Node...");
		initializaingLabel.setFont(Font.font("Verdana", 15));
		initializaingLabel.setStyle("-fx-text-fill: #f7b61a;");
		root.getChildren().add(initializaingLabel);
		
		root.setStyle("-fx-background-color: #000000;");
		scene = new Scene(root, 600, 420);
		setScene(scene);
		      
		mp.play();
		show();		       
	}
	
	/*public boolean isRunningNode()
	{
		return task.isRunning();
	}*/
	
	/*
	 * Load the main graphic user interface.
	 */
	private void loadMainStage()
	{
		try {
			WalletController btczWalletController = new WalletController(task.getNodeStarter(), primaryStage);
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("main.fxml"));
	    	fxmlLoader.setController(btczWalletController);
			
	        
	        Pane mainPane = (Pane)fxmlLoader.load();
	        mainPane.setStyle("-fx-background-color: transparent;");
	        
	
	        Scene scene = new Scene(mainPane);
	       
	        scene.getStylesheets().add(SplashScreen.class.getClassLoader().getResource("resources/style.css").toExternalForm());
	        
	        primaryStage.setTitle("Crypto FX Wallet");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
