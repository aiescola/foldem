package codes.derive.foldem.example;

import static codes.derive.foldem.Poker.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import codes.derive.foldem.Hand;
import codes.derive.foldem.Range;
import codes.derive.foldem.tool.HandMatrixBuilder;
import codes.derive.foldem.util.RandomContext;

/**
 * Generates a basic hand matrix image containing a random distribution of hands
 * and saves it to the local filesystem as range.png
 */
public class HandMatrixExample {

	public static void main(String... args) throws IOException {
		Range range = range();
		for (Hand hand : hands()) {
			range.define(RandomContext.get().nextDouble(), hand);
		}
		ImageIO.write((BufferedImage) new HandMatrixBuilder().build(range), "png", new File("range.png"));
	}
	
}
