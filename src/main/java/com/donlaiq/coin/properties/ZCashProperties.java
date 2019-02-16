/**
 * Map of properties related to the ZCash cryptocoin.
 * 
 * @author donlaiq
 */


package com.donlaiq.coin.properties;

import java.util.Map;

public class ZCashProperties implements CryptoProperties{

	@Override
	public void setCryptoProperties(Map<String, String> properties) 
	{
		properties.put("time.zone", "UTF");
		properties.put("coin.code", "ZEC");
		properties.put("encode", "ISO-8859-1");
		properties.put("foreign.language", "Spanish");
		properties.put("two.kind.of.addresses", "true");
		properties.put("donate.address", "t1ZzxhxiXcUWsaSCTUzKxBNepq5ScBQsFLK");
		properties.put("start.command", "zcashd");
		properties.put("cli.command", "zcash-cli");
		properties.put("list.transactions", "listtransactions \\\"*\\\" 100");
		properties.put("get.total.balance", "z_gettotalbalance");
		properties.put("get.addresses.by.account", "getaddressesbyaccount \\\"\\\"");
		properties.put("t.address.balance", "listaddressgroupings");
		properties.put("z.list.addresses", "z_listaddresses");
		properties.put("z.get.balance", "z_getbalance");
		properties.put("dump.priv.key", "dumpprivkey");
		properties.put("z.export.key", "z_exportkey");
		properties.put("import.priv.key", "importprivkey");
		properties.put("z.import.key", "z_importkey");
		properties.put("get.new.address", "getnewaddress");
		properties.put("z.get.new.address", "z_getnewaddress");
		properties.put("get.blockchain.info", "getblockchaininfo");
		properties.put("send.many", "z_sendmany");
	}

}
