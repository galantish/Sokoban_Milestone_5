package controller.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;public class MyServer 
{
	private int port;
	private iClientHandler clientHandler;
	private volatile boolean stop; //What is volatile?
	
	public MyServer(int port, iClientHandler clientHandler) 
	{
		this.port = port;
		this.clientHandler = clientHandler;
		this.stop = false;
	}
	
	public void runServer() throws Exception
	{
		ServerSocket server = new ServerSocket(this.port);
		server.setSoTimeout(1000);
		
		while(!stop)//Waiting to the next client
		{
			try
			{
				Socket aClient = server.accept();//Blocking calls from clients
				System.out.println("Client is connected!");
				InputStream inFromClient = aClient.getInputStream();
				OutputStream outToClient = aClient.getOutputStream();
			
				this.clientHandler.handleClient(inFromClient, outToClient);
				
				inFromClient.close();
				outToClient.close();
				aClient.close();
				System.out.println("Client is not connected!");
			}
			catch (SocketTimeoutException e) 
			{
				continue;
			}
		}
		server.close();
	}

	public void start()
	{
		Thread t = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					System.out.println("Running...");
					runServer();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public void stop()
	{
		this.stop = true;
	}
}