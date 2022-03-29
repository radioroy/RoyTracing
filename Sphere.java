// x^2 + y^2 + z^2 = r^2
// x, y, z position for a vector at time t
// position = initial position (vector) + velocity (vector) * t
//		out.x = p.x + dir.x * t;
//		out.y = p.y + dir.y * t;
//		out.z = p.z + dir.z * t;
//
// t = (px + dx*t - xShift)^2 + (py + dy*t - yShift)^2 + (pz + dz*t - zShift)^2 = r^2
// write in terms of the quadratic equation
//
public class Sphere extends WorldObject {
	double xShift = 0;
	double yShift = 0;
	double zShift = 0;
	double r = 0; // radius

	Point center = new Point(xShift, yShift, zShift);
	// Point lightDir = new Point(0.5, 1, 0);
	Point lightDir = new Point(0.5, 1, -0.5);
	Vector fromCenterToLight = new Vector(center, lightDir);

	public Sphere(double r) {
		this.r = r;
	}

	public Sphere(double xShift, double yShift, double zShift, double r) {
		this.xShift = xShift;
		this.yShift = yShift;
		this.zShift = zShift;
		this.r = r;
	}

	// takes in a vector and returns the reflected vector
	public Vector ref(Vector v) {
		Point dirFromCenter = new Point(v.p.x - xShift, v.p.y - yShift, v.p.z - zShift);
		Vector unitFromCenter = new Vector(center, dirFromCenter);
		Vector temp = v.reflected(unitFromCenter);
		temp.p.x = v.p.x;
		temp.p.y = v.p.y;
		temp.p.z = v.p.z;
		return temp;
	}

	public void colorAtPoint(int[] rgb, Point p) {
		Point dirFromCenter = new Point(p.x - xShift, p.y - yShift, p.z - zShift);
		Vector unitFromCenter = new Vector(center, dirFromCenter);

		double howMuchLight = fromCenterToLight.dot(unitFromCenter);

		// rgb[0] = 220;
		rgb[0] = 128 + (int) (128.0 * howMuchLight);
		rgb[1] = 0;
		rgb[2] = 0;

//		if (howMuchLight > 0.95) {
//			System.out.println(howMuchLight);
//			rgb[0] = 255;
//			rgb[1] = (int) (255.0 * howMuchLight);
//			rgb[2] = (int) (255.0 * howMuchLight);
//		}

//		 checkered sphere?
//		double checkerSize = 5;
//		// should be dark/white based off of odd/even numbered square
//		if ((((int) (p.x + 1000000) / checkerSize % 2 == 0) && ((int) p.z / checkerSize % 2 == 0))
//				|| (((int) (p.x + 1000000) / checkerSize % 2 != 0) && ((int) p.z / checkerSize % 2 != 0))) {
//			rgb[0] = 0;
//			rgb[1] = 0;
//			rgb[2] = 255;
//		} else {
//			rgb[0] = 200;
//			rgb[1] = 200;
//			rgb[2] = 200;
//
//		}
	}

	public double[] vectorIntersectionTime(Vector v) {
		// quadratic(a, b, c)
		// System.out.println("intsersection");

		double a = v.dir.x * v.dir.x + v.dir.y * v.dir.y + v.dir.z * v.dir.z;
		double b = 2.0 * (v.p.x * v.dir.x + v.p.y * v.dir.y + v.p.z * v.dir.z - v.dir.x * xShift - v.dir.y * yShift
				- v.dir.z * zShift);
		double c = v.p.x * v.p.x - 2 * v.p.x * xShift + xShift * xShift + v.p.y * v.p.y - 2 * v.p.y * yShift
				+ yShift * yShift + v.p.z * v.p.z - 2 * v.p.z * zShift + zShift * zShift - r * r;

		boolean t = quadratic(a, b, c);
		return quad;
	}

	//
	// ...... -b +- sqrt(b^2 - 4ac)
	// x = -----------------------
	// ....... 2a
	double[] quad;

	public boolean quadratic(double a, double b, double c) {
		double disc = b * b - 4 * a * c; // discriminant
		if (disc < 0) {
			quad = new double[0];
			return false; // can't do imaginary numbers in java
		} else if (disc == 0) {
			quad = new double[1];
			quad[0] = -b / 2 * a;
		} else {
			quad = new double[2];
			quad[1] = (-b + Math.sqrt(disc)) / 2 * a; // + solution
			quad[0] = (-b - Math.sqrt(disc)) / 2 * a; // - solution
			return true;
		}
		return true;
	}

}
