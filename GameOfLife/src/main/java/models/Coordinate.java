package models;

public class Coordinate {

	private int X;

	private int Y;

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public Coordinate(int x, int y) {
		super();
		X = x;
		Y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + X;
		result = prime * result + Y;
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
		Coordinate other = (Coordinate) obj;
		if (X != other.X)
			return false;
		if (Y != other.Y)
			return false;
		return true;
	}
}
