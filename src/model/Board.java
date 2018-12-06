package model;

import java.util.List;

public interface Board<T> {
	public List<T> getPossibleMoves(T board);
}
