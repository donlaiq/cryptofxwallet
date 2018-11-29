/**
 * A command returning a single line.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

public class ReturnSingleOutputProcessHandler extends ProcessHandler{
	
	public ReturnSingleOutputProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() 
	{
		String privateKey = "";
		if(scanner.hasNextLine())
			privateKey = scanner.nextLine();
		return privateKey;
	}

}
