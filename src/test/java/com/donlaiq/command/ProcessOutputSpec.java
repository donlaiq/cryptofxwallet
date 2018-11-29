package com.donlaiq.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.donlaiq.command.factory.ProcessHandler;
import com.donlaiq.command.factory.TAddressBalanceProcessHandler;
import com.donlaiq.command.factory.TAddressFinderProcessHandler;
import com.donlaiq.command.factory.TotalBalanceProcessHandler;
import com.donlaiq.command.factory.TransactionListProcessHandler;
import com.donlaiq.command.factory.ZAddressFinderProcessHandler;
import com.donlaiq.controller.model.Address;
import com.donlaiq.controller.model.Transaction;


@DisplayName("A crypto node")
public class ProcessOutputSpec {
	private List<String> tAddresses, zAddresses;
	private String command;
	
	//@Mock
	//private ProcessHandlerWrapper processHandlerWrapper;
	
	@BeforeEach
	public void init()
	{
		command = "anyValidCommand";
		tAddresses = Arrays.asList("taaaaaaaaaaaaaaaaaaaaaaaaa1aaaaaaaaa", "tbbbbbbbbbbbbbb2bbbbbbbbbbbbbbbbbbb", "tcccccccccccccccccccccccc3ccccccccc",
				"tddddddddddddd4dddddddddddddddddddd", "teeeeeeeeeeeeeeeeeeee5eeeeeeeeeeeee", "tfff6ffffffffffffffffffffffffffffff",
				"tggggggggggggggggggggggggggggggg7gg", "t8hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "tiiiiiiiiiiiiiiiiiiiiiiiiiiiii9iiii");		
		zAddresses = Arrays.asList("zaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"zbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	}

	@Test
	@DisplayName("is started if the path to the command exists")
	void existsCommandPathToStartNode()
	{
		try
		{
			Properties properties = new Properties();
			properties.load(new FileInputStream("wallet.properties"));
			
			File file = new File(properties.getProperty("path") + properties.getProperty("start.command"));
			assertTrue(file != null);
			//assert
		}
		catch(Exception e) 
		{
			assertTrue(e instanceof FileNotFoundException, () -> "Should throw FileNotFoundException");
		}
	}
	
	
	@Test
	@DisplayName("has a command to work with the blockchain and your own wallet")
	void existsCommandPathToUseThe()
	{
		try
		{
			Properties properties = new Properties();
			properties.load(new FileInputStream("wallet.properties"));
			
			File file = new File(properties.getProperty("path") + properties.getProperty("cli.command"));
			assertTrue(file != null);
			//assert
		}
		catch(Exception e) 
		{
			assertTrue(e instanceof FileNotFoundException, () -> "Should throw FileNotFoundException");
		}
	}
	
	
	@Test
	@DisplayName("should return a list of the last N transactions associated with the addresses of the wallet")
	void processReturningListOfTransactions()
	{
		String processOutput = "[\n" + 
				"		  {\n" + 
				"		    \"account\": \"\",\n" + 
				"		    \"address\": \"taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\n" + 
				"		    \"category\": \"receive\",\n" + 
				"		    \"amount\": 10.00000000,\n" + 
				"		    \"vout\": 0,\n" + 
				"		    \"confirmations\": 14745,\n" + 
				"		    \"blockhash\": \"abcd1\",\n" + 
				"		    \"blockindex\": 1,\n" + 
				"		    \"blocktime\": 1580404034,\n" + 
				"		    \"txid\": \"dcba1\",\n" + 
				"		    \"walletconflicts\": [\n" + 
				"		    ],\n" + 
				"		    \"time\": 1580403994,\n" + 
				"		    \"timereceived\": 1580403994,\n" + 
				"		    \"vjoinsplit\": [\n" + 
				"		    ],\n" + 
				"		    \"size\": 3173\n" + 
				"		  },\n" + 
				"		  {\n" + 
				"		    \"account\": \"\",\n" + 
				"		    \"address\": \"tbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\",\n" + 
				"		    \"category\": \"send\",\n" + 
				"		    \"amount\": -0.84116581,\n" + 
				"		    \"vout\": 1,\n" + 
				"		    \"fee\": -0.00010000,\n" + 
				"		    \"confirmations\": 14745,\n" + 
				"		    \"blockhash\": \"abcd2\",\n" + 
				"		    \"blockindex\": 1,\n" + 
				"		    \"blocktime\": 1580404035,\n" + 
				"		    \"txid\": \"dcba1\",\n" + 
				"		    \"walletconflicts\": [\n" + 
				"		    ],\n" + 
				"		    \"time\": 1580403995,\n" + 
				"		    \"timereceived\": 1580403995,\n" + 
				"		    \"vjoinsplit\": [\n" + 
				"		    ],\n" + 
				"		    \"size\": 3173\n" + 
				"		  },\n" + 
				"		  {\n" + 
				"		    \"account\": \"\",\n" + 
				"		    \"address\": \"tffffffffffffffffffffffffffffffffff\",\n" + 
				"		    \"category\": \"send\",\n" + 
				"		    \"amount\": -10.00000000,\n" + 
				"		    \"vout\": 0,\n" + 
				"		    \"fee\": -0.00010000,\n" + 
				"		    \"confirmations\": 14745,\n" + 
				"		    \"blockhash\": \"abcd3\",\n" + 
				"		    \"blockindex\": 1,\n" + 
				"		    \"blocktime\": 1540404034,\n" + 
				"		    \"txid\": \"dcba1\",\n" + 
				"		    \"walletconflicts\": [\n" + 
				"		    ],\n" + 
				"		    \"time\": 1580403996,\n" + 
				"		    \"timereceived\": 1580403996,\n" + 
				"		    \"vjoinsplit\": [\n" + 
				"		    ],\n" + 
				"		    \"size\": 3173\n" + 
				"		  }\n" + 
				"		]";
		
		ProcessHandler processHandler = new TransactionListProcessHandler(command);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		List<Transaction> outputList = (List<Transaction>)processHandler.executeProcess();
		
		Transaction t1 = new Transaction("2020-01-30 17:06:34", "10.00000000", "taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "dcba1");
		Transaction t2 = new Transaction("2020-01-30 17:06:35", "-0.84116581", "tbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", "dcba1");
		Transaction t3 = new Transaction("2020-01-30 17:06:36", "-10.00000000", "tffffffffffffffffffffffffffffffffff", "dcba1");
		
		assertThat(outputList).isNotEmpty();
		assertThat(outputList).containsExactly(t1, t2, t3);
	}
	
	
	@Test
	@DisplayName("should return an array with the balance for the T and Z addresses, and the sum of both of them")
	void processReturningBalances()
	{
		String processOutput = "{\n" + 
				"		  \"transparent\": \"498.76130014\",\n" + 
				"		  \"private\": \"899.9999\",\n" + 
				"		  \"total\": \"1398.76120014\"\n" + 
				"		}";
		
		ProcessHandler processHandler = new TotalBalanceProcessHandler(command);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		String[] outputList = (String[])processHandler.executeProcess();
		
		assertThat(outputList).isNotEmpty();
		assertTrue(outputList.length == 3);
		assertTrue(Double.valueOf(outputList[0]) + Double.valueOf(outputList[1]) == Double.valueOf(outputList[2]));
	}
	
	
	
	@Test
	@DisplayName("should return a list containing all the T addresses from the wallet")
	void processReturningAllTheTAddresses()
	{
		String processOutput = "[" + 
				"  \"taaaaaaaaaaaaaaaaaaaaaaaaa1aaaaaaaaa\",\n" + 
				"  \"tbbbbbbbbbbbbbb2bbbbbbbbbbbbbbbbbbb\",\n" + 
				"  \"tcccccccccccccccccccccccc3ccccccccc\",\n" + 
				"  \"tddddddddddddd4dddddddddddddddddddd\",\n" + 
				"  \"teeeeeeeeeeeeeeeeeeee5eeeeeeeeeeeee\",\n" + 
				"  \"tfff6ffffffffffffffffffffffffffffff\",\n" + 
				"  \"tggggggggggggggggggggggggggggggg7gg\",\n" + 
				"  \"t8hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh\",\n" + 
				"  \"tiiiiiiiiiiiiiiiiiiiiiiiiiiiii9iiii\"\n" + 
				"]";

		ProcessHandler processHandler = new TAddressFinderProcessHandler(command);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		List<String> outputList = (List<String>)processHandler.executeProcess();
		
		assertThat(outputList).isNotEmpty();
		assertThat(outputList).containsExactly(tAddresses.get(0), tAddresses.get(1), tAddresses.get(2), tAddresses.get(3), tAddresses.get(4),
				tAddresses.get(5), tAddresses.get(6), tAddresses.get(7), tAddresses.get(8));
		/*for(String address : tAddresses)
			assertThat(outputList).contains(address); */
	}
	
	@Test
	@DisplayName("should return a list containing all the T addresses together with its balance")
	void processReturningAllTheTAddressesWithItsBalance()
	{
		String processOutput = "[\n" + 
				"		  [\n" + 
				"		    [\n" + 
				"		      \"taaaaaaaaaaaaaaaaaaaaaaaaa1aaaaaaaaa\",\n" + 
				"		      120.21020607,\n" + 
				"		      \"\"\n" + 
				"		    ]\n" + 
				"		  ],\n" + 
				"		  [\n" + 
				"		    [\n" + 
				"		      \"tddddddddddddd4dddddddddddddddddddd\",\n" + 
				"		      0.02010406\n" + 
				"		    ],\n" + 
				"		    [\n" + 
				"		      \"t8hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh\",\n" + 
				"		      377.6898242,\n" + 
				"		      \"\"\n" + 
				"		    ]\n" + 
				"		  ],\n" + 
				"		  [\n" + 
				"		    [\n" + 
				"		      \"tiiiiiiiiiiiiiiiiiiiiiiiiiiiii9iiii\",\n" + 
				"		      0.84116581,\n" + 
				"		      \"\"\n" + 
				"		    ]\n" + 
				"		  ]\n" + 
				"		]";
		
		TAddressBalanceProcessHandler processHandler = new TAddressBalanceProcessHandler(command);
		processHandler.setAllAddresses(tAddresses);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		List<Address> outputList = (List<Address>)processHandler.executeProcess();
		assertThat(outputList).isNotEmpty();
		assertTrue(outputList.size() == tAddresses.size());
		assertThat(outputList).containsExactlyInAnyOrder(new Address(tAddresses.get(0), "120.21020607"), new Address(tAddresses.get(1), "0.0"), 
				new Address(tAddresses.get(2), "0.0"), new Address(tAddresses.get(3), "0.02010406"), new Address(tAddresses.get(4), "0.0"), 
				new Address(tAddresses.get(5), "0.0"), new Address(tAddresses.get(6), "0.0"), new Address(tAddresses.get(7), "377.6898242"), 
				new Address(tAddresses.get(8), "0.84116581"));
	}
	
		
	
	@Test
	@DisplayName("should return a list containing all the Z addresses from the wallet")
	void processReturningAllTheZAddresses()
	{
		String processOutput = "[" + 
				"  \"zaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\n" + 
				"  \"zbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\",\n" + 
				"]";

		ProcessHandler processHandler = new ZAddressFinderProcessHandler(command);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		List<String> outputList = (List<String>)processHandler.executeProcess();
		
		assertThat(outputList).isNotEmpty();
		for(String address : zAddresses)
			assertThat(outputList).contains(address);
	}
	
	
	/*@Test
	@DisplayName("should return the balance of a Z address")
	void processReturningTheBalanceOfAZAddress()
	{
		String processOutput = "120.21020607";
		
		ProcessHandler processHandler = new ZAddressBalanceProcessHandler(command);
		processHandler.initializeScanner(new ByteArrayInputStream(processOutput.getBytes()));
		List<String> outputList = (List<String>)processHandler.commonStuff();
	}*/
}
