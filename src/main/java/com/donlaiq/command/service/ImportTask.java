/**
 * Task to handle concurrently the import of the several addresses on the wallet.
 * It waits for a UTF-8 text file having a "-" character in every line, where every line represents an address.
 * If the left side of the character "-" starts with a z, then the address to import is a Z address.
 * If the left side of the character "-" starts with anything else, then it tries to import the transparent address handled by the wallet.
 * The right side of the character "-" should be the private key of the address
 * 
 * @donlaiq
 */

package com.donlaiq.command.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.donlaiq.command.ProcessHandlerWrapper;

import javafx.concurrent.Task;

public class ImportTask extends Task<Boolean>{
	
	private ProcessHandlerWrapper processHandlerWrapper;
	private File file;
	
	
	public ImportTask(File file)
	{
		this.file = file;
		this.processHandlerWrapper = new ProcessHandlerWrapper();
	}

	@Override
	protected Boolean call() throws Exception 
	{
	       		
		List<String> lines = new ArrayList<String>();
        FileInputStream fileReader = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader, "UTF-8"));
		String line = bufferedReader.readLine();
		while(line != null)
		{
			lines.add(line);
			line = bufferedReader.readLine();	
		}
		
		bufferedReader.close();
		
		for(int i = 0; i < lines.size(); i++)
		{
			if(lines.get(i).contains("-"))
			{
				// if the user is trying to import an address from a node just with transparent addresses, 
				// then the file should follow the format *-<private_key> for every line.
				// After the last address to import, it does a rescan to look for the last 100 transactions
				// associated the addresses of the wallet.
				if(i == lines.size() - 1)
				{
					if(lines.get(i).startsWith("z"))
						processHandlerWrapper.importZPrivateKey(lines.get(i).split("-")[1].trim(), "true");
					else
						processHandlerWrapper.importTPrivateKey(lines.get(i).split("-")[1].trim(), "true");
				}
				else
				{
					if(lines.get(i).startsWith("z"))
						processHandlerWrapper.importZPrivateKey(lines.get(i).split("-")[1].trim(), "false");
					else
						processHandlerWrapper.importTPrivateKey(lines.get(i).split("-")[1].trim(), "false");
				}
				
			}
			else
			{
				bufferedReader.close();
				throw new Exception();
			}
			
		}
			
		
		return true;
	}

}
