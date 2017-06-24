package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SokobanClientHandler extends Observable implements iClientHandler 
{
	private BlockingQueue<String> serverMessages;
	private boolean stopSendToClient;
	
	public SokobanClientHandler() 
	{
		this.serverMessages = new ArrayBlockingQueue<String>(25);
		this.stopSendToClient = false;
		this.serverMessages.clear();
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) 
	{
		try 
		{
			this.serverMessages.clear();
			this.stopSendToClient = false;
			
			//Adapter from InputStream to BufferReader
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter printToClient = new PrintWriter(outToClient);
			
			//open a new thread who reading from the client
			Thread fromClient = aSyncReadInputs(clientInput, "exit");
			Thread toClient = aSyncSendOutput(printToClient);
			
			fromClient.join(); //the threads are dead!
			toClient.join();
			clientInput.close();
			printToClient.close();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//read from client
	private void readInputs(BufferedReader input, String exitStr)
	{
		String line;
		boolean isEnd = false; //?
		try 
		{
			while(!isEnd)
			{
				 line=input.readLine();
				 if(line.equals(exitStr) == true)
				 {
					isEnd = true;
					insertToMessageQueue("Goodbye");
					stop();
				}
				else
				{
					setChanged();
					notifyObservers(line);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private Thread aSyncReadInputs(BufferedReader input, String exitStr)
	{
		Thread t = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				readInputs(input, exitStr);
			}
		});
		t.start();
		return t;
	}
	
	//send to client
	public void sendOutput(PrintWriter printToClient)
	{
		String outputStr;
		while(!this.stopSendToClient)
		{
			try 
			{
				outputStr = this.serverMessages.take();
				printToClient.println(outputStr);
				printToClient.flush();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public Thread aSyncSendOutput(PrintWriter printToClient)
	{
		Thread t = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				sendOutput(printToClient);
			}
		});
		t.start();
		return t;
	}
	
	public void insertToMessageQueue(String outputStr)
	{
		try 
		{
			this.serverMessages.put(outputStr);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void sendLevel(char[][] levelData)
	{
		String levelDataLine;
		for(int i=0; i<levelData.length; i++)
		{
			levelDataLine = String.valueOf(levelData[i]);
			insertToMessageQueue(levelDataLine);
		}
	}
	
	private void stop()
	{
		this.stopSendToClient=true;
	}
	
	public BlockingQueue<String> getMsgToClient() 
	{
		return serverMessages;
	}

	public void setMsgToClient(BlockingQueue<String> msgToClient) 
	{
		this.serverMessages = msgToClient;
	}
}