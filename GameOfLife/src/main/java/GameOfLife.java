// please fell free to contact me if anything is not clear or you have any issue running the simulation
// dev: Long Shi
// date: 04182017
// email: iam.sl.eepy3418@gmail.com
// cell: 567-208-0868

// input params and grid can be edited in sampleInput.txt at src/main/resources
// output can be checked in sampleOut.txt at root
// additional examples can be found in unit tests

import java.io.BufferedWriter;
import java.io.FileWriter;

import helpers.Helper;
import models.Grid;

public class GameOfLife {

	public static void main(String[] args) throws Exception {

		Helper helper = new Helper();

		Grid golGrid = helper.initGrid("sampleInput.txt");

		BufferedWriter writer = new BufferedWriter(new FileWriter("sampleOut.txt"));

		if (golGrid.hasBoarder()) {
			writer.write("init:");
			writer.newLine();
			helper.printGrid(golGrid, writer);

			helper.processPrediction(golGrid, writer);

			writer.write("final:");
			writer.newLine();
			helper.printGrid(golGrid, writer);
		} else {
			writer.write("not supporting game without boarders.");
		}

		writer.close();
	}

}
