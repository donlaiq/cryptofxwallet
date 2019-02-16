package com.donlaiq.controller.setup;

import java.util.Map;

import com.donlaiq.coin.properties.GlobalProperties;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class SetupController {
	
	protected final String FIELD_REQUIRED = "This field is required";
	
	protected GlobalProperties globalProperties;
	protected Stage stage;
	protected String fxmlPathNext, fxmlPathPrevious;
	protected SetupController nextController, previousController;
	protected Map<String, String> choseProperties;
	
	public abstract void loadProperties();
	protected abstract boolean isEmptyTextField();
	protected abstract void setRequiredPlaceholders();
	protected abstract void updateMap();
	protected abstract void setControllers();
	protected abstract void setPaths();
	
	protected Lighting lighting;
	protected Glow glow;
	
	public SetupController(Stage stage)
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
		glow.setLevel(0.9);
	}
	
	@FXML
	protected void nextScreen(ActionEvent event)
	{
		if(!isEmptyTextField())
		{
			updateMap();
			try
			{
				setPaths();
				setControllers();
				FXMLLoader fxmlLoaderCommand = new FXMLLoader();
		        fxmlLoaderCommand.setLocation(getClass().getResource(fxmlPathNext));
		        fxmlLoaderCommand.setController(nextController);
		        Pane paneCommand = (Pane)fxmlLoaderCommand.load();
		        
		        Scene sceneCommand = new Scene(paneCommand);
		        sceneCommand.getStylesheets().add(SetupController.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
		        
		        nextController.setChoseProperties(choseProperties);
		        nextController.loadProperties();
		        
		        stage.setScene(sceneCommand);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			setRequiredPlaceholders();
	}
	
	
	@FXML
	protected void previousScreen(ActionEvent event)
	{
		updateMap();
		try
		{
			setPaths();
			setControllers();
			FXMLLoader fxmlLoaderCommand = new FXMLLoader();
	        fxmlLoaderCommand.setLocation(getClass().getResource(fxmlPathPrevious));
	        fxmlLoaderCommand.setController(previousController);
	        Pane paneCommand = (Pane)fxmlLoaderCommand.load();
	        
	        Scene sceneCommand = new Scene(paneCommand);
	        sceneCommand.getStylesheets().add(SetupController.class.getClassLoader().getResource("resources/setup.css").toExternalForm());
	        
	        previousController.setChoseProperties(choseProperties);
	        previousController.loadProperties();
	       
	        
	        stage.setScene(sceneCommand);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected void setChoseProperties(Map<String, String> choseProperties)
	{
		this.choseProperties = choseProperties;
	}
	
	
	protected void startGlowing()
	{
		KeyValue keyValue = new KeyValue(glow.levelProperty(), 0);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
		Timeline timeline = new Timeline(keyFrame);
		timeline.setAutoReverse(true);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
