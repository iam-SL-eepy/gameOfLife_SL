package helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import models.Cell;
import models.Coordinate;
import models.Grid;
import models.State;

public class HelperTest {

	Helper uut;

	@Before
	public void setup() {
		uut = new Helper();
	}

	@Test
	public void ensureInitGridCanInitGridWithCorrectValues() throws Exception {
		String actualInPath = "input/singleDot.txt";

		Grid actual = uut.initGrid(actualInPath);

		Grid expected = new Grid();
		expected.setBOARDER_X(2);
		expected.setBOARDER_Y(3);
		expected.setMAX_ITERATIONS(1);
		expected.setPRINT_STEPS(false);
		expected.getCells().put(new Coordinate(0, 0), createCell(State.DEAD, 1, 1));
		expected.getCells().put(new Coordinate(0, 1), createCell(State.DEAD, 1, 1));
		expected.getCells().put(new Coordinate(0, 2), createCell(State.DEAD, 1, 1));
		expected.getCells().put(new Coordinate(1, 0), createCell(State.DEAD, 1, 1));
		expected.getCells().put(new Coordinate(1, 1), createCell(State.ALIVE, 0, 0));
		expected.getCells().put(new Coordinate(1, 2), createCell(State.DEAD, 1, 1));

		assertEquals(actual, expected);
	}

	@Test
	public void ensureCalculateStatesChangeGridIntoNextState() throws Exception {
		String actualInPath = "input/threeDots.txt";

		Grid actual = uut.initGrid(actualInPath);

		uut.calculateStates(actual);

		Grid expected = new Grid();
		expected.setBOARDER_X(2);
		expected.setBOARDER_Y(3);
		expected.setMAX_ITERATIONS(1);
		expected.setPRINT_STEPS(false);
		expected.getCells().put(new Coordinate(0, 0), createCell(State.DEAD, 2, 2));
		expected.getCells().put(new Coordinate(0, 1), createCell(State.ALIVE, 3, 3));
		expected.getCells().put(new Coordinate(0, 2), createCell(State.ALIVE, 3, 3));
		expected.getCells().put(new Coordinate(1, 0), createCell(State.DEAD, 2, 2));
		expected.getCells().put(new Coordinate(1, 1), createCell(State.ALIVE, 3, 3));
		expected.getCells().put(new Coordinate(1, 2), createCell(State.ALIVE, 3, 3));

		assertEquals(actual, expected);
	}

	@SuppressWarnings("static-access")
	@Test
	public void ensureUpdateNeighborCellsDoesNotUpdateNeigborsOutOfFieldWhenOnBoarder() throws Exception {
		String actualInPath = "input/threeDots.txt";

		Grid grid = uut.initGrid(actualInPath);

		uut.updateNeighborCells(new Coordinate(0, 1), new Cell(State.ALIVE), grid);

		assertNull(grid.getCells().get(new Coordinate(-1, 0)));
		assertNull(grid.getCells().get(new Coordinate(-1, 1)));
		assertNull(grid.getCells().get(new Coordinate(-1, 2)));
		assertNotNull(grid.getCells().get(new Coordinate(0, 0)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void ensureUpdateNeighborCellsDoesNotUpdateNeigborsOutOfFieldUpdateWhenOnCorners() throws Exception {
		String actualInPath = "input/threeDots.txt";

		Grid grid = uut.initGrid(actualInPath);

		uut.updateNeighborCells(new Coordinate(0, 0), new Cell(State.ALIVE), grid);

		assertNull(grid.getCells().get(new Coordinate(-1, -1)));
		assertNull(grid.getCells().get(new Coordinate(-1, 0)));
		assertNull(grid.getCells().get(new Coordinate(-1, 1)));
		assertNull(grid.getCells().get(new Coordinate(0, -1)));
		assertNull(grid.getCells().get(new Coordinate(1, -1)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void ensureIsCellWithinBoarderReturnsTrueWhenCellIsWithinBoarders() {
		Grid grid = new Grid();
		grid.setBOARDER_X(2);
		grid.setBOARDER_Y(2);

		assertTrue(uut.isCellWithinBoarder(grid, 0, 0));
		assertTrue(uut.isCellWithinBoarder(grid, 1, 1));
	}

	@SuppressWarnings("static-access")
	@Test
	public void ensureIsCellWithinBoarderReturnsFalseWhenCellIsOutofBoarders() {
		Grid grid = new Grid();
		grid.setBOARDER_X(2);
		grid.setBOARDER_Y(2);

		assertFalse(uut.isCellWithinBoarder(grid, -5, 1));
		assertFalse(uut.isCellWithinBoarder(grid, 6, 0));
	}

	private Cell createCell(State state, int neighbors, int neighborsNext) {
		Cell cell = new Cell(state);
		cell.setNeighbors(neighbors);
		cell.setNeighborsNext(neighborsNext);

		return cell;
	}

	@Test
	public void ensureGameOfLifeHasCorrectResultCaseOne() throws Exception {
		String actualInPath = "input/blink.txt";
		String actualOutPath = "src/test/resources/output/blinkActualOut.txt";
		String expectedOutPath = "src/test/resources/output/blinkExpectedOut.txt";

		gameOfLifeSim(actualInPath, actualOutPath);

		byte[] actual = Files.readAllBytes(Paths.get(actualOutPath));
		byte[] expected = Files.readAllBytes(Paths.get(expectedOutPath));

		assertEquals(new String(actual), new String(expected));
	}

	@Test
	public void ensureGameOfLifeHasCorrectResultCaseTwo() throws IOException, Exception {
		String actualInPath = "input/beacon.txt";
		String actualOutPath = "src/test/resources/output/beaconActualOut.txt";
		String expectedOutPath = "src/test/resources/output/beaconExpectedOut.txt";

		gameOfLifeSim(actualInPath, actualOutPath);

		byte[] actual = Files.readAllBytes(Paths.get(actualOutPath));
		byte[] expected = Files.readAllBytes(Paths.get(expectedOutPath));

		assertEquals(new String(actual), new String(expected));
	}

	@Test
	public void ensureGameOfLifeHasCorrectResultCaseThree() throws IOException, Exception {
		String actualInPath = "input/custom.txt";
		String actualOutPath = "src/test/resources/output/customActualOut.txt";
		String expectedOutPath = "src/test/resources/output/customExpectedOut.txt";

		gameOfLifeSim(actualInPath, actualOutPath);

		byte[] actual = Files.readAllBytes(Paths.get(actualOutPath));
		byte[] expected = Files.readAllBytes(Paths.get(expectedOutPath));

		assertEquals(new String(actual), new String(expected));
	}

	private void gameOfLifeSim(String inPath, String outPath) throws Exception, IOException {
		Grid golGrid = uut.initGrid(inPath);

		BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));

		if (golGrid.hasBoarder()) {
			writer.write("init:");
			writer.newLine();
			uut.printGrid(golGrid, writer);

			uut.processPrediction(golGrid, writer);

			writer.write("final:");
			writer.newLine();
			uut.printGrid(golGrid, writer);
		} else {
			writer.write("not supporting game without boarders.");
		}

		writer.close();
	}
}
