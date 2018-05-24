package airflowww;

import java.awt.Point;

public class Helperjunk {
	public static Double getAngle(Point a, Point b) {
		return Math.atan(Math.abs(a.getX() - b.getX()) / (a.getY() - b.getY()));
	}
}
