/**
 * Map of properties related to the Qtum cryptocoin.
 * 
 * @author donlaiq
 */


package com.donlaiq.coin.properties;

import java.util.Map;

public class QtumProperties implements CryptoProperties{

	@Override
	public void setCryptoProperties(Map<String, String> properties) 
	{
		properties.put("time.zone", "UTF");
		properties.put("coin.code", "QTUM");
		properties.put("encode", "ISO-8859-1");
		properties.put("foreign.language", "Spanish");
		properties.put("two.kind.of.addresses", "false");
		properties.put("donate.address", "QRoxfYMNadqeQmtNWhajVjX5JgRgZczaC9");
		properties.put("start.command", "qtumd");
		properties.put("cli.command", "qtum-cli");
		properties.put("list.transactions", "listtransactions \\\"*\\\" 100");
		properties.put("get.total.balance", "getbalance");
		properties.put("get.addresses.by.account", "getaddressesbyaccount \\\"\\\"");
		properties.put("t.address.balance", "listaddressgroupings");
		properties.put("dump.priv.key", "dumpprivkey");
		properties.put("import.priv.key", "importprivkey");
		properties.put("get.new.address", "getnewaddress");
		properties.put("get.blockchain.info", "getblockchaininfo");
		properties.put("send.many", "sendmany");
	}

}
