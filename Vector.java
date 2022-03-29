
public class Vector { // (ray)
	Point p;
	Point dir;

	public Vector(Point p, Point dir) {
		double magnitudeDir = Math.sqrt(dir.x * dir.x + dir.y * dir.y + dir.z * dir.z); // making Vector a unit vector
		dir.x = dir.x / magnitudeDir;
		dir.y = dir.y / magnitudeDir;
		dir.z = dir.z / magnitudeDir;

		this.p = p;
		this.dir = dir;

		// point, direction
	}

//	public Vector(Point p, Point dir, boolean unit) {
//		this.p = p;
//		this.dir = dir;
//
//		// point, direction
//	}

	public Point pointAtTime(double t) {
		// parametric equations
		// position = initial position (vector) + velocity (vector) * t
		Point out = new Point(0, 0, 0);
		out.x = p.x + dir.x * t;
		out.y = p.y + dir.y * t;
		out.z = p.z + dir.z * t;
		return out;
	}

	public double dot(Vector v) { // dot product
		return this.dir.x * v.dir.x + this.dir.y * v.dir.y + this.dir.z * v.dir.z;
	}

	// reflect function
	public Vector reflected(Vector v) { // v is the normal vector
		// reflected = incoming - 2 * normal * (incoming dot normal)
		double dot = (this.dot(v));
		Point pos = new Point(0, 0, 0);
		Point dir = new Point(this.dir.x - 2 * v.dir.x * dot, this.dir.y - 2 * v.dir.y * dot,
				this.dir.z - 2 * v.dir.z * dot);

		Vector out = new Vector(pos, dir);
		return out;

	}

}
