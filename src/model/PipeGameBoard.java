package model;

import java.util.List;
import java.util.LinkedList;

import javax.sound.sampled.Line;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;

public class PipeGameBoard implements Cloneable,Board<PipeGameBoard> {
	public Pipe[][] pipeGameBoard;
	public boolean isSolved;
	public Pipe startPipe;
	public Pipe endPipe;
	public int boardRowLength;
	public int boardCoulmnLength;
	public List<Pipe> connectedPipesListFromStart = new ArrayList<Pipe>();
	private char[] unstraightPipes = {'7', 'J', 'L', 'F'};
	private char[] straightPipes = {'|', '-'};
	Map<Character,char[]> pipesLeftConnections = new HashMap<Character, char[]>();
	Map<Character,char[]> pipesUpConnections = new HashMap<Character, char[]>();
	Map<Character,char[]> pipesDownConnections = new HashMap<Character, char[]>();
	Map<Character,char[]> pipesRightConnections = new HashMap<Character, char[]>();

	public PipeGameBoard(String pipeGameStructure)
	{
		this.fillPipesSidesAvailableConnections();
		this.pipeGameBoard = this.createBoard(pipeGameStructure);
		this.isSolved = false;
	}
	
	private Pipe[][] createBoard(String pipeGameStructre)
	{
		int[] boardLength = this.findBoardLength(pipeGameStructre);
		this.boardRowLength = boardLength[0];
		this.boardCoulmnLength = boardLength[1];
		Pipe[][] board = new Pipe[this.boardRowLength][this.boardCoulmnLength];
		//LinkedList<Pipe> board = new LinkedList<Pipe>();
		String[] lines = pipeGameStructre.split("\\r?\\n");
		for(int i = 0; i < this.boardRowLength; i++) {
			char[] line = lines[i].toCharArray();
		    for(int j = 0; j<this.boardCoulmnLength; j++){
		    	if (line[j] == ' ')
		    	{
		    		continue;
		    	}
		    	else if(line[j] == 's')
		    	{
		    		board[i][j] = new Pipe(i, j, line[j], new char[] {});
		    		this.startPipe = board[i][j];
		    	}
		    	else if(line[j] == 'g')
		    	{
		    		board[i][j] = new Pipe(i, j, line[j], new char[] {});
		    		this.endPipe = board[i][j];
		    	}
		    	else if(this.checkIfStraightPipe(line[j]))
		    	{
		    		board[i][j] = new Pipe(i, j, line[j], this.straightPipes);

		    	}
		    	else
		    	{
		    		board[i][j] = new Pipe(i, j, line[j], this.unstraightPipes);	
		    	}
		    }
		}
		return board;
		
	}
	
	private boolean checkIfStraightPipe(char pipeType)
	{
		boolean unstraightPipe = false;
    	for (char x : this.unstraightPipes)
    	{
    		if (x == pipeType)
    		{
    			unstraightPipe = true;
    			break;
    		}
    	}
    	if(!unstraightPipe)
    	{
    		return true;
    	}
    	return false;
	}
	
	private int[] findBoardLength(String pipeGameStructre)
	{
		String[] lines = pipeGameStructre.split("\\r?\\n");
		int[] output = new int[2];
		int maxSizeRow = 0;
		for (int i = 0; i < lines.length;i++)
		{
			if(lines[i].length() > maxSizeRow)
			{
				maxSizeRow = lines[i].length();
			}
		}
		output[0] = lines.length;
		output[1] = maxSizeRow;
		return output;
	}
	
	private void fillPipesSidesAvailableConnections()
	{
		//Left Available Connections
		this.pipesLeftConnections.put((char)'|', "".toCharArray());
		this.pipesLeftConnections.put((char)'L', "".toCharArray());
		this.pipesLeftConnections.put((char)'F', "".toCharArray());
		this.pipesLeftConnections.put((char)'-', "-LFsg".toCharArray());
		this.pipesLeftConnections.put((char)'7', "-LFsg".toCharArray());
		this.pipesLeftConnections.put((char)'J', "-LFsg".toCharArray());
		this.pipesLeftConnections.put((char)'s', "-LF".toCharArray());
		this.pipesLeftConnections.put((char)'g', "-LF".toCharArray());
		
		//Up Available Connections
		this.pipesUpConnections.put((char) 'F', "".toCharArray());
		this.pipesUpConnections.put((char) '-', "".toCharArray());
		this.pipesUpConnections.put((char) '7', "".toCharArray());
		this.pipesUpConnections.put((char) '|', "F7|sg".toCharArray());
		this.pipesUpConnections.put((char) 'L', "F7|sg".toCharArray());
		this.pipesUpConnections.put((char) 'J', "F7|sg".toCharArray());
		this.pipesUpConnections.put((char) 's', "F7|".toCharArray());
		this.pipesUpConnections.put((char) 'g', "F7|".toCharArray());
		
		//Down Available Connections
		this.pipesDownConnections.put((char) '-', "".toCharArray());
		this.pipesDownConnections.put((char) 'L', "".toCharArray());
		this.pipesDownConnections.put((char) 'J', "".toCharArray());
		this.pipesDownConnections.put((char) 'F', "|LJsg".toCharArray());
		this.pipesDownConnections.put((char) '7', "|LJsg".toCharArray());
		this.pipesDownConnections.put((char) '|', "|LJsg".toCharArray());
		this.pipesDownConnections.put((char) 's', "|LJ".toCharArray());
		this.pipesDownConnections.put((char) 'g', "|LJ".toCharArray());
		
		//Right Available Connections
		this.pipesRightConnections.put((char) '7', "".toCharArray());
		this.pipesRightConnections.put((char) '|', "".toCharArray());
		this.pipesRightConnections.put((char) 'J', "".toCharArray());
		this.pipesRightConnections.put((char) '-', "-J7sg".toCharArray());
		this.pipesRightConnections.put((char) 'L', "-J7sg".toCharArray());
		this.pipesRightConnections.put((char) 'F', "-J7sg".toCharArray());
		this.pipesRightConnections.put((char) 's', "-J7".toCharArray());
		this.pipesRightConnections.put((char) 'g', "-J7".toCharArray());
		
		
	}	
	public boolean isUpNeighbourConnected(Pipe pipeToCheck, int rowIndexPipeToCheck, int columnIndexPipeToCheck, List<Pipe> previousNeighbours)
	{
		if(rowIndexPipeToCheck - 1 > -1)
		{
			Pipe neighborPipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck - 1][columnIndexPipeToCheck];
			if(pipeToCheck.isConnected(neighborPipeToCheck, this.pipesUpConnections) && previousNeighbours.contains(neighborPipeToCheck) == false)
			{
				return true;
			}
		}
		return false;
		
	}
	public boolean isLeftNeighbourConnected(Pipe pipeToCheck, int rowIndexPipeToCheck, int columnIndexPipeToCheck, List<Pipe> previousNeighbours)
	{
		if(columnIndexPipeToCheck - 1 > -1)
		{
			Pipe neighborPipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck - 1];
			if(pipeToCheck.isConnected(neighborPipeToCheck, this.pipesLeftConnections) && previousNeighbours.contains(neighborPipeToCheck) == false)
			{
				return true;
			}
		}
		return false;
		
	}
	public boolean isDownNeighbourConnected(Pipe pipeToCheck, int rowIndexPipeToCheck, int columnIndexPipeToCheck, List<Pipe> previousNeighbours)
	{
		if(rowIndexPipeToCheck + 1 <= this.boardRowLength - 1)
		{
			Pipe neighborPipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck + 1][columnIndexPipeToCheck];
			if(pipeToCheck.isConnected(neighborPipeToCheck, this.pipesDownConnections) && previousNeighbours.contains(neighborPipeToCheck) == false)
			{
				return true;
			}
		}
		return false;
	}
	public boolean isRightNeighbourConnected(Pipe pipeToCheck, int rowIndexPipeToCheck, int columnIndexPipeToCheck, List<Pipe> previousNeighbours)
	{
		if(columnIndexPipeToCheck + 1 <= this.boardCoulmnLength - 1)
		{
			Pipe neighborPipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck + 1];
			if(pipeToCheck.isConnected(neighborPipeToCheck, this.pipesRightConnections) && previousNeighbours.contains(neighborPipeToCheck) == false)
			{
				return true;
			}
		}
		return false;
	}
	public List<Pipe> getConnectedPipesToStartList()
	{
		this.connectedPipesListFromStart.clear();
		List<Pipe> previousNeighbours = new ArrayList<Pipe>();
		this.getConnectedPipesListFromStart(this.startPipe.pipeIndex, this.startPipe.pipeColumn, previousNeighbours);
		return this.connectedPipesListFromStart;
	}
	public void getConnectedPipesListFromStart(int rowIndexPipeToCheck, int columnIndexPipeToCheck, List<Pipe> previousNeighbours)
	{
		if(this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck] instanceof Pipe)
		{
			Pipe pipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck];
			if(isUpNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				this.connectedPipesListFromStart.add(this.pipeGameBoard[rowIndexPipeToCheck - 1][columnIndexPipeToCheck]);
				this.getConnectedPipesListFromStart(rowIndexPipeToCheck - 1, columnIndexPipeToCheck, previousNeighbours);
				
			}
			if(isLeftNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				this.connectedPipesListFromStart.add(this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck - 1]);
				this.getConnectedPipesListFromStart(rowIndexPipeToCheck, columnIndexPipeToCheck - 1, previousNeighbours);
			}
			if(isDownNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				this.connectedPipesListFromStart.add(this.pipeGameBoard[rowIndexPipeToCheck + 1][columnIndexPipeToCheck]);
				this.getConnectedPipesListFromStart(rowIndexPipeToCheck + 1, columnIndexPipeToCheck, previousNeighbours);
			}
			if(isRightNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				this.connectedPipesListFromStart.add(this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck + 1]);
				this.getConnectedPipesListFromStart(rowIndexPipeToCheck, columnIndexPipeToCheck + 1, previousNeighbours);
			}
		}
		
		
	}
	public boolean isPipesConnected(int rowIndexPipeToCheck, int columnIndexPipeToCheck, Pipe targetPipe, List<Pipe> previousNeighbours)
	{
		//recursive check all of unvisited neighbors of start pipe
		if(this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck] instanceof Pipe)
		{
			Pipe pipeToCheck = this.pipeGameBoard[rowIndexPipeToCheck][columnIndexPipeToCheck];
			if(targetPipe.pipeType == 'g' && pipeToCheck.pipeType == 'g')
			{
				return true;
			}
			if(pipeToCheck == targetPipe)
			{
				return true;
			}
			if(isUpNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				return this.isPipesConnected(rowIndexPipeToCheck - 1, columnIndexPipeToCheck, targetPipe, previousNeighbours);
				
			}
			if(isLeftNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				return this.isPipesConnected(rowIndexPipeToCheck, columnIndexPipeToCheck - 1, targetPipe, previousNeighbours);
			}
			if(isDownNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				return this.isPipesConnected(rowIndexPipeToCheck + 1, columnIndexPipeToCheck, targetPipe, previousNeighbours);
			}
			if(isRightNeighbourConnected(pipeToCheck, rowIndexPipeToCheck, columnIndexPipeToCheck, previousNeighbours))
			{
				previousNeighbours.add(pipeToCheck);
				return this.isPipesConnected(rowIndexPipeToCheck, columnIndexPipeToCheck + 1, targetPipe, previousNeighbours);
			}
		}
		return false;
		
	}
	public String getPipeGameStructre()
	{
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < this.boardRowLength;i++)
		{
			for(int j = 0;j < this.boardCoulmnLength;j++)
			{
				if(this.pipeGameBoard[i][j] instanceof Pipe)
				{
					output.append(this.pipeGameBoard[i][j].pipeType);	
				}
				else
				{
					output.append(' ');
				}
			}
			output.append("\n");
		}
		return output.toString();
	}
	@Override
	public PipeGameBoard clone() throws CloneNotSupportedException {
		PipeGameBoard clonedPipeGameBoard = (PipeGameBoard) super.clone();
		//Its ugly because Java Collections is cloned by reference and not by type
		Pipe[][] clonedPipeBoard = this.createBoard(this.getPipeGameStructre());
		clonedPipeGameBoard.pipeGameBoard = clonedPipeBoard;
		if(clonedPipeGameBoard == this) {
			System.out.println("Both instances hold same PipeGameBoard");
		}
		if(this.pipeGameBoard == clonedPipeGameBoard.pipeGameBoard)
		{
			System.out.println("Both instances hold same Board of pipes");
		}
		return clonedPipeGameBoard;
	}
	public String toString()
	{
		return this.getPipeGameStructre();
	}
	public boolean isSolved()
	{
		int startRowIndex = this.startPipe.pipeIndex;
		int startColumnIndex = this.startPipe.pipeColumn;
		List<Pipe> previousNeighbours = new ArrayList<Pipe>();
		if(this.isPipesConnected(startRowIndex, startColumnIndex, this.endPipe, previousNeighbours))
		{
			this.isSolved = false;
			return true;
		}
		else
		{
			return false;
		}
	}
	public List<PipeGameBoard> getPossibleMoves(PipeGameBoard currentBoard)
	{
		List<PipeGameBoard> nextSteps = new ArrayList<PipeGameBoard>();
		if(currentBoard.isSolved())
		{
			System.out.println("Goal found!");
			return nextSteps; 
		}
		Pipe[][] pipeGameBoard = currentBoard.pipeGameBoard;
		for(int i = 0;i < currentBoard.boardRowLength;i++)
		{
			for(int j = 0; j< currentBoard.boardCoulmnLength;j++)
			{
				if(pipeGameBoard[i][j] instanceof Pipe)
				{
					if(pipeGameBoard[i][j].pipeType != 's' && pipeGameBoard[i][j].pipeType != 'g')
					{
						//Its a regular Pipe
						PipeGameBoard newPipeGameBoard;
						try {
							newPipeGameBoard = currentBoard.clone();
							Pipe pipeToCheck = newPipeGameBoard.pipeGameBoard[i][j];
							List<Pipe> previousNeighbours = new ArrayList<Pipe>();
							//If the pipe is already connected - don't rotate
							if(newPipeGameBoard.isPipesConnected(newPipeGameBoard.startPipe.pipeIndex, newPipeGameBoard.startPipe.pipeColumn, pipeToCheck, previousNeighbours))
							{
								//Already tried to connect last connected pipe unsuccessfully
								continue;
							}
							//Pipe is not connected - try rotate to connect
							Pipe currentPipe = newPipeGameBoard.pipeGameBoard[i][j];
							if(this.tryConnectPipeToStart(newPipeGameBoard, currentPipe))
							{
								nextSteps.add(newPipeGameBoard);
								break;
							}
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(nextSteps.isEmpty())
		{
			//Means its not the correct way, go all connected pipe backwards and rotate them
			int connectedPipesSize = currentBoard.getConnectedPipesToStartList().size();
			for(int k=connectedPipesSize;k >=1; k--)
			{
				//Clone new PipeGameBoard to every new rotation step
				PipeGameBoard visitedClonedBoard = null;
				try {
					visitedClonedBoard = currentBoard.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Pipe> connectedPipes = visitedClonedBoard.getConnectedPipesToStartList();
				Pipe lastConnectedPipe = connectedPipes.get(k - 1);
				char lastConnectedPipeType = lastConnectedPipe.pipeType;
				lastConnectedPipe.rotate();
				if(this.tryConnectPipeToStart(visitedClonedBoard, lastConnectedPipe) && lastConnectedPipe.pipeType != lastConnectedPipeType)
				{
					//I have to set here the real parent
					nextSteps.add(visitedClonedBoard);
				}
			}
		}
		return nextSteps;
	}
	private boolean tryConnectPipeToStart(PipeGameBoard pipeGameBoard, Pipe pipeToConnect)
	{
		for(int rotateNumber = 0; rotateNumber < pipeToConnect.pipesFamily.length;rotateNumber++)
		{
			List<Pipe> previousNeighbours = new ArrayList<Pipe>();
			if(!pipeGameBoard.isPipesConnected(pipeGameBoard.startPipe.pipeIndex, pipeGameBoard.startPipe.pipeColumn, pipeToConnect, previousNeighbours))
			{
				pipeToConnect.rotate();
			}	
			else
			{
				//Pipe connected successfully - new State is inserted
				return true;
			}
		}
		return false;
	}
}
