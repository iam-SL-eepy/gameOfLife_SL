package models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Grid {

	private int MAX_ITERATIONS;

	private int BOARDER_X;

	private int BOARDER_Y;

	private boolean PRINT_STEPS = false;

	Map<Coordinate, Cell> cells = new ConcurrentHashMap<Coordinate, Cell>();

	public boolean hasBoarder() {
		return BOARDER_X > 0 && BOARDER_Y > 0;
	}

	public int getMAX_ITERATIONS() {
		return MAX_ITERATIONS;
	}

	public void setMAX_ITERATIONS(int mAX_ITERATIONS) throws Exception {
		MAX_ITERATIONS = mAX_ITERATIONS;
	}

	public void updateCells() {
		Iterator<Entry<Coordinate, Cell>> cellsItr = cells.entrySet().iterator();
		while (cellsItr.hasNext()) {
			Entry<Coordinate, Cell> cellPos = cellsItr.next();
			if (cellPos.getValue().getNeighborsNext() == 0 && !cellPos.getValue().alive()) {
				cellsItr.remove();
			} else {
				cellPos.getValue().setNeighbors(cellPos.getValue().getNeighborsNext());
			}
		}
	}

	public Map<Coordinate, Cell> getCells() {
		if (cells == null) {
			cells = new HashMap<Coordinate, Cell>();
		}

		return cells;
	}

	public void setCells(Map<Coordinate, Cell> cells) {
		this.cells = cells;
	}

	public int getBOARDER_X() {
		return BOARDER_X;
	}

	public void setBOARDER_X(int bOARDER_X) {
		BOARDER_X = bOARDER_X;
	}

	public int getBOARDER_Y() {
		return BOARDER_Y;
	}

	public void setBOARDER_Y(int bOARDER_Y) {
		BOARDER_Y = bOARDER_Y;
	}

	public boolean isPRINT_STEPS() {
		return PRINT_STEPS;
	}

	public void setPRINT_STEPS(boolean pRINT_STEPS) {
		PRINT_STEPS = pRINT_STEPS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + BOARDER_X;
		result = prime * result + BOARDER_Y;
		result = prime * result + MAX_ITERATIONS;
		result = prime * result + (PRINT_STEPS ? 1231 : 1237);
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
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
		Grid other = (Grid) obj;
		if (BOARDER_X != other.BOARDER_X)
			return false;
		if (BOARDER_Y != other.BOARDER_Y)
			return false;
		if (MAX_ITERATIONS != other.MAX_ITERATIONS)
			return false;
		if (PRINT_STEPS != other.PRINT_STEPS)
			return false;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		return true;
	}
}
