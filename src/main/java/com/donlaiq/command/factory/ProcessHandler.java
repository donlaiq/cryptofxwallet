/**
 * Class abstracting the process of calling a new process. 
 * It sets all the boilerplate to call a process and through an abstract method let the subclasses doing the particular job.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public abstract class ProcessHandler {
	
	protected Process commandProcess;
	
	// Reads the output of a process line by line
	protected Scanner scanner;
	
	private String nodeAbsolutePath, cliCommand, systemCommand, systemCommandParameter;
	private String command;
	
	
	//protected int lengthTAddress, lengthTPrivateKey, lengthZAddress, lengthZPrivateKey;

	
	public ProcessHandler(String command)
	{
		this.command = command;
		scanner = null;
		try
		{
			Properties walletProperties = new Properties();
			walletProperties.load(ProcessHandler.class.getClassLoader().getResourceAsStream("resources/wallet.properties"));
			nodeAbsolutePath = walletProperties.getProperty("node.path");
			cliCommand = walletProperties.getProperty("cli.command");
			systemCommand = walletProperties.getProperty("system.command");
			systemCommandParameter = walletProperties.getProperty("system.command.parameter");
			/*lengthTAddress = Integer.valueOf(walletProperties.getProperty("length.t.address"));
			lengthTPrivateKey = Integer.valueOf(walletProperties.getProperty("length.t.private.key"));
			lengthZAddress = Integer.valueOf(walletProperties.getProperty("length.z.address"));
			lengthZPrivateKey = Integer.valueOf(walletProperties.getProperty("length.z.private.key"));*/
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Abstract method all the subclasses may implement, doing a particular job.
	 */
	public abstract Object doSomething();
	
	/*
	 * Boilerplate stuff to create a process and release its resources after the work it's done.
	 */
	public Object executeProcess()
	{
		Object object = null;
		try
		{
			ProcessBuilder pb = new ProcessBuilder(systemCommand, systemCommandParameter, nodeAbsolutePath + cliCommand + command);
			commandProcess = pb.start();

			initializeScanner(commandProcess.getInputStream());
			object = doSomething();
			/*scanner.close();
			scanner = null;*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//if(scanner != null)
				scanner.close();
			commandProcess.descendants().forEach(sub->sub.destroy());
			commandProcess.destroy();
		}
		return object;
	}
	
	/*
	 * Given the difficulty to test the functioning of a process, this method allows to fake the output of a process.
	 */
	public void initializeScanner(InputStream inputStream)
	{
		if(scanner == null)
			scanner = new Scanner(inputStream);
	}
}
