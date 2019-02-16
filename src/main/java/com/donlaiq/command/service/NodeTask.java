/**
 * JavaFX task to start the node and wait for it availability.
 * 
 * @author donlaiq
 */

package com.donlaiq.command.service;

import com.donlaiq.command.NodeStarter;

import javafx.concurrent.Task;

public class NodeTask extends Task<Boolean>{
	
	private NodeStarter nodeStarter;
	
	public NodeTask()
	{
		nodeStarter = new NodeStarter();
	}

	@Override
	protected Boolean call() throws Exception 
	{
		nodeStarter.waitUntilNodeIsAvailable();
		return true;
	}
	

	public NodeStarter getNodeStarter()
	{
		return nodeStarter;
	}
	
	
	public void releaseResources()
	{
		nodeStarter.releaseResources();
	}
	
	public void startNode(boolean isRescan)
	{
		nodeStarter.startNode(isRescan);
	}

	
	public boolean isNodeProcessAlive()
	{
		return nodeStarter.isAlive();
	}
}
