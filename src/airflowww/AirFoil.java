package airflowww;

import java.awt.Point;

//encapsulates the properties of the shape that is being tested on (like airfoils)
public class AirFoil extends Figure {
	double chordLength;
	
	public AirFoil() {
		super();
	}
	
	public AirFoil(double[] xs, double[] ys) {
		super(xs, ys);
	}
	
	public static double findChordLength() {
		Point leadPt = getLeadingEdge();
		Point trailPt = getTrailingEdge();
		return leadPt.distance(trailPt);
	}
	
	// leading edge would be the point closest to the y-coordinate of flow arrow
	public static Point getLeadingEdge() {
		Point leadingPt = null;
		double yArrow = Controller.flowArrow.findCenterOfMass().getY();
		for (int i = 0; i < xs.length; i++) {
			if (ys[i] == yArrow) {
				leadingPt = new Point((int) xs[i], (int) ys[i]);
			}	
		}
		return leadingPt;
	}
	
	// trailing edge would be the point farthest away from flow direction
	public static Point getTrailingEdge() {
		Point farthestPt = new Point(getDisplayXs()[0], getDisplayYs()[0]);
		for (int i = 0; i < getDisplayXs().length; i++) {
			Point nextPt = new Point(getDisplayXs()[i], getDisplayYs()[i]);
			Point centerOfMass = Controller.flowArrow.findCenterOfMass();
			if (farthestPt.distance(centerOfMass) < nextPt.distance(centerOfMass)) {
				farthestPt = nextPt; // update fartestPt
			}
		}
		return farthestPt;
	}
}
