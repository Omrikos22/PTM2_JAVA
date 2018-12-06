package model;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class Node<E> {
	public E vertex;
	private List<Edge<E>> edges;
	private Node<E> parent;
	private boolean isVisited;
	
	public Node(E vertex){
		this.vertex = vertex;
		this.edges = new ArrayList<>();
	}
	public boolean addEdge(Node<E> node ,int weight)
	{
		if(hasEdge(node))
		{
			return false;
		}
		Edge<E> newEdge = new Edge<>(this, node, weight);
		return this.edges.add(newEdge);
	}
	public boolean hasEdge(Node<E> node)
	{
		return findEdge(node).isPresent();
	}
	private Optional<Edge<E>> findEdge(Node<E> node)
	{
		return this.edges.stream()
				.filter(edge -> edge.isBetween(this, node))
				.findFirst();
	}
	public List<Edge<E>> edges() {
		return this.edges;
	}
	public void setParent(Node<E> parent)
	{
		this.parent = parent;
	}
	public void setVisited(boolean isVisited)
	{
		this.isVisited = isVisited;
	}
	public boolean isVisited()
	{
		return this.isVisited;
	}
	public E vertex()
	{
		return this.vertex;
	}
	public Node<E> parent()
	{
		return this.parent;
	}
}
