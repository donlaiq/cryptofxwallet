/**
 * A command returning the balance of the wallet, differentiating between transparent and private addresses and summing the whole balance.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.util.regex.Pattern;

public class TotalBalanceProcessHandler extends ProcessHandler{

	public TotalBalanceProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() 
	{
		String[] balances = new String[3];
		while(scanner.hasNext())
        {
			String line = scanner.nextLine();
        	if(line.contains("transparent"))
        	{
        		String aux = line.split(":")[1];
        		balances[0] = aux.substring(1, aux.length()-1).split("\"")[1];
        	}
        	else if(line.contains("private"))
        	{
        		String aux = line.split(":")[1];
        		balances[1] = aux.substring(1, aux.length()-1).split("\"")[1];
        	}
        	else if(line.contains("total"))
        	{
        		String aux = line.split(":")[1];
        		balances[2] = aux.substring(1, aux.length()-1).split("\"")[1];
        	}
        	else if(Pattern.compile("[0-9]").matcher(line).find())
        	{
        		balances[0] = line.trim();
        		balances[1] = "0.0";
        		balances[2] = "0.0";
        	}
        }
		return balances;
	}
}
