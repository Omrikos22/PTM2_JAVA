package model;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ClientHandler extends Runnable{
	void handleClient() throws IOException;
	void setClientSocket(Socket clientSocket) throws IOException;
	int getClientBoardSize();
}
