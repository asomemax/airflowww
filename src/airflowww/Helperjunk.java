package airflowww;

import java.awt.Point;

public class Helperjunk {
	/**
	 * Returns average of elements(DOUBLE)
	 */
	public static double average(int[] a) {
		double avg = 0.0;
		for (int i = 0; i < a.length; i++) {
			avg += a[i];
		}
		return avg / a.length;
	}

	/**
	 * returns listOfints between start, and top - 1
	 */
	public static int[] intsBetween(int l, int h) {

		int[] ret = new int[Math.abs(l - h)];
		for (int i = l; i < h; i++) {
			ret[i - l] = i;
		}
		return ret;
	}

}
