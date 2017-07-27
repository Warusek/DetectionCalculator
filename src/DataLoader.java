import java.util.Random;

public class DataLoader {

	Random random = new Random();
	Particle particle;

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
		xSampleSize = 10;
		ySampleSize = 10;
		//zSampleSize = 10;
		numberOfTry = 1000000;

		minRange = 3.3;
		maxRange = 13.3;

		for (int i = 1; i <= numberOfTry; i++) {
			particle = new Particle(xSampleSize, ySampleSize, zSampleSize, minRange, maxRange);
			number++;
		}

		double x = (double) numberOfDetected / numberOfTry * 1.0;
		System.out.println(x);
		System.out.println(number);
	}

	static void addNumberOfDetected() {
		numberOfDetected++;
	}
}
