import java.util.Random;
import java.util.Scanner;

public class DataLoader {

	Scanner scanner = new Scanner(System.in);
	Random random = new Random();
	Particle particle;
	int n = 0;

	// in cm
	final double detectorSize = 42.4, detectorFreeSpaceHigh = 20;
	double xSampleSize, ySampleSize, zSampleSize;

	// in micro
	double minRange, maxRange;

	double[] particleCoor = new double[3];

	int numberOfTry, number = 0;
	static int numberOfDetected;

	DataLoader(String string, String string2, String string3, String string4, String string5) {
		xSampleSize = Double.parseDouble(string);
		ySampleSize = Double.parseDouble(string2);
		numberOfTry = 1000 * Integer.parseInt(string3);
		minRange = Double.parseDouble(string4);
		maxRange = Double.parseDouble(string5);

		numberOfDetected = 0;

		for (int i = 1; i <= numberOfTry; i++) {
			n++;
			if (n % 50000 == 0) {
				double x = (double) numberOfDetected / n * 100000.0;
				Loader2.addTextToBigField(
						String.valueOf(n) + ": " + String.valueOf(100 * Math.round(x) / 100000.0) + "%");

			}

			particle = new Particle(xSampleSize, ySampleSize, zSampleSize, minRange, maxRange, detectorSize,
					detectorFreeSpaceHigh);
		}

		double x = (double) numberOfDetected / numberOfTry * 100000.0;
		Loader2.addTextToBigField(
				"Percentage of detected particles: " + String.valueOf(100 * Math.round(x) / 100000.0) + "%");

		n = 0;
	}

	static void addNumberOfDetected() {
		numberOfDetected++;
	}
}
