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
	private boolean isStartedNode;
	
	public NodeTask()
	{
		nodeStarter = new NodeStarter();
		isStartedNode = nodeStarter.startNode();
	}

	@Override
	protected Boolean call() throws Exception 
	{
		if(isStartedNode)
		{
			nodeStarter.waitUntilNodeIsAvailable();
			return true;
		}
		return false;
	}
	

	public NodeStarter getNodeStarter()
	{
		return nodeStarter;
	}
}
