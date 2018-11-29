/**
 * A command returning all the private addresses in the wallet.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.util.ArrayList;
import java.util.List;

import com.donlaiq.command.factory.ProcessHandler;

public class ZAddressFinderProcessHandler extends ProcessHandler{

	public ZAddressFinderProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() 
	{
		List<String> zAddresses = new ArrayList<String>();
        while(scanner.hasNext())
        {
        	String line = scanner.nextLine();
        	if(line.contains("z"))
        	{
        		zAddresses.add(line.split("\"")[1]);
        	}
        }
        return zAddresses;
	}
}
