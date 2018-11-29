/**
 * Wrapper to put together all the cli commands used by the application.
 * 
 * @author donlaiq
 */

package com.donlaiq.command;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.donlaiq.command.factory.ReturnSingleOutputProcessHandler;
import com.donlaiq.command.factory.ProcessHandler;
import com.donlaiq.command.factory.ReturnNothingProcessHandler;
import com.donlaiq.command.factory.TAddressBalanceProcessHandler;
import com.donlaiq.command.factory.TAddressFinderProcessHandler;
import com.donlaiq.command.factory.TotalBalanceProcessHandler;
import com.donlaiq.command.factory.TransactionListProcessHandler;
import com.donlaiq.command.factory.ZAddressFinderProcessHandler;
import com.donlaiq.controller.model.Address;
import com.donlaiq.controller.model.Transaction;

public class ProcessHandlerWrapper {
	
	private Properties properties, commandProperties;
	
	public ProcessHandlerWrapper()
	{
		try 
		{
			properties = new Properties();
			properties.load(TransactionListProcessHandler.class.getClassLoader().getResourceAsStream("resources/wallet.properties"));
			
			commandProperties = new Properties();
			commandProperties.load(TransactionListProcessHandler.class.getClassLoader().getResourceAsStream("resources/command.properties"));
		}
		catch(Exception e) {}
	}
	
	/*
	 * Get a list with the last 100 transactions, where some address of the wallet is associated with them.
	 */
	public List<Transaction> getTransactionList()
	{
		ProcessHandler processHandler = new TransactionListProcessHandler(" " + commandProperties.getProperty("list.transactions"));
		return (List<Transaction>)processHandler.executeProcess();
	}
	
	/*
	 * Get the balances of the wallet. Two balances and the final sum of them, if there are more than one kind of address.
	 */
	public String[] getBalances()
	{
		ProcessHandler processHandler = new TotalBalanceProcessHandler(" " + commandProperties.getProperty("get.total.balance"));
		return (String[])processHandler.executeProcess();
	}
	
	/*
	 * Get the list of transparent addresses.
	 */
	public List<Address> getTAddressesList()
	{
		ProcessHandler processHandler = new TAddressFinderProcessHandler(" " + commandProperties.getProperty("get.addresses.by.account"));
		TAddressBalanceProcessHandler tAddressBalanceProcessHandler = new TAddressBalanceProcessHandler(" " + commandProperties.getProperty("t.address.balance"));
		tAddressBalanceProcessHandler.setAllAddresses((List<String>)processHandler.executeProcess());
		return (List<Address>)tAddressBalanceProcessHandler.executeProcess();
	}
	
	/*
	 * Get the list of private addresses.
	 */
	public List<Address> getZAddressesList()
	{
		List<Address> zAddressList = new ArrayList<Address>();
		ProcessHandler processHandler = new ZAddressFinderProcessHandler(" " + commandProperties.getProperty("z.list.addresses"));
		List<String> zAddresses = (List<String>)processHandler.executeProcess();
		for(String address : zAddresses)
		{
			ProcessHandler zAddressBalanceProcessHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("z.get.balance") + " " + address);
			zAddressList.add(new Address(address, (String)zAddressBalanceProcessHandler.executeProcess()));
		}
		return zAddressList;
	}
	
	/*
	 * Get the private key of a transparent address.
	 */
	public String getTPrivateKey(String publicKey)
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("dump.priv.key") + " \"" + publicKey + "\""); 
		return (String) processHandler.executeProcess();
	}
	
	/*
	 * Get the private key of a private address.
	 */
	public String getZPrivateKey(String publicKey)
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("z.export.key") + " \"" + publicKey + "\"");
		return (String) processHandler.executeProcess();
	}
	
	/*
	 * Import a transparent address to the wallet given its private key.
	 */
	public void importTPrivateKey(String privateKey, String rescan)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + commandProperties.getProperty("import.priv.key") + " \"" + privateKey + "\" \"\" " + rescan); 
		processHandler.executeProcess();
	}
	
	/*
	 * Import a private address to the wallet given its private key.
	 */
	public void importZPrivateKey(String privateKey, String rescan)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + commandProperties.getProperty("z.import.key") + " \"" + privateKey + "\" " + rescan);
		processHandler.executeProcess();
	}
	
	/*
	 * Generate a new transparent address.
	 */
	public String newTAddress()
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("get.new.address"));
		return (String)processHandler.executeProcess();
	}
	
	/*
	 * Generate a new private address.
	 */
	public String newZAddress()
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("z.get.new.address"));
		return (String)processHandler.executeProcess();
	}
	
	/*
	 * It calls a RESTful API to get the amount of blocks of a full synchronized node. 
	 */
	private String getTotalBlockCount()
	{
		String totalBlocks = null;
		try 
		{
			String uri = properties.getProperty("blockchain.explorer.blockcount.api");
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			Scanner scanner = new Scanner(connection.getInputStream());
			if(scanner.hasNext())
				totalBlocks = scanner.nextLine();
			scanner.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalBlocks;
	}
	
	/*
	 * Returns the percentage of synchronization of the local node.
	 */
	public String getBlockchainPercentage()
	{
		String totalBlocks = getTotalBlockCount();
		
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + commandProperties.getProperty("get.block.count"));
		String totalDownloadedBlocks = (String)processHandler.executeProcess();
		
		if(totalBlocks != null && totalDownloadedBlocks != null && !totalBlocks.equals("") && !totalDownloadedBlocks.equals(""))
		{
			Double percentage = (Double.valueOf(totalDownloadedBlocks) / Double.valueOf(totalBlocks)) * 100.0;
			//System.out.println(percentage);
			String stringPercentage = String.valueOf(percentage);
			if(stringPercentage.length() >= 5)
				return stringPercentage.substring(0, 5);
			return stringPercentage;
		}
		return "";
	}
	
	
	/*
	 * It allows to send coins to another address.
	 */
	public void sendMoney(String addressFrom, String addressTo, String amount)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + commandProperties.getProperty("send.many") + " \"" + addressFrom + "\" \"[{\\\"address\\\":\\\"" + addressTo + "\\\", \\\"amount\\\":" + amount + "}]\"");
		processHandler.executeProcess();
	}
}
