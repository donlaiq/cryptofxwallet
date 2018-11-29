/**
 * JavaFX service enclosing a JavaFX task to import concurrently several addresses.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.service;

import java.io.File;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ImportService extends Service<Boolean>{
	
	private File file;
	
	public ImportService(File file)
	{
		this.file = file;
	}

	@Override
	protected Task<Boolean> createTask() 
	{
		return new ImportTask(file);
	}

}
