package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SearchablePipeGameBoard implements Searchable<PipeGameBoard> {
	public Graph<PipeGameBoard> connectedGraph;
	public PipeGameBoard  startNode;
	public PipeGameBoard  goalNode;
	
	public SearchablePipeGameBoard(PipeGameBoard pipeGameBoard)
	{
		this.connectedGraph = new Graph<PipeGameBoard>();
		this.startNode = pipeGameBoard;
		this.connectedGraph.addVertex(pipeGameBoard);
		this.buildWeightedGraph(pipeGameBoard);
	}
	private void buildWeightedGraph(PipeGameBoard pipeGameBoard)
	{
		Node<PipeGameBoard> currentBoardStateNode = this.connectedGraph.getVertex(pipeGameBoard);
		List<PipeGameBoard> nextBoardSteps = pipeGameBoard.getPossibleMoves(pipeGameBoard);
		if(nextBoardSteps.isEmpty()) {
			this.goalNode = pipeGameBoard;
			return;
		}
		for(PipeGameBoard nextBoardStep : nextBoardSteps)
		{
			System.out.println("Next step is: ");
			System.out.println(nextBoardStep.toString());
			int weight = this.getBoardWeight(nextBoardStep);
			if(this.connectedGraph.containsVertex(nextBoardStep))
			{
				System.out.println("Already Exists Vertex");
				continue;
			}
			this.connectedGraph.addVertex(nextBoardStep);
			this.connectedGraph.addEdge(pipeGameBoard, nextBoardStep, weight);
			Node nextBoardStepNode = this.connectedGraph.getVertex(nextBoardStep);
			nextBoardStepNode.setVisited(false);
			nextBoardStepNode.setParent(currentBoardStateNode);
			buildWeightedGraph(nextBoardStep);	
		}
		
	}

	private int getBoardWeight(PipeGameBoard pipeGameBoard)
	{
		return 1;
	}
	
	public Graph<PipeGameBoard> getGraph()
	{
		return this.connectedGraph;
	}
	public PipeGameBoard getStartVertex()
	{
		return this.startNode;
	}
	public PipeGameBoard getGoalVertex()
	{
		return this.goalNode;
	}

}
