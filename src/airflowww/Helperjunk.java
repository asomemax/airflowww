package airflowww;

import java.awt.Point;

public class Helperjunk {
	/**
	 * Returns average of elements(DOUBLE)
	 */
	public static double average(double[] a) {
		double avg = 0.0;
		for (int i = 0; i < a.length; i++) {
			avg += a[i];
		}
		return avg / a.length;
	}

	public static double average(int[] a) {
		double[] r = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = a[i];
		}
		return average(r);
	}

	/**
	 * returns listOfints between start, and top - 1
	 */
	public static Integer[] intsBetween(int l, int h) {

		Integer[] ret = new Integer[Math.abs(l - h)];
		for (int i = l; i < h; i++) {
			ret[i - l] = i;
		}
		return ret;
	}

}
