package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import controller.commands.iCommand;

/*
 * CommandController - a common controller that holds all the commands and manages them by queue.
 */
public class CommandController 
{
	private BlockingQueue<iCommand> commandQueue;
	private boolean stop;
	
	public CommandController() 
	{
		this.stop = false;
		this.commandQueue = new ArrayBlockingQueue<iCommand>(20);
	}
	
	/*
	 * InsertCommand - gets a command and insert it to the queue.
	 */
	public void insertCommand(iCommand command) throws InterruptedException
	{
		this.commandQueue.put(command);
	}
	
	/*
	 * Start - inside a thread, poll a command from the queue and execute the command.
	 */
	public void start()
	{
		Thread t = new Thread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				//will run until the user enter exit or the game is finish
				while(!stop)
				{
					try 
					{
						//iCommand command = getCommandQueue().take();
						iCommand command = getCommandQueue().poll(1, TimeUnit.SECONDS);				
						if(command != null)				
							command.execute();	
					} 
					catch (InterruptedException e) 
					{
						System.out.println(e.getMessage());
					}
				}
			}
		});
		
		t.start();
	}
	
	/*
	 * Stop - stop the thread (basically it's close the session and close the window after exit command).
	 */
	public void stop()
	{
		this.stop = true;
		System.out.println("Goodbye!");
	}

	public BlockingQueue<iCommand> getCommandQueue() 
	{
		return commandQueue;
	}

	public void setCommandQueue(BlockingQueue<iCommand> commandQueue) 
	{
		this.commandQueue = commandQueue;
	}
}
