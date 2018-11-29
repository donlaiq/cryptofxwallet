/**
 * A command returning all the transparent addresses in the wallet.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TAddressFinderProcessHandler extends ProcessHandler{

	public TAddressFinderProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() {
		List<String> allAddresses = new ArrayList<String>();
		while(scanner.hasNext())
        {
			String line = scanner.nextLine();
			if(Pattern.compile("[0-9]").matcher(line).find())
			{
				allAddresses.add(line.split("\"")[1]);
			}
        }
		return allAddresses;
	}

}
