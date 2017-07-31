import java.util.Random;

public class Particle {

	Random random = new Random();

	double[] particleStartingCoor = new double[3];
	double[] particleStartingArgonCoor = new double[3];
	double[] particleCoor = new double[3];
	double xSampleSize, ySampleSize, zSampleSize, min, max;
	double[] vector = new double[3];
	double distanceTravelled;
	boolean detected;
	double detectorSize, detectorFreeSpaceHigh;

	// in MeV
	double[] energy = { 4.50, 4.24, 3.96, 3.68, 3.37, 3.05, 2.74, 2.42, 2.04, 1.25};
	// in microns
	double[] minimumDistanceTravelledInArgonToDetected = { 9930.0, 9560.0, 9225.0, 9090.0, 8333.0, 7813.0, 7421.0,
			6849.0, 6289.0, 5370.0};
	double distanceInArgon = 0.0, distanceTravelledInArgon;

	boolean exitCube = true;

	// energy of particle in MeV
	double energyOfParticle = 0.0;

	Particle(double xSampleSize, double ySampleSize, double zSampleSize, double min, double max, double detectorSize,
			double detectorFreeSpaceHigh) {
		this.xSampleSize = xSampleSize;
		this.ySampleSize = xSampleSize;
		this.zSampleSize = xSampleSize;
		this.min = min;
		this.max = max;
		this.detectorSize = detectorSize;
		this.detectorFreeSpaceHigh = detectorFreeSpaceHigh;

		createDecay();
		flyInRandomDirection();
		calcTravel();
		checkIfDetected();
		// printCoordinates();
	}

	private void createDecay() {
		particleCoor[0] = ((detectorSize - xSampleSize) / 2) + random.nextDouble() * xSampleSize;
		particleCoor[1] = ((detectorSize - ySampleSize) / 2) + random.nextDouble() * ySampleSize;
		particleCoor[2] = -random.nextDouble() * max / 10000;

		particleStartingCoor[0] = particleCoor[0];
		particleStartingCoor[1] = particleCoor[1];
		particleStartingCoor[2] = particleCoor[2];
	}

//	private void printCoordinates() {
//		System.out.println("Startowe koordynaty: {" + particleStartingCoor[0] + ", " + particleStartingCoor[1] + ", "
//				+ particleStartingCoor[2] + "}. Koñcowe koordynaty: {" + particleCoor[0] + ", " + particleCoor[1] + ", "
//				+ particleCoor[2] + "}. \n Energia po przejœciu przez próbkê: " + energyOfParticle
//				+ "MeV, po przejœciu w próbce " + distanceTravelled + ". W Argonie przesz³a: "
//				+ distanceTravelledInArgon);
//
//	}

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
		if (distanceTravelled < max && distanceTravelled > min) {
			checEnergyOfParticleAfterSampleExit();
			checkTravelThroughArgon();
			if (exitCube == false)
				DataLoader.addNumberOfDetected();
		}
	}

	private void checkTravelThroughArgon() {
		particleStartingArgonCoor[0] = particleCoor[0];
		particleStartingArgonCoor[1] = particleCoor[1];
		particleStartingArgonCoor[2] = particleCoor[2];

		int n = 0;
		do {
			n++;
			particleCoor[0] += (vector[0] / 10000);
			particleCoor[1] += (vector[1] / 10000);
			particleCoor[2] += (vector[2] / 10000);
		} while (n < distanceInArgon * 1.01 && particleCoor[1] > 0 && particleCoor[1] < detectorSize
				&& particleCoor[0] > 0 && particleCoor[0] < detectorSize);

		distanceTravelledInArgon = (Math.sqrt(Math.pow(particleCoor[0] - particleStartingArgonCoor[0], 2)
				+ Math.pow(particleCoor[1] - particleStartingArgonCoor[1], 2)
				+ Math.pow(particleCoor[2] - particleStartingArgonCoor[2], 2))) * 10000;

		if (distanceTravelledInArgon >= distanceInArgon)
			exitCube = false;

	}

	private void checEnergyOfParticleAfterSampleExit() {
		if (distanceTravelled >= min && distanceTravelled < (min + (max - min) * 0.1)) {
			energyOfParticle = energy[0];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[0];
		} else if (distanceTravelled >= (min + (max - min) * 0.1) && distanceTravelled < (min + (max - min) * 0.2)) {
			energyOfParticle = energy[1];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[1];
		} else if (distanceTravelled >= (min + (max - min) * 0.2) && distanceTravelled < (min + (max - min) * 0.3)) {
			energyOfParticle = energy[2];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[2];
		} else if (distanceTravelled >= (min + (max - min) * 0.3) && distanceTravelled < (min + (max - min) * 0.4)) {
			energyOfParticle = energy[3];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[3];
		} else if (distanceTravelled >= (min + (max - min) * 0.4) && distanceTravelled < (min + (max - min) * 0.5)) {
			energyOfParticle = energy[4];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[4];
		} else if (distanceTravelled >= (min + (max - min) * 0.5) && distanceTravelled < (min + (max - min) * 0.6)) {
			energyOfParticle = energy[5];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[5];
		} else if (distanceTravelled >= (min + (max - min) * 0.6) && distanceTravelled < (min + (max - min) * 0.7)) {
			energyOfParticle = energy[6];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[6];
		} else if (distanceTravelled >= (min + (max - min) * 0.7) && distanceTravelled < (min + (max - min) * 0.8)) {
			energyOfParticle = energy[7];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[7];
		} else if (distanceTravelled >= (min + (max - min) * 0.8) && distanceTravelled < (min + (max - min) * 0.9)) {
			energyOfParticle = energy[8];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[8];
		} else if (distanceTravelled >= (min + (max - min) * 0.9) && distanceTravelled <= (min + (max - min) * 1)) {
			energyOfParticle = energy[9];
			distanceInArgon = minimumDistanceTravelledInArgonToDetected[9];
		}

	}

}
