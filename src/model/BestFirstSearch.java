package model;

public class BestFirstSearch
{
	
}
//import java.util.Comparator;
//import java.util.InputMismatchException;
//import java.util.PriorityQueue;
//import java.util.Scanner;
// 
//public class BestFirstSearch
//{
//    private PriorityQueue<Vertex> priorityQueue;
//    private int heuristicvalues[];
//    private int numberOfNodes;
// 
//    public static final int MAX_VALUE = 999;
// 
//    public BestFirstSearch(int numberOfNodes)
//    {
//        this.numberOfNodes = numberOfNodes;
//        this.priorityQueue = new PriorityQueue<Vertex>(this.numberOfNodes,
//        new Vertex());
//    }
// 
//    public void bestFirstSearch(int adjacencyMatrix[][], int[] heuristicvalues,int source)
//    {
//        int evaluationNode;
//        int destinationNode;
//        int visited[] = new int [numberOfNodes + 1];
//        this.heuristicvalues = heuristicvalues;
// 
//        priorityQueue.add(new Vertex(source, this.heuristicvalues[source]));
//        visited[source] = 1;
// 
//        while (!priorityQueue.isEmpty())
//        {
//            evaluationNode = getNodeWithMinimumHeuristicValue();
//            destinationNode = 1;
// 
//            System.out.print(evaluationNode + "\t");			
//            while (destinationNode <= numberOfNodes)
//            {
//                Vertex vertex = new Vertex(destinationNode,this.heuristicvalues[destinationNode]);
//                if ((adjacencyMatrix[evaluationNode][destinationNode] != MAX_VALUE 			                                           
//                      && evaluationNode != destinationNode)&& visited[destinationNode] == 0)
//                {
//                    priorityQueue.add(vertex);
//                    visited[destinationNode] = 1;
//                }
//                destinationNode++;
//            }
//        }
//    }
// 
//    private int getNodeWithMinimumHeuristicValue()
//    {
//        Vertex vertex = priorityQueue.remove();
//        return vertex.node;
//    }