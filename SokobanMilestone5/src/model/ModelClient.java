package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import commands.Commands;

public class ModelClient
{
	private String serverIp;
	private int serverPort;
	private Gson json;
	
	public ModelClient(String serverIp, int serverPort)
	{
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		GsonBuilder builder = new GsonBuilder();
		this.json = builder.create();
	}
	
	public String createServerSession(Commands command, String params)
	{
		Socket theServer = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try
		{
			theServer = new Socket(this.serverIp, this.serverPort);
			System.out.println("Connected to server..");
			
			out = new PrintWriter(theServer.getOutputStream());
			in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			
			String comJson = this.json.toJson(command);
			out.println(comJson);
			out.flush();
			
			out.println(params);
			out.flush();
			
			switch (command)
			{
				case DB_QUERY:
				case GET_CLUE:
				case GET_SOLUTION:
					String getResult = in.readLine();
					return getResult;
				default:
					return null;
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				in.close();
				out.close();
				theServer.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;

	}
}