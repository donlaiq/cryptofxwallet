/**
 * Objects related to the ZCash cryptocoin.
 * 
 * @author donlaiq
 */

package com.donlaiq.coin;

import com.donlaiq.coin.properties.CryptoProperties;
import com.donlaiq.coin.properties.ZCashProperties;


public class ZCash extends CryptoCoin{

	@Override
	public CryptoProperties getCryptoProperties() {
		return new ZCashProperties();
	}

}
