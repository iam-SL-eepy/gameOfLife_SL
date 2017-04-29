package helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import models.Cell;
import models.Coordinate;
import models.Grid;
import models.State;

public class Helper {
	
	private static final String STATE_ALIVE = "+"; 

	public Grid initGrid(String path) throws Exception {
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		Scanner scanner = new Scanner(inStream);
		Grid grid = new Grid();

		readParams(scanner, grid);
		if (grid.hasBoarder()) {
			readGrid(scanner, grid);
		}
		scanner.close();

		return grid;
	}

	private static void readGrid(Scanner scanner, Grid grid) {
		int currentX = 0;
		scanner.nextLine();
		Scanner lineScanner = null;
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			lineScanner = new Scanner(nextLine);
			int currentY = 0;
			while (lineScanner.hasNext()) {
				if (lineScanner.next().equals(STATE_ALIVE)) {
					if (isCellWithinBoarder(grid, currentX, currentY)) {
						if (grid.getCells().get(new Coordinate(currentX, currentY)) == null) {
							grid.getCells().put(new Coordinate(currentX, currentY), new Cell(State.ALIVE));
						} else {
							grid.getCells().get(new Coordinate(currentX, currentY)).setState(State.ALIVE);
						}
						updateNeighborCells(new Coordinate(currentX, currentY),
								grid.getCells().get(new Coordinate(currentX, currentY)), grid);
					}
				}
				currentY++;
			}
			currentX++;
		}
		grid.updateCells();

		lineScanner.close();
	}

	private static void readParams(Scanner scanner, Grid grid) throws Exception {
		scanner.nextLine();
		grid.setMAX_ITERATIONS(scanner.nextInt());
		grid.setBOARDER_X(scanner.nextInt());
		grid.setBOARDER_Y(scanner.nextInt());
		grid.setPRINT_STEPS(scanner.next().equalsIgnoreCase("y") ? true : false);
		scanner.nextLine();
	}

	public void processPrediction(Grid grid, BufferedWriter writer) throws IOException {
		for (int itr = 0; itr < grid.getMAX_ITERATIONS(); itr++) {
			calculateStates(grid);
			if (grid.isPRINT_STEPS()) {
				writer.write("step " + itr + ":");
				writer.newLine();
				printGrid(grid, writer);
			}
		}
	}

	protected void calculateStates(Grid grid) {
		if (!grid.getCells().isEmpty()) {
			Iterator<Entry<Coordinate, Cell>> cellsItr = grid.getCells().entrySet().iterator();
			while (cellsItr.hasNext()) {
				Entry<Coordinate, Cell> cellPos = cellsItr.next();
				if (cellPos.getValue() != null) {
					if (cellPos.getValue().getNeighbors() != 2 && cellPos.getValue().getNeighbors() != 3) {
						if (cellPos.getValue().alive()) {
							cellPos.getValue().setState(State.DEAD);
							updateNeighborCells(cellPos.getKey(), cellPos.getValue(), grid);
						}
					} else if (cellPos.getValue().getNeighbors() == 3) {
						if (!cellPos.getValue().alive()) {
							cellPos.getValue().setState(State.ALIVE);
							updateNeighborCells(cellPos.getKey(), cellPos.getValue(), grid);
						}
					}
				}
			}
			grid.updateCells();
		}
	}

	protected static void updateNeighborCells(Coordinate coordinate, Cell cell, Grid grid) {
		// only the cells with state change will need to update neighbors
		int neighbor_x = coordinate.getX() - 1;

		while (neighbor_x <= coordinate.getX() + 1) {
			int neighbor_y = coordinate.getY() - 1;

			while (neighbor_y <= coordinate.getY() + 1) {
				if (neighbor_x != coordinate.getX() || neighbor_y != coordinate.getY()) {
					if (isCellWithinBoarder(grid, neighbor_x, neighbor_y)) {
						if (grid.getCells().get(new Coordinate(neighbor_x, neighbor_y)) != null) {
							if (cell.alive()) {
								grid.getCells().get(new Coordinate(neighbor_x, neighbor_y)).increaseNeighborsNext();
							} else {
								grid.getCells().get(new Coordinate(neighbor_x, neighbor_y)).decreaseNeighborsNext();
							}
						} else if (cell.alive()) {
							grid.getCells().put(new Coordinate(neighbor_x, neighbor_y), new Cell(State.DEAD));
							grid.getCells().get(new Coordinate(neighbor_x, neighbor_y)).increaseNeighborsNext();
						}
					}
				}
				neighbor_y++;
			}
			neighbor_x++;
		}
	}

	protected static boolean isCellWithinBoarder(Grid grid, int neighbor_x, int neighbor_y) {
		return neighbor_x >= 0 && neighbor_y >= 0 && neighbor_x < grid.getBOARDER_X()
				&& neighbor_y < grid.getBOARDER_Y();
	}

	public void printGrid(Grid golGrid, BufferedWriter writer) throws IOException {
		for (int row = 0; row < golGrid.getBOARDER_X(); row++) {
			for (int col = 0; col < golGrid.getBOARDER_Y(); col++) {
				Cell currentCell = golGrid.getCells().get(new Coordinate(row, col));
				if (currentCell != null && currentCell.alive()) {
					writer.write("+ ");
				} else {
					writer.write("0 ");
				}
			}
			writer.newLine();
		}

		writer.write("=============================================================");
		writer.newLine();
	}

}
