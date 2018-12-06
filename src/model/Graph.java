package model;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

public class Graph<E> {
	private Map<E, Node<E>> adjacencyList;
	
	public Graph()
	{
		this.adjacencyList = new HashMap<>();
	}
	public void addVertex(E vertex)
	{
		if(!adjacencyList.containsKey(vertex))
		{
			this.adjacencyList.put(vertex, new Node(vertex));
		}
	}
	public boolean addEdge(E vertex1, E vertex2, int weight)
	{
		if(!containsVertex(vertex1) && containsVertex(vertex2))
		{
			throw new RuntimeException("Vertex does not exist");
		}
		Node<E> node1 = getVertex(vertex1);
		Node<E> node2 = getVertex(vertex2);
		return node1.addEdge(node2, weight);
	}
	public boolean containsVertex(E vertex) 
	{
		for(Map.Entry<E, Node<E>> entry : this.adjacencyList.entrySet())
		{
			if(Objects.equals(entry.getKey().toString(), vertex.toString()))
			{
				return true;
			}
		}
		return false;
	}
	public Node<E> getVertex(E vertex)
	{
		return this.adjacencyList.get(vertex);
	}
	public Node<E> getNode(E value)
	{
		return this.adjacencyList.get(value);
	}
	
	public void resetGraph()
	{
		this.adjacencyList.keySet().forEach(key -> {
			Node<E> node = this.getNode(key);
			node.setParent(null);
			node.setVisited(false);
		});
	}
}
