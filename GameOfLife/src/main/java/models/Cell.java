package models;

public class Cell {

	private State state = State.DEAD;

	private int neighbors = 0;
	
	private int neighborsNext = 0;

	public Cell(State state) {
		super();
		this.state = state;
	}

	public boolean alive() {
		return state == State.ALIVE;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(int neighbors) {
		this.neighbors = neighbors;
	}
	
	public void increaseNeighborsNext() {
		this.neighborsNext++;
	}
	
	public void decreaseNeighborsNext() {
		this.neighborsNext--;
	}

	public int getNeighborsNext() {
		return neighborsNext;
	}

	public void setNeighborsNext(int neighborsNext) {
		this.neighborsNext = neighborsNext;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + neighbors;
		result = prime * result + neighborsNext;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (neighbors != other.neighbors)
			return false;
		if (neighborsNext != other.neighborsNext)
			return false;
		if (state != other.state)
			return false;
		return true;
	}

}
