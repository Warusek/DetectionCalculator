import java.util.Random;

public class Particle {

	Random random = new Random();

	double[] particleStartingCoor = new double[3];
	double[] particleCoor = new double[3];
	double xSampleSize, ySampleSize, zSampleSize, min, max;
	double[] vector = new double[3];
	double distanceTravelled;
	boolean detected;

	Particle(double xSampleSize, double ySampleSize, double zSampleSize, double min, double max) {

		this.xSampleSize = xSampleSize;
		this.ySampleSize = xSampleSize;
		this.zSampleSize = xSampleSize;
		this.min = min;
		this.max = max;

		createDecay();
		// printCoordinates();
		flyInRandomDirection();
		calcTravel();
		checkIfDetected();
		printCoordinates();
	}

	private void createDecay() {
		particleCoor[0] = random.nextDouble() * xSampleSize;
		particleCoor[1] = random.nextDouble() * ySampleSize;
		particleCoor[2] = -random.nextDouble() * max / 10000;

		particleStartingCoor[0] = particleCoor[0];
		particleStartingCoor[1] = particleCoor[1];
		particleStartingCoor[2] = particleCoor[2];
	}

	private void printCoordinates() {
		// System.out.println(
		// "{" + particleStartingCoor[0] + ", " + particleStartingCoor[1] + ", "
		// + particleStartingCoor[2] + "}");
		// System.out.println("{" + particleCoor[0] + ", " + particleCoor[1] +
		// ", " + particleCoor[2] + "}");
//		System.out.println(vector[0] + " " + vector[1] + " " + vector[2]);
		//System.out.println(distanceTravelled);
	}

	private void flyInRandomDirection() {
		double horizontalAngle = random.nextDouble() * 2 * Math.PI;
		double verticalAngle = (random.nextDouble() * Math.PI);

		double x = Math.sin(verticalAngle) * Math.cos(horizontalAngle) + particleCoor[0];
		double y = Math.sin(verticalAngle) * Math.sin(horizontalAngle) + particleCoor[1];
		double z = Math.cos(verticalAngle) + particleCoor[2];

		vector[0] = (x - particleCoor[0]);
		vector[1] = (y - particleCoor[1]);
		vector[2] = (z - particleCoor[2]);
	}

	private void calcTravel() {
		int n = 0;
		if (vector[2] > 0) {
			do {
				n++;
				particleCoor[0] += (vector[0] / 10000000);
				particleCoor[1] += (vector[1] / 10000000);
				particleCoor[2] += (vector[2] / 10000000);
			} while (n < max * 1000 && !(particleCoor[2] > 0));
		}

		distanceTravelled = (Math.sqrt(Math.pow(particleCoor[0] - particleStartingCoor[0], 2)
				+ Math.pow(particleCoor[1] - particleStartingCoor[1], 2)
				+ Math.pow(particleCoor[2] - particleStartingCoor[2], 2))) * 10000;

		if (n == (max * 1000)) {
			distanceTravelled = max;
		}

	}

	private void checkIfDetected() {
		if (distanceTravelled < max && distanceTravelled >= min)
			DataLoader.addNumberOfDetected();
	}

}
