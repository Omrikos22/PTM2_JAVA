package model;

public interface Server {
	public void run(ClientHandler clientHandler);
	public void shutdown() throws InterruptedException;
}
