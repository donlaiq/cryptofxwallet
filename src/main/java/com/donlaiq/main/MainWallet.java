/**
 * Launch a splash screen waiting while the node is started 
 * 
 * @author donlaiq
 */

package com.donlaiq.main;


import javafx.application.Application;
import javafx.stage.Stage;

public class MainWallet extends Application {
    
    private SplashScreen splashScreen;

    public static void main(String[] args) 
    {
    	launch(MainWallet.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
    	splashScreen = new SplashScreen(primaryStage);
    	splashScreen.display();
    }

}
