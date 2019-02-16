/**
 * A command returning the last N transactions involving addresses from the wallet.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import com.donlaiq.controller.model.Transaction;

public class TransactionListProcessHandler extends ProcessHandler{
	private Properties properties;

	public TransactionListProcessHandler(String command) 
	{
		super(command);
		try 
		{
			properties = new Properties();
			properties.load(TransactionListProcessHandler.class.getClassLoader().getResourceAsStream("resources/setup.properties"));
		}
		catch(Exception e) {}
	}

	@Override
	public Object doSomething() 
	{
		String address = "";
		String amount = "";
		String time = "";
		String txid = "";
		List<Transaction> transactionList = new ArrayList<Transaction>();
        while(scanner.hasNext())
        {
        	String line = scanner.nextLine();
        	if(line.contains("address"))
        		address = line.split(":")[1].split("\"")[1];
        	else if(line.contains("amount"))
        		amount = line.split(":")[1].substring(1, line.split(":")[1].length()-1);
        	else if(line.contains("txid"))
        		txid = line.split(":")[1].split("\"")[1];
        	else if(line.contains("\"time\""))
        	{
        		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(properties.getProperty("time.zone")));
        		calendar.setTime(new Date(Long.valueOf(line.split(":")[1].substring(1, line.split(":")[1].length()-1)) * 1000L));
        		int year = calendar.get(Calendar.YEAR);
        		int month = calendar.get(Calendar.MONTH);
        		String monthToString = String.valueOf(month+1);
        		if(monthToString.length() < 2)
        			monthToString = "0" + monthToString;
        		int day = calendar.get(Calendar.DAY_OF_MONTH);
        		String dayToString = String.valueOf(day);
        		if(dayToString.length() < 2)
        			dayToString = "0" + dayToString;
        		int hour = calendar.get(Calendar.HOUR_OF_DAY);
        		int minute = calendar.get(Calendar.MINUTE);
        		int second = calendar.get(Calendar.SECOND);
        		String hourToString = String.valueOf(hour);
        		if(hourToString.length() < 2)
        			hourToString = "0" + hourToString;
        		String minuteToString = String.valueOf(minute);
        		if(minuteToString.length() < 2)
        			minuteToString = "0" + minuteToString;
        		String secondToString = String.valueOf(second);
        		if(secondToString.length() < 2)
        			secondToString = "0" + secondToString;
        		
        		time = String.valueOf(year) + "-" + monthToString + "-" + dayToString + " " + hourToString + ":" + minuteToString + ":" + secondToString;
        		
        		if(!amount.startsWith("-"))
        			transactionList.add(new Transaction(time, amount, address, txid));
        		else
        			transactionList.add(new Transaction(time, amount, "", txid));
        	}
        }
        
        return transactionList;
	}
	
	
}
