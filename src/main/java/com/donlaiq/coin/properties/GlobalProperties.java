/**
 * Class holding a map to pass through every screen to configure a new node.
 * 
 * @author donlaiq
 */


package com.donlaiq.coin.properties;

import io.github.pixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;


public class GlobalProperties {
	
	public Map<String, String> setupMap;
	private static GlobalProperties properties;
	
	/*
	 * Initialize a Map, loading it with the last configuration saved in the file setup.properties.
	 */
	private GlobalProperties()
	{
		setupMap = new HashMap<String, String>();
		
		Stream<String> lines = null;
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(GlobalProperties.class.getClassLoader().getResource("resources/setup.properties").getPath()));
			String line = BoundedLineReader.readLine(reader, 5_000_000);
			while(line != null)
			{
				String[] splitLine = line.split("=");
		    	setupMap.put(splitLine[0], splitLine[1]);
		    	line = BoundedLineReader.readLine(reader, 5_000_000);
			}
			reader.close();		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(lines != null)
				lines.close();
		}
		
	}
	
	
	/*
	 * Return a singleton of the class.
	 */
	public static GlobalProperties getGlobalProperties() 
	{
		if(properties == null)
			properties = new GlobalProperties();
		 return properties;
	}	
	
	
	/*
	 * After the last screen of the configuration is set up, it saves the configuration of the node in the file setup.properties.
	 */
	public void saveDefaultProperties(Map<String, String> chooseProperties)
	{
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(GlobalProperties.class.getClassLoader().getResource("resources/setup.properties").getPath())) )
		{
			Iterator<String> keys = chooseProperties.keySet().iterator();
			while(keys.hasNext())
			{
				String currentKey = keys.next();
				bw.write(currentKey + "=" + chooseProperties.get(currentKey));
				bw.newLine();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Get a single property of the map.
	 */
	public String get(String property)
	{
		return setupMap.get(property);
	}

}
