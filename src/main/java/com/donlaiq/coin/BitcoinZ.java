/**
 * Objects related to the BitcoinZ cryptocoin.
 * 
 * @author donlaiq
 */

package com.donlaiq.coin;

import com.donlaiq.coin.properties.BitcoinZProperties;
import com.donlaiq.coin.properties.CryptoProperties;

public class BitcoinZ extends CryptoCoin {

	@Override
	public CryptoProperties getCryptoProperties() {
		return new BitcoinZProperties();
	}

}
