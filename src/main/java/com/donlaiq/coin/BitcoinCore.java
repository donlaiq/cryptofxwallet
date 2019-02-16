/**
 * Objects related to the Bitcoin Core cryptocoin.
 * 
 * @author donlaiq
 */

package com.donlaiq.coin;

import com.donlaiq.coin.properties.BitcoinCoreProperties;
import com.donlaiq.coin.properties.CryptoProperties;

public class BitcoinCore extends CryptoCoin{

	@Override
	public CryptoProperties getCryptoProperties() {
		return new BitcoinCoreProperties();
	}

}
