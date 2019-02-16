/**
 * Wrapper to put together all the cli commands used by the application.
 * 
 * @author donlaiq
 */

package com.donlaiq.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.donlaiq.command.factory.ReturnSingleOutputProcessHandler;
import com.donlaiq.command.factory.ReturnValueOfInteresProcessHandler;
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
	
	private Properties properties;
	
	public ProcessHandlerWrapper()
	{
		try 
		{
			properties = new Properties();
			properties.load(TransactionListProcessHandler.class.getClassLoader().getResourceAsStream("resources/setup.properties"));
		}
		catch(Exception e) {}
	}
	
	/*
	 * Get a list with the last 100 transactions, where some address of the wallet is associated with them.
	 */
	public List<Transaction> getTransactionList()
	{
		ProcessHandler processHandler = new TransactionListProcessHandler(" " + properties.getProperty("list.transactions"));
		return (List<Transaction>)processHandler.executeProcess();
	}
	
	/*
	 * Get the balances of the wallet. Two balances and the final sum of them, if there are more than one kind of address.
	 */
	public String[] getBalances()
	{
		ProcessHandler processHandler = new TotalBalanceProcessHandler(" " + properties.getProperty("get.total.balance"));
		return (String[])processHandler.executeProcess();
	}
	
	/*
	 * Get the list of transparent addresses.
	 */
	public List<Address> getTAddressesList()
	{
		ProcessHandler processHandler = new TAddressFinderProcessHandler(" " + properties.getProperty("get.addresses.by.account"));
		TAddressBalanceProcessHandler tAddressBalanceProcessHandler = new TAddressBalanceProcessHandler(" " + properties.getProperty("t.address.balance"));
		tAddressBalanceProcessHandler.setAllAddresses((List<String>)processHandler.executeProcess());
		return (List<Address>)tAddressBalanceProcessHandler.executeProcess();
	}
	
	/*
	 * Get the list of private addresses.
	 */
	public List<Address> getZAddressesList()
	{
		List<Address> zAddressList = new ArrayList<Address>();
		ProcessHandler processHandler = new ZAddressFinderProcessHandler(" " + properties.getProperty("z.list.addresses"));
		List<String> zAddresses = (List<String>)processHandler.executeProcess();
		for(String address : zAddresses)
		{
			ProcessHandler zAddressBalanceProcessHandler = new ReturnSingleOutputProcessHandler(" " + properties.getProperty("z.get.balance") + " " + address);
			zAddressList.add(new Address(address, (String)zAddressBalanceProcessHandler.executeProcess()));
		}
		return zAddressList;
	}
	
	/*
	 * Get the private key of a transparent address.
	 */
	public String getTPrivateKey(String publicKey)
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + properties.getProperty("dump.priv.key") + " \"" + publicKey + "\""); 
		return (String) processHandler.executeProcess();
	}
	
	/*
	 * Get the private key of a private address.
	 */
	public String getZPrivateKey(String publicKey)
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + properties.getProperty("z.export.key") + " \"" + publicKey + "\"");
		return (String) processHandler.executeProcess();
	}
	
	/*
	 * Import a transparent address to the wallet given its private key.
	 */
	public void importTPrivateKey(String privateKey, String rescan)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + properties.getProperty("import.priv.key") + " \"" + privateKey + "\" \"\" " + rescan); 
		processHandler.executeProcess();
	}
	
	/*
	 * Import a private address to the wallet given its private key.
	 */
	public void importZPrivateKey(String privateKey, String rescan)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + properties.getProperty("z.import.key") + " \"" + privateKey + "\" " + rescan);
		processHandler.executeProcess();
	}
	
	/*
	 * Generate a new transparent address.
	 */
	public String newTAddress()
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + properties.getProperty("get.new.address"));
		return (String)processHandler.executeProcess();
	}
	
	/*
	 * Generate a new private address.
	 */
	public String newZAddress()
	{
		ProcessHandler processHandler = new ReturnSingleOutputProcessHandler(" " + properties.getProperty("z.get.new.address"));
		return (String)processHandler.executeProcess();
	}
	
	
	/*
	 * Returns the percentage of synchronization of the local node.
	 */
	public String getBlockchainPercentage()
	{
		ProcessHandler processHandler = new ReturnValueOfInteresProcessHandler(" " + properties.getProperty("get.blockchain.info"), "verificationprogress");
		return (String)processHandler.executeProcess();
	}
	
	
	/*
	 * It allows to send coins to another address.
	 */
	public void sendMoney(String addressFrom, String addressTo, String amount)
	{
		ProcessHandler processHandler = new ReturnNothingProcessHandler(" " + properties.getProperty("send.many") + " \"" + addressFrom + "\" \"[{\\\"address\\\":\\\"" + addressTo + "\\\", \\\"amount\\\":" + amount + "}]\"");
		processHandler.executeProcess();
	}
}
