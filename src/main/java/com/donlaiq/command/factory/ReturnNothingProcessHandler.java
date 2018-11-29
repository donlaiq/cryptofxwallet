/**
 * A command returning nothing after its processing.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

public class ReturnNothingProcessHandler extends ProcessHandler{

	public ReturnNothingProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() 
	{
		return null;
	}
}
