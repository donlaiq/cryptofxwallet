/**
 * Abstraction to create related objects of the same family.
 * At the moment just one object is required.
 * 
 * @author donlaiq
 */

package com.donlaiq.coin;

import java.util.Map;
import com.donlaiq.coin.properties.CryptoProperties;

public abstract class CryptoCoin {
	
	private static CryptoCoin cryptoCoin;
	protected Map<String, String> setupMap;
	
	public abstract CryptoProperties getCryptoProperties();
	
	public static CryptoCoin getCryptoCoin() 
	{
		return cryptoCoin;
	}	
	
	/*
	 * Start point to set the required cryptocoin to work with.
	 */
	public static CryptoCoin getCryptoCoin(String cryptoName)
	{
		if("Qtum".equals(cryptoName))
			cryptoCoin = new Qtum();
		else if("ZCash".equals(cryptoName))
			cryptoCoin = new ZCash();
		else if("Bitcoin Core".equals(cryptoName))
			cryptoCoin = new BitcoinCore();
		else
			cryptoCoin = new BitcoinZ();

		return cryptoCoin;
	}
	
	
	/*
	 * Get a Map with the required properties to connect the node.
	 */
	public String getProperty(String property)
	{
		return setupMap.get(property);
	}
}
