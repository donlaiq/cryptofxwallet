/**
 * POJO with the more relevant data of an address.
 * 
 * @author donlaiq
 */

package com.donlaiq.controller.model;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;

public class Address {
	private SimpleStringProperty addressString, balance;
	
	public Address(String addressString, String balance)
	{
		this.addressString = new SimpleStringProperty(addressString);
		this.balance = new SimpleStringProperty(balance);
	}
	
	public String getAddressString()
	{
		return this.addressString.get();
	}
	
	public String getBalance()
	{
		return this.balance.get();
	}
	
	@Override
	public boolean equals(Object a) 
	{
	    if (this == a)
	        return true;

	    if (a == null)
	        return false;
	    
	    if (getClass() != a.getClass())
	        return false;
	    
	    Address address = (Address) a;
	
	    return Objects.equals(addressString.get(), address.addressString.get())
	            && Objects.equals(balance.get(), address.balance.get());
	}
}
