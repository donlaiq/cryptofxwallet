package com.donlaiq.controller.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Properties;

import com.donlaiq.controller.WalletController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class NodeController extends SetupController{

	private Properties stringProperties;
	
	private String OS;
	
	@FXML
	private TextField nodePath, javaFXSDKPath, startCommand, cliCommand;
	
	@FXML 
	private Button nodePathButton, javaFXSDKPathButton, back, next;
	
	@FXML
	private Label nodePathLabel, javafxSdkPathLabel, nodeConfigurationLabel, startCommandLabel, cliCommandLabel;
	
	public NodeController(Stage stage)
	{
		super(stage);
		
		OS = System.getProperty("os.name").toLowerCase();
		
		try
		{
			Properties walletProperties = new Properties();
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
		// At this point, the nodes are not still ready to call requestFocus to change the focus, but it will instruct to do it as soon as possible.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nodeConfigurationLabel.requestFocus();
			}
		});
		
		back.setEffect(lighting);
		next.setEffect(lighting);
		
		SVGPath svg = new SVGPath();
		svg.setStroke(new Color(0, 0, 0, .7));
		svg.setFill(new Color(0.968627451, 0.71372549, 0.101960784, 1));
		svg.setContent("M26 30l6-16h-26l-6 16zM4 12l-4 18v-26h9l4 4h13v4z");
		svg.setScaleX(0.8);
		svg.setScaleY(0.8);
		
		
		SVGPath svg2 = new SVGPath();
		svg2.setStroke(new Color(0, 0, 0, .6));
		svg2.setFill(new Color(0.968627451, 0.71372549, 0.101960784, 1));
		svg2.setContent("M26 30l6-16h-26l-6 16zM4 12l-4 18v-26h9l4 4h13v4z");
		svg2.setScaleX(0.8);
		svg2.setScaleY(0.8);
		
		nodePathButton.setGraphic(svg);
		javaFXSDKPathButton.setGraphic(svg2);
		
		nodePathButton.setEffect(glow);
		javaFXSDKPathButton.setEffect(glow);
		
		startGlowing();
	}
	
	
	@FXML
	public void lookForNodeFolder()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(stringProperties.getProperty("look.for.node.folder"));
        File directory = directoryChooser.showDialog(stage);
        
        if(directory != null)
		{
        	if(OS.indexOf("win") >= 0)
        		nodePath.setText(directory.getAbsolutePath() + "\\");
        	else
        		nodePath.setText(directory.getAbsolutePath() + "/");
		}
	}
	
	@FXML
	public void lookForJavaFXSDKFolder()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(stringProperties.getProperty("look.for.javafx.sdk.folder"));
        File directory = directoryChooser.showDialog(stage);
        
        if(directory != null)
		{
        	if(OS.indexOf("win") >= 0)
        		javaFXSDKPath.setText(directory.getAbsolutePath() + "\\");
        	else
        		javaFXSDKPath.setText(directory.getAbsolutePath() + "/");
		}
	}
	
	
	public void loadProperties()
	{
		if(choseProperties != null && choseProperties.containsKey("start.command"))
		{
			nodePath.setText(choseProperties.getOrDefault("node.path", ""));
			javaFXSDKPath.setText(choseProperties.getOrDefault("javafx.sdk.path", ""));
			startCommand.setText(choseProperties.getOrDefault("start.command", ""));
			cliCommand.setText(choseProperties.getOrDefault("cli.command", ""));
		}
		else
		{
			nodePath.setText(globalProperties.get("node.path"));
			javaFXSDKPath.setText(globalProperties.get("javafx.sdk.path"));
			startCommand.setText(globalProperties.get("start.command"));
			cliCommand.setText(globalProperties.get("cli.command"));
		}

	}
	
	public void updateMap()
	{
		choseProperties.put("node.path", nodePath.getText());
		choseProperties.put("javafx.sdk.path", javaFXSDKPath.getText());
		choseProperties.put("start.command", startCommand.getText());
		choseProperties.put("cli.command", cliCommand.getText());
	}


	@Override
	protected void setPaths()
	{
		if("true".equals(choseProperties.get("two.kind.of.addresses")))
			fxmlPathNext = "/resources/fxml/command_setup_z.fxml";
		else
			fxmlPathNext = "/resources/fxml/command_setup_t.fxml";
		fxmlPathPrevious = "/resources/fxml/general_setup.fxml";
	}
	
	@Override
	protected void setControllers() {
		nextController = new CommandController(stage);
		previousController = new GeneralController(stage);
	}

	@Override
	protected boolean isEmptyTextField() 
	{
		if(nodePath.getText().isBlank() || javaFXSDKPath.getText().isBlank() || startCommand.getText().isBlank() || cliCommand.getText().isBlank())
			return true;
		return false;
	}

	@Override
	protected void setRequiredPlaceholders() 
	{
		if(nodePath.getText().isBlank())
			nodePath.setPromptText(FIELD_REQUIRED);
		if(javaFXSDKPath.getText().isBlank())
			javaFXSDKPath.setPromptText(FIELD_REQUIRED);
		if(startCommand.getText().isBlank())
			startCommand.setPromptText(FIELD_REQUIRED);
		if(cliCommand.getText().isBlank())
			cliCommand.setPromptText(FIELD_REQUIRED);
	}
}
