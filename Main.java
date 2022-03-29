
//RoyTracing
import java.util.Random; // "ray marching"

public class Main {
	// test vectors
	Vector normalTest = new Vector(new Point(0, 0, 0), new Point(0, 1, 1));
	Vector incidentTest = new Vector(new Point(0, 0, 0), new Point(0, 1, 0));

	// frameSize
	int xSize = 300 * 3; // x dimension
	int ySize = 200 * 3; // y dimension
	Point camera = new Point(xSize / 2, ySize / 2 + 0.01, -600); // point representing camera ... z -300

	HorizontalPlane hp = new HorizontalPlane(0); // horizontal plane at z = 0

	Sphere s = new Sphere(xSize / 2 - 222, ySize / 2 - 100, 200, ySize / 2 + -100);
	Sphere s2 = new Sphere(xSize / 2 + 70, ySize / 2, 200, 100);
	Sphere s3 = new Sphere(xSize / 2 - 400, ySize / 2 + 500, 500, 100);
	Sphere s4 = new Sphere(xSize / 2 - 300, ySize / 2, -100, 30);
	Sphere s5 = new Sphere(xSize / 2 + 800, ySize / 2 + 400, 100, 600);
	Point p3 = new Point(xSize / 2, ySize / 2, 0);
	Point p4 = new Point(0, 0.70710, 0.70710);
	Vector v2 = new Vector(p3, p4);

	public static void main(String[] args) {
		Main prog = new Main(); // to avoid "static" conflictions
		prog.start();
	}

	// TODO Auto-generated method stub
	public void start() {
		s.refCoef = 1.0; // sphere coefficient = 1
		s2.refCoef = 0.0;
		s3.refCoef = 0.0;
		s4.refCoef = 0.0;
		s5.refCoef = 1.0; // 1
		hp.refCoef = 0.0;
		//
		Vector testRef = incidentTest.reflected(normalTest);
		System.out.println("Reflection  --  x: " + testRef.dir.x + "  y: " + testRef.dir.y + "  z: " + testRef.dir.z);

		int[][] rCordsTest = new int[ySize][xSize]; // red value coordinates
		int[][] gCordsTest = new int[ySize][xSize]; // green value coordinates
		int[][] bCordsTest = new int[ySize][xSize]; // blue value coordinates

//		randFillArr(rCordsTest);
//		randFillArr(gCordsTest);
//		randFillArr(bCordsTest);

		// HorizPlaneMap(rCordsTest, gCordsTest, bCordsTest);

		// Checkered Plane:
		// HorizPlaneCheckered(rCordsTest, gCordsTest, bCordsTest); // horizontal plane

		WorldObject[] objects = new WorldObject[6];
		objects[0] = hp;
		objects[1] = s;
		objects[2] = s2;
		objects[3] = s3;
		objects[4] = s4;
		objects[5] = s5;

		for (int i = 0; i < 100; i++) {
			String fileName = String.format("frame%03d", i);
			renderFrame(objects, rCordsTest, gCordsTest, bCordsTest);
			Frame p = new Frame(rCordsTest, gCordsTest, bCordsTest, xSize, ySize, fileName);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.zShift += 10;
		}
//		renderFrame(objects, rCordsTest, gCordsTest, bCordsTest);
//
//		Frame p = new Frame(rCordsTest, gCordsTest, bCordsTest, xSize, ySize);

		Point p1 = new Point(50, 50, 0); // test points
		Point p2 = new Point(0, -.001, 1);
		Vector v1 = new Vector(p1, p2); // test vector

		double[] intersectionTimes = hp.vectorIntersectionTime(v1);
		// printArr(intersectionTimes);

		// printArr(s.vectorIntersectionTime(v2));

	}

	static Random rand0to255 = new Random();

	public static void randFillArr(int[][] ar) { // testing
		for (int i = 0; i <= ar.length - 1; i++) {
			for (int j = 0; j <= ar[0].length - 1; j++) {
				ar[i][j] = rand0to255.nextInt(256);
			}
		}
	}

	public static void printArr(double[] ar) {
		for (double i : ar) {
			System.out.print(i + ", ");
		}
		System.out.println("");
	}

	public void HorizPlaneMap(int[][] r, int[][] g, int[][] b) {
		for (int i = 0; i <= r.length - 1; i++) {
			for (int j = 0; j <= r[0].length - 1; j++) {
				double x = j;
				double y = i;
				Point p = new Point(x - camera.x, y - camera.y, 0 - camera.z);
				Vector v = new Vector(camera, p);
				double[] intersectionTime = hp.vectorIntersectionTime(v);
				if (intersectionTime.length == 0) {
					r[i][j] = 0;
					g[i][j] = 0;
					b[i][j] = 0;
				} else {
					Point intersection = v.pointAtTime(intersectionTime[0]);
					r[i][j] = 0;
					g[i][j] = (int) (220 * intersectionTime[0]) % 256;
					b[i][j] = 0;
				}
			}
		}
	}

	public void HorizPlaneCheckered(int[][] r, int[][] g, int[][] b) {
		for (int i = 0; i <= r.length - 1; i++) {
			for (int j = 0; j <= r[0].length - 1; j++) {
				double x = j;
				double y = i;
				Point p = new Point(x - camera.x, y - camera.y, 0 - camera.z);
				Vector v = new Vector(camera, p);
				double[] intersectionTime = hp.vectorIntersectionTime(v);
				if (intersectionTime.length == 0) {
					r[i][j] = 0;
					g[i][j] = 0;
					b[i][j] = 0;
				} else {
					Point intersection = v.pointAtTime(intersectionTime[0]);
					int[] rgb = new int[3];
					hp.colorAtPoint(rgb, intersection);

					r[i][j] = rgb[0];
					g[i][j] = rgb[1];
					b[i][j] = rgb[2];
				}
			}
		}
	}

	public void renderFrame(WorldObject[] obj, int[][] r, int[][] g, int[][] b) {
		for (int i = 0; i <= r.length - 1; i++) {
			for (int j = 0; j <= r[0].length - 1; j++) {
				double x = j;
				double y = i;
				Point p = new Point(x - camera.x, y - camera.y, 0 - camera.z);
				Vector v = new Vector(camera, p);
				double[] temp;
				int smallestIndex = 0;
				double smallestSoFar = 0;

				boolean objectInPath = false;
				int numReflections = 0;

				boolean doneReflecting = false;
				// figure out which object is the closest
				while (!doneReflecting) {
					temp = rayBounce(obj, v);
					smallestIndex = (int) temp[1];
					smallestSoFar = temp[2];
					//
					if (temp[0] == 1.0) {
						objectInPath = true;
					} else {
						objectInPath = false;
					}
					if (!objectInPath) {
						doneReflecting = true;
					} else {
//						if (smallestIndex == -1) {
//							System.out.println("outofbounds -1");
//						}
						double currentRefCoef = obj[smallestIndex].refCoef;
						if (currentRefCoef == 0) {
							doneReflecting = true;
						} else { // currentRefCoef = 1
							numReflections++;
							Point intersection = v.pointAtTime(smallestSoFar);
							v.p = intersection;
							v = obj[smallestIndex].ref(v);
						}
					}
				}

				if (!objectInPath) {
					if (numReflections == 0) {
						r[i][j] = 0;
						g[i][j] = 0;
						b[i][j] = 0;
					} else {
						r[i][j] = 30;
						g[i][j] = 30;
						b[i][j] = 30;
					}
				} else {
					Point intersection = v.pointAtTime(smallestSoFar);

					int[] rgb = new int[3];
//					System.out.println("i: " + i + " j: " + j + " smallestSoFar: " + smallestSoFar + " smallestIndex: "
//							+ smallestIndex);
					obj[smallestIndex].colorAtPoint(rgb, intersection);

					r[i][j] = rgb[0];
					g[i][j] = rgb[1];
					b[i][j] = rgb[2];
				}
			}
		}
	}

	public double[] rayBounce(WorldObject[] obj, Vector v) {
		double[] out = new double[3];
		boolean objectInPath = false;
		int smallestIndex = -1;
		double smallestSoFar = 1000000000;
		for (int k = 0; k < obj.length; k++) {
			double[] tempT = obj[k].vectorIntersectionTime(v);
			if (tempT.length > 0) {
				if (tempT[0] > 0.0000001) {
					if (tempT[0] < smallestSoFar || objectInPath == false) {
						objectInPath = true;
						smallestSoFar = tempT[0];
						smallestIndex = k;
					}
				}
			}
		}
		if (objectInPath) {
			out[0] = 1.0;
		} else {
			out[0] = 0.0;
		}
		out[1] = smallestIndex;
		out[2] = smallestSoFar;
		return out;
	}
}
