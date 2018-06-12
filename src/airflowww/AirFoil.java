package airflowww;

import java.awt.Point;

//encapsulates the properties of the shape that is being tested on (like airfoils)
public class AirFoil extends Figure {
	
	public AirFoil(double[] xs, double[] ys) {
		super(xs, ys);
	}
	
	public AirFoil() {
		this(new double[] { 0.0 }, new double[] { 0.0 });
	}
	
	public double findChordLength() {
		Point leadPt = getLeadingPt();
		Point trailPt = getTrailingPt();
		return leadPt.distance(trailPt);
	}
	
	// leading edge would be the point closest to the y-coordinate of flow arrow
	public Point getLeadingPt() {
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
	public Point getTrailingPt() {
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
