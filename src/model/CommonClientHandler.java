package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public  abstract class CommonClientHandler implements ClientHandler {
	InputStream inputstrm;
	OutputStream outputstrm;
	Socket clntSocket;
	
	public abstract void bindToClient(Socket clntSocket, InputStream inputstrm, OutputStream outputstrm)  throws IOException;
}
