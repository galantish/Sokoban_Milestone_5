package controller.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface iClientHandler 
{
	public void handleClient(InputStream inFromClient, OutputStream outToClient);

}