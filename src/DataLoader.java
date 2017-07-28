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

	DataLoader() {
		numberOfDetected = 0;

		System.out.println("The sample X size (in cm):");
		xSampleSize = scanner.nextDouble();

		System.out.println("The sample Y size (in cm):");
		ySampleSize = scanner.nextDouble();

		System.out.println("Minimum distance travelled in the sample (in microns):");
		minRange = scanner.nextDouble();

		System.out.println("Maximum distance travelled in the sample (in microns):");
		maxRange = scanner.nextDouble();

		System.out.println("Number of particles generated (in thousands):");
		numberOfTry = 1000 * scanner.nextInt();

		for (int i = 1; i <= numberOfTry; i++) {
			n++;
			if (n % 10000 == 0) {
				double x = (double) numberOfDetected / n * 100000.0;
				System.out.println(n + ": " + 100 * Math.round(x) / 100000.0 + "%");
			}

			particle = new Particle(xSampleSize, ySampleSize, zSampleSize, minRange, maxRange, detectorSize,
					detectorFreeSpaceHigh);
		}

		double x = (double) numberOfDetected / numberOfTry * 100000.0;
		System.out.println("Percentage of detected particles: " + 100 * Math.round(x) / 100000.0 + "%");
	}

	static void addNumberOfDetected() {
		numberOfDetected++;
	}
}
