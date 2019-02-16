package com.donlaiq.controller;

import com.donlaiq.coin.CryptoCoin;
import com.donlaiq.coin.properties.GlobalProperties;
import com.donlaiq.controller.setup.GeneralController;
import com.donlaiq.main.SplashScreen;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartController {

	@FXML
	private Button configureButton, startButton;
	
	@FXML
	private ImageView logo;
	
	private Stage stage;
	private Glow glow;
	private Lighting lighting;
	private GlobalProperties globalProperties;
	
	
	public StartController(Stage stage)
	{
		this.stage = stage;
		
		globalProperties = GlobalProperties.getGlobalProperties();
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-90.0);
		light.setColor(new Color(0.95, 0.95, 0.95, 1));
		
		lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(2.0);
		
		glow = new Glow();
		glow.setLevel(0.5);
	}
	
	
	@FXML
	protected void initialize()
	{
		configureButton.setEffect(lighting);
		startButton.setEffect(lighting);
		
		logo.setEffect(glow);
		
		startGlowing();
	}
	
	@FXML
	protected void configureNode(ActionEvent event)
	{
		try
		{
			GeneralController generalController = new GeneralController(stage);
			FXMLLoader fxmlLoaderCommand = new FXMLLoader();
	        fxmlLoaderCommand.setLocation(getClass().getResource("/resources/fxml/general_setup.fxml"));	        
	        fxmlLoaderCommand.setController(generalController);
	        Pane paneCommand = (Pane)fxmlLoaderCommand.load();
	        
	        Scene sceneCommand = new Scene(paneCommand);
	        sceneCommand.getStylesheets().add(StartController.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
	        
	        //generalController.setChoseProperties(choseProperties);
	        generalController.loadProperties();
	        
	        stage.setScene(sceneCommand);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void startNode(ActionEvent event)
	{
		stage.close();
		CryptoCoin.getCryptoCoin(globalProperties.get("cryptocoin"));
		SplashScreen splashScreen = new SplashScreen(stage);
		splashScreen.display();
	}
	
	protected void startGlowing()
	{
		KeyValue keyValue = new KeyValue(glow.levelProperty(), 0.1);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), /*e-> {
			node.setEffect(glow);
		},*/ keyValue);
		Timeline timeline = new Timeline(keyFrame);
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
