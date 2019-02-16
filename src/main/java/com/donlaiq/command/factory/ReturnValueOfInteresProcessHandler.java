/**
 * Given the key, it tries to find the value associated through every line returned by the scanner.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

public class ReturnValueOfInteresProcessHandler extends ProcessHandler{
	
	private String key;

	public ReturnValueOfInteresProcessHandler(String command, String key) {
		super(command);
		this.key = key;
	}

	@Override
	public Object doSomething() {
		boolean stopped = false;
		String time = "0";
		while(scanner.hasNext() && !stopped)
        {
			String line = scanner.nextLine();
        	if(line.contains(key))
        	{
        		String aux = line.split(":")[1];
        		time = aux.substring(1, aux.length()-1);
        		stopped = true;
        	}
        }
		return time;
	}

}
