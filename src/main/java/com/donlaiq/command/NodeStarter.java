/**
 * It starts the node and verify its availability before to operate on it.
 * 
 * @author donlaiq
 */

package com.donlaiq.command;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;

public class NodeStarter {
	
	// System command to start a Command Line Interface (sh for Linux, cmd.exe for Windows)
	private String systemCommand;
	
	// Parameter to run the system command (-c for Linux, /k for Windows)
	private String systemCommandParameter;
	
	// Absolute path to the directory holding the commands to interact with the node
	private String nodeAbsolutePath; 
	
	// Command to start the node
	private String startCommand;
	
	// Command to make some request to the running node
	private String cliCommand; 
	
	// Process running the node
	private Process cryptoNodeProcess;
	
	/*
	 * The commands are readed from an external file trying to make the application as extensible as possible.
	 */
	public NodeStarter()
	{
		try
		{
			Properties walletProperties = new Properties();
			walletProperties.load(NodeStarter.class.getClassLoader().getResourceAsStream("resources/wallet.properties"));
			nodeAbsolutePath = walletProperties.getProperty("node.path");
			startCommand = walletProperties.getProperty("start.command");
			cliCommand = walletProperties.getProperty("cli.command");
			systemCommand = walletProperties.getProperty("system.command");
			systemCommandParameter = walletProperties.getProperty("system.command.parameter");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * It tries to start the node. 
	 */
	public boolean startNode() 
	{
		try
		{
			File file = new File(nodeAbsolutePath + startCommand);
			if(file.exists())
			{
				ProcessBuilder bitcoinzCli = new ProcessBuilder();
				bitcoinzCli.command(systemCommand, systemCommandParameter, nodeAbsolutePath + startCommand);
		        cryptoNodeProcess = bitcoinzCli.start();
			}
			else 
				throw new Exception();
		}
		catch(Exception e) 
		{
			if(cryptoNodeProcess != null)
			{
				cryptoNodeProcess.descendants().forEach(sub->sub.destroy());
				cryptoNodeProcess.destroy();
			}
			System.out.println("The path to the command doesn't exists.");
			return false;
		}
		return true;
	}
	
	/*
	 * There's a delay between the node is started and its availability to further use.
	 * It keeps trying till the node is usable.
	 */
	public void waitUntilNodeIsAvailable()
	{
		Process commandProcess = null;
		Scanner in = null;
		boolean isStarted = false;
		try
		{
			File file = new File(nodeAbsolutePath + cliCommand);
			if(file.exists())
			{
				ProcessBuilder pb = new ProcessBuilder(systemCommand, systemCommandParameter, nodeAbsolutePath + cliCommand + " getwalletinfo");
				
				while(!isStarted)
				{
					commandProcess = pb.start();
			
					in = new Scanner(commandProcess.getInputStream());
					if(in.hasNextLine())
						isStarted = true;
					
					Thread.sleep(1000);
				}
			}
			else 
				throw new Exception();
		}
		catch(Exception e)
		{
			System.out.println("The path to the command doesn't exists.");			
		}
		finally
		{
			if(in != null)
				in.close();
			if(commandProcess != null)
			{
				commandProcess.descendants().forEach(sub->sub.destroy());
				commandProcess.destroy();
			}
		}
	}

	
	/*
	 * When the application finishes, it tries to stop the node and release its resources.
	 */
	public void releaseResources() 
	{
		if(cryptoNodeProcess != null)
		{
			cryptoNodeProcess.descendants().forEach(sub->sub.destroy());
			cryptoNodeProcess.destroy();
		}
	}
}
