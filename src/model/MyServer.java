package model;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyServer implements Server{
    protected int          serverPortVal   = 6400;
    protected ServerSocket serverSocketVal = null;
    protected int maximum_parllel = 1;
    private boolean stop;
    private int counter;
    private ExecutorService executor;
    private ExecutorService scheduler = Executors.newSingleThreadExecutor();
    private PriorityBlockingQueue<ClientHandler> priorityQueue;

    public MyServer(int port, int M){
    	this.counter = 0;
        this.serverPortVal = port;
        this.maximum_parllel = M;
        this.executor = Executors.newFixedThreadPool(this.maximum_parllel);
        this.priorityQueue = new PriorityBlockingQueue<ClientHandler>(100, 
        		Comparator.comparing(ClientHandler::getClientBoardSize));
    }
    
    public void run(ClientHandler clientHandler)
    {
    	new Thread(() -> {
    		try {
    			runServer(clientHandler);
    		}
    		catch (Exception e) {
    			System.out.print(e.getMessage());
    		}
    	}).start();
    }
    public void runServer(ClientHandler clientHandler) throws IOException, InterruptedException{
    	ServerSocket server = new ServerSocket(this.serverPortVal);
    	server.setSoTimeout(1000);
        while(!this.executor.isShutdown())
        {
        	try
        	{
        		Socket aClient = server.accept();
        		try {
        			// Creating new ClientHandlerInstance for the new Client
        			ClientHandler newclientHandlerInstance = clientHandler.getClass().getConstructor().newInstance();
        			newclientHandlerInstance.setClientSocket(aClient);
        			this.scheduleJob(newclientHandlerInstance);
        			this.scheduler.execute(() -> {
        				while (true) {
        	                try {
        	                    this.executor.execute(this.priorityQueue.take());
        	                } catch (Exception e) {
        	                    // exception needs special handling
        	                    System.out.println(e);
        	                }
        	            }
        			});
        		}
        		catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	catch(SocketTimeoutException e)
        	{
        		//
        	}
        }
        server.close();
    }
    public void scheduleJob(ClientHandler job) {
    	this.priorityQueue.add(job);
    }
    public void shutdown() throws InterruptedException
    {
    	this.executor.shutdown();
    	// Waiting for everything to finish
    	while (!this.executor.awaitTermination(10, TimeUnit.SECONDS)) {
    		  System.out.println("Awaiting completion of threads.");
    		}	
    	System.out.println("Shutting Down the server...");
        this.executor.shutdownNow();
    }
}