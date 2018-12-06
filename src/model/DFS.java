package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DFS<T> implements Searcher<T> {
	private T startVertex;
	private T endVertex;
	private Graph<T> graph;
	
	public DFS(Searchable<T> searchablePipeGame)
	{
		this.graph = searchablePipeGame.getGraph();
		this.startVertex = searchablePipeGame.getStartVertex();
		this.endVertex = searchablePipeGame.getGoalVertex();
	}
	
	public void runDFS()
	{
		if(!this.graph.containsVertex(this.startVertex))
		{
			throw new RuntimeException("Vertex not exist");
		}
		
		//reset the graph
		this.graph.resetGraph();
		
		//DFS uses Stack data structure
		Stack<Node<T>> stack = new Stack<Node<T>>();
		Node<T> start = this.graph.getNode(startVertex);
		stack.push(start);
		start.setVisited(true);
		while(!stack.isEmpty())
		{
			Node<T> node = (Node<T>)stack.peek();
			//Get first node unvisited
			Edge<T> childEdge = node.edges().stream().filter
					(edge -> edge.toNode().isVisited() == false).findFirst().orElse(null);
			Node<T> child;
			if(childEdge != null)
			{
				child = childEdge.toNode();
				child.setVisited(true);
				child.setParent(node);
				stack.push(child);
			}
			else
			{
				stack.pop();
			}
		}
		
	}
	
	public List<T> getShortestPath()
	{
		// if nodes not found, return empty path
		if(!this.graph.containsVertex(this.startVertex) || !this.graph.containsVertex(this.endVertex))
		{
			return null;
		}
		
		//run dfs on graph
		this.runDFS();
		
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
