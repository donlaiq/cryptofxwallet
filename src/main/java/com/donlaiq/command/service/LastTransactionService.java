/**
 * JavaFX service to look for concurrently the last 100 transactions associated with the addresses on the wallet.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.service;

import java.util.List;

import com.donlaiq.command.ProcessHandlerWrapper;
import com.donlaiq.controller.model.Transaction;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LastTransactionService extends Service<List<Transaction>>{
	
	private ProcessHandlerWrapper processHandlerWrapper;
	
	public LastTransactionService()
	{
		this.processHandlerWrapper = new ProcessHandlerWrapper();
	}

	@Override
	protected Task<List<Transaction>> createTask() {
		return new Task<List<Transaction>>() {

			@Override
			protected List<Transaction> call() throws Exception {
				return processHandlerWrapper.getTransactionList();
			}
			
		};
	}

}

