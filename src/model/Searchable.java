package model;

import java.util.List;

public interface Searchable<T> {
	public Graph<T> getGraph();
	public T getStartVertex();
	public T getGoalVertex();

}
