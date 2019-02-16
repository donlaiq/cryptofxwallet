/**
 * Launch a splash screen waiting while the node is started 
 * 
 * @author donlaiq
 */

package com.donlaiq.main;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWallet extends Application {
    
    //private SplashScreen splashScreen;
	private SetupScreen setupScreen;

    public static void main(String[] args) 
    {
    	launch(MainWallet.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
    	/*ZCashBlockCount z = new ZCashBlockCount();
    	z.getBlockCount();*/
    	//primaryStage.initStyle(StageStyle.UNDECORATED);
    	setupScreen = new SetupScreen(primaryStage);
    	setupScreen.display();
    }

}
