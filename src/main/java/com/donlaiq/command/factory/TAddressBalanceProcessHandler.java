/**
 * A command returning a list of transparent addresses with its corresponding balance.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.donlaiq.controller.model.Address;

public class TAddressBalanceProcessHandler extends ProcessHandler{
	
	private List<String> allAddresses;

	public TAddressBalanceProcessHandler(String command) 
	{
		super(command);
	}

	@Override
	public Object doSomething() 
	{
		List<Address> addresses = new ArrayList<Address>();
		List<String> listedAddresses = new ArrayList<String>();
		
		String address = "";
		String balance = "";
		
		while(scanner.hasNext())
        {
        	String line = scanner.nextLine(); 
        	if(line.contains("\"") && !line.contains("\"\"") && line.split("\"")[1].matches("^[a-zA-Z0-9]*$"))
        	{
        		address = line.split("\"")[1];
        		listedAddresses.add(address);
        	}
        	else if(Pattern.compile("[0-9]").matcher(line).find())
        	{
        		if(line.endsWith(","))
        			balance = line.substring(0, line.length()-1).trim();
        		else
        			balance = line.substring(0, line.length()).trim();
        		addresses.add(new Address(address, balance));
        	}
        }
        
        for(String addr : allAddresses)
        	if(!listedAddresses.contains(addr))
        		addresses.add(new Address(addr, "0.0"));
        
        return addresses;
	}

	
	/*
	 * This class needs the output of a previous process to do its job, so it works like a pipe between processes.
	 */
	public void setAllAddresses(List<String> allAddreses)
	{
		this.allAddresses = allAddreses;
	}
}
