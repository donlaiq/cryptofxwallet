/**
 * POJO with the more relevant data of a transaction.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller.model;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;

public class Transaction {
	private SimpleStringProperty date, amount, address, txid;
	
	public Transaction(String date, String amount, String address, String txid)
	{
		this.date = new SimpleStringProperty(date);
		this.amount = new SimpleStringProperty(amount);
		this.address = new SimpleStringProperty(address);
		this.txid = new SimpleStringProperty(txid);
	}

	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public String getAmount() {
		return amount.get();
	}

	public void setAmount(String amount) {
		this.amount.set(amount);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public String getTxid() {
		return txid.get();
	}

	public void setTxid(String txid) {
		this.txid.set(txid);
	}
	
	
	
	@Override
	public boolean equals(Object t) 
	{
	    if (this == t)
	        return true;

	    if (t == null)
	        return false;
	    
	    if (getClass() != t.getClass())
	        return false;
	    
	    Transaction transaction = (Transaction) t;
	
	    return Objects.equals(date.get(), transaction.date.get())
	            && Objects.equals(amount.get(), transaction.amount.get())
	            && Objects.equals(address.get(), transaction.address.get())
	            && Objects.equals(txid.get(), transaction.txid.get());
	}

}
