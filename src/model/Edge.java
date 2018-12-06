package model;

public class Edge<E> {
	private Node<E> node1;
	private Node<E> node2;
	private int weight;
	
	public Edge(Node<E> node1, Node<E> node2, int weight)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	public boolean isBetween(Node<E> node1, Node<E> node2)
	{
		return (this.node1 == node1 && this.node2 == node2);
	}
	
	public Node<E> fromNode()
	{
		return this.node1;
	}
	public Node<E> toNode()
	{
		return this.node2;
	}

}
