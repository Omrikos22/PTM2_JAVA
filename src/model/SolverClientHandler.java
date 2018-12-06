package model;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SolverClientHandler implements ClientHandler {
	
	private String pipeGameState;
	private CacheManager cacheManager;
	private Solver solver;
	private InputStream inFromClient;
	private OutputStream outToClient;
	private Socket aClient;
	
	public void setClientSocket(Socket clientSocket) throws IOException
	{
		this.aClient = clientSocket;
		this.inFromClient = clientSocket.getInputStream();
		this.outToClient = clientSocket.getOutputStream();
	}
	
	public void run()
	{
		try {
			this.handleClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getClientBoardSize()
	{
		PipeGameBoard pipeGameBoard = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(this.inFromClient);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();
			String message = new String();
			while(!message.contains("done")) {
			    buf.write((byte) result);
			    result = bis.read();
			    message = buf.toString("UTF-8");
			}
			buf.flush();
			this.pipeGameState = message.substring(0, message.length() - 4);
			pipeGameBoard = new PipeGameBoard(this.pipeGameState);
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (pipeGameBoard.boardCoulmnLength * pipeGameBoard.boardRowLength);
		
	}
	
	public void handleClient() throws IOException
	{
		System.out.println("SolverClientHandler is here for you!");
		BufferedInputStream bis = new BufferedInputStream(this.inFromClient);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		String message = new String();
		while(!message.contains("done")) {
		    buf.write((byte) result);
		    result = bis.read();
		    message = buf.toString("UTF-8");
		}
		buf.flush();
		this.pipeGameState = message.substring(0, message.length() - 4);
		System.out.println("Board current state: ");
		System.out.println("--------------------");
		System.out.println(this.pipeGameState);
		System.out.println("--------------------");
		this.cacheManager = new FileCacheManager("SolvedGames");
		PipeGameBoard pipeGameBoard = new PipeGameBoard(this.pipeGameState);
		String pipeGameBoardCachedSolve = this.cacheManager.Load(pipeGameBoard.toString());
		List<PipeGameBoard> winSteps;
		if (pipeGameBoardCachedSolve == "")
		{
			this.solver = new PipeGameSolver(this.pipeGameState);
			winSteps = this.solver.solve();
			String pipeGameStateFileName = pipeGameBoard.toString();
			StringBuilder winStepsFileContent = new StringBuilder();
			for(PipeGameBoard winStep : winSteps)
			{
				for(int i = 0;i<pipeGameBoard.boardRowLength;i++)
				{
					for(int j = 0;j<pipeGameBoard.boardCoulmnLength;j++)
					{
						if(pipeGameBoard.pipeGameBoard[i][j] instanceof Pipe && winStep.pipeGameBoard[i][j] instanceof Pipe)
						{
							if(pipeGameBoard.pipeGameBoard[i][j].pipeType != winStep.pipeGameBoard[i][j].pipeType)
							{
								int timesToRotatePipe = 0;
								while(pipeGameBoard.pipeGameBoard[i][j].pipeType != winStep.pipeGameBoard[i][j].pipeType)
								{
									pipeGameBoard.pipeGameBoard[i][j].rotate();
									timesToRotatePipe += 1;
								}
								System.out.println(String.format("Step: %d,%d,%d", i, j, timesToRotatePipe));
								winStepsFileContent.append(String.format("%d,%d,%d\r\n", i, j, timesToRotatePipe));
								this.outToClient.write((String.format("%d,%d,%d\n", i, j, timesToRotatePipe)).getBytes());
							}	
						}
					}
				}
			}
			this.cacheManager.Save(pipeGameStateFileName, winStepsFileContent.toString());
		}
		else
		{
			System.out.println("Cached Solution Founded!");
			System.out.println(pipeGameBoardCachedSolve);
			String[] steps;
			steps = pipeGameBoardCachedSolve.split("X");
			for(String step : steps)
			{
				this.outToClient.write((String.format("%s\n", step)).getBytes());
			}
		}
		System.out.println("done");
		this.outToClient.write(("done").getBytes());
		this.outToClient.flush();
		// Closing connections
		this.inFromClient.close();
		this.outToClient.close();
		//this.aClient.close();
	}
}
