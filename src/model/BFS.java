package model;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Collections;
import java.util.ArrayList;

public class BFS<T> implements Searcher<T>{
	private T startVertex;
	private T endVertex;
	private Graph<T> graph;
	
	public BFS(Searchable<T> searchablePipeGame)
	{
		this.graph = searchablePipeGame.getGraph();
		this.startVertex = searchablePipeGame.getStartVertex();
		this.endVertex = searchablePipeGame.getGoalVertex();
	}
	
	public void runBFS()
	{
		if(!this.graph.containsVertex(this.startVertex))
		{
			throw new RuntimeException("Vertex not exist");
		}
		
		//reset the graph
		this.graph.resetGraph();
		
		//initialize the queue
		Queue<Node<T>> queue = new LinkedList<>();
		Node<T> start = this.graph.getNode(startVertex);
		queue.add(start);
		
		//explore the graph
		while(!queue.isEmpty())
		{
			Node<T> first = queue.remove();
			first.setVisited(true);
			first.edges().forEach(edge -> {
				Node<T> neighbour = edge.toNode();
				if(!neighbour.isVisited())
				{
					neighbour.setParent(first);
					queue.add(neighbour);
				}
			});
			
		}
	}
	
	public List<T> getShortestPath()
	{
		// if nodes not found, return empty path
		if(!this.graph.containsVertex(this.startVertex) || !this.graph.containsVertex(this.endVertex))
		{
			return null;
		}
		
		//run bfs on graph
		this.runBFS();
		
		List<T> path = new ArrayList<T>();
		//trace path back from end vertex to start
		Node<T> end = this.graph.getNode(this.endVertex);
		while(end!= null && end!= this.graph.getNode(this.startVertex))
		{
			path.add(end.vertex());
			end = end.parent();
		}
		//if end is null, node not found
		if(end == null)
		{
			return null;
		}
		else {
			Collections.reverse(path);
		}
		return path;
	}
}
