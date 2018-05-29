package airflowww;

import java.awt.Point;

public class Helperjunk {
	public static Double getAngle(Point a, Point b) {
		return Math.atan(Math.abs(a.getX() - b.getX()) / (a.getY() - b.getY()));
	}

	/**
	 * Returns average of elements(DOUBLE)
	 */
	public static double average(int[] a) {
		Double avg = 0.0;
		for (int i = 0; i < a.length; i++) {
			avg += a[i];
		}
		return avg / a.length;
	}

}
