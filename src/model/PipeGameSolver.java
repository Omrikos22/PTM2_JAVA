package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class PipeGameSolver implements Solver<PipeGameBoard>{
	
	private String pipeGameState;
	
	public PipeGameSolver(String pipeGameState)
	{
		
		this.pipeGameState = pipeGameState;
	}
	
	public List<PipeGameBoard> solve()
	{
		PipeGameBoard pipeGameBoard = new PipeGameBoard(this.pipeGameState);
		if(pipeGameBoard.isSolved())
		{
			return Arrays.asList(pipeGameBoard);
		}
		SearchablePipeGameBoard searchablePipeGame = new SearchablePipeGameBoard(pipeGameBoard);
		Searcher bfsSearcher = new BFS(searchablePipeGame);
		List<PipeGameBoard> winSteps = bfsSearcher.getShortestPath();
		System.out.println("Win Steps: ");
		for(PipeGameBoard pipeGame: winSteps)
		{
			System.out.println(pipeGame.toString());
		}
		return winSteps;
		
		
	}

}
