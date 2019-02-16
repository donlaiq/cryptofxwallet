/**
 * Objects related to the Qtum cryptocoin.
 * 
 * @author donlaiq
 */

package com.donlaiq.coin;

import com.donlaiq.coin.properties.CryptoProperties;
import com.donlaiq.coin.properties.QtumProperties;

public class Qtum extends CryptoCoin{

	@Override
	public CryptoProperties getCryptoProperties() {
		return new QtumProperties();
	}

}
