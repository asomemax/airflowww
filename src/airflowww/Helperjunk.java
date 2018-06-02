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

}
