import java.util.Random;

public class HorizontalPlane extends WorldObject {
	double y; // horizontal plane

	public HorizontalPlane(double y) {
		this.y = y;
	}

	public double[] vectorIntersectionTime(Vector v) {
		double vHeightAbovePlane = v.p.y - y;
		double yVelocity = v.dir.y;
		if (yVelocity == 0) {
			return new double[] {};
		}
		double tim = -1 / yVelocity * vHeightAbovePlane;
		if (tim < 0) {
			return new double[] {};
		} else {
			return new double[] { tim };
		}
	}

	public void colorAtPoint(int[] rgb, Point p) {
//		if ((int) p.x / 50 % 2 == 0) {
//			rgb[0] = 255;
//			rgb[1] = 255;
//			rgb[2] = 255;
//		} else {
//			rgb[0] = 0;
//			rgb[1] = 0;
//			rgb[2] = 0;
//		}
		int dark = 255;
		int light = 0;
		int checkerSize = 100;
		double distToOrigin = Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
		double colorChangeVal = 1000.0;
		double colorChange = 1 / (1 + (distToOrigin / colorChangeVal));

		light = (int) (127 + 127 * colorChange);
		dark = (int) (127 - 127 * colorChange);

//		if (distToOrigin > 1000) {
//			dark = 205;
//		} else if (distToOrigin > 1000) {
//			light = 155;
//			dark = 100;
//		}

		// should be dark/white based off of odd/even numbered square
		if ((((int) (p.x + 1000000) / checkerSize % 2 == 0) && ((int) p.z / checkerSize % 2 == 0))
				|| (((int) (p.x + 1000000) / checkerSize % 2 != 0) && ((int) p.z / checkerSize % 2 != 0))) {
//			rgb[0] = dark;
//			rgb[1] = dark;
//			rgb[2] = dark;
			rgb[0] = dark;
			rgb[1] = Math.abs((int) (p.z % 255));
			rgb[2] = Math.abs((int) (p.x % 255));
		} else {
			rgb[0] = light;
			rgb[1] = light;
			rgb[2] = light;

		}
	}

	// takes in a vector and returns the reflected vector
	public Vector ref(Vector v) {
		Random randy = new Random();
		double randDubX = randy.nextDouble();
		double randDubZ = randy.nextDouble();
		double randomVocusVal = 1;
		double SinVal = Math.sin(v.p.x / 10 + v.p.z / 20);
		double SinValDivisor = 6;
		Point dirFromCenter = new Point(0, 1, 0); // normal vector
//		Point dirFromCenter = new Point((randDubX - 0.5) / randomVocusVal + SinVal / SinValDivisor, 1,
//				(randDubZ - 0.5) / randomVocusVal); // normal
		// vector
		Vector unitFromCenter = new Vector(new Point(0, 0, 0), dirFromCenter);
		Vector temp = v.reflected(unitFromCenter);
		temp.p.x = v.p.x;
		temp.p.y = v.p.y;
		temp.p.z = v.p.z;
		return temp;
//		Vector temp = v;
//		temp.p.x = v.p.x;
//		temp.p.y = v.p.y;
//		temp.p.z = v.p.z;
//		temp.dir.y = -1 * v.dir.y;
//		return temp;
	}
}
