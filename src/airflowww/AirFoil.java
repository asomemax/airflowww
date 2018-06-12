package airflowww;

import java.awt.Point;

public class AirFoil extends Figure {
	double dragCoeff;
	
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
	
	// airfoils use the square of the chord length as the reference area 
	// since airfoil chords are usually defined with a length of 1,
	public double findReferenceArea() {
		return Math.pow(findChordLength(), 2) ;
	}
	
	/** 
	 * calculating drag force using F = 0.5 * p * u^2 * C_d * A
	 * reference: https://en.wikipedia.org/wiki/Drag_equation
	 * @param massDensity	- mass density of fluid (air in this case), can be predefined
	 * @param flowSpeed		- velocity of air flow relative to object
	 * @param dragCoeff		- drag coefficient based on airfoil's shape, can use predefined values
	 * @param refArea		- reference area, airfoils use the square of the chord length as the reference area
	 * 						  since airfoil chords are usually defined with a length of 1,
	 * @return
	 */
	public double dragForce(double massDensity, double flowSpeed, double dragCoeff, double refArea) {
		return 0.5 * massDensity * Math.pow(flowSpeed,  2) * dragCoeff * refArea;
	}
	
	public double liftForce(double mass) {	// this one requires integrals
		return 0.0;
	}
	
	// finding the angle between the chord line and the flow direction (x-axis by default)
	public double findAngleOfAttack() {
		double dy = this.getTrailingPt().y - this.getLeadingPt().y;	// trailingPt goes first since positive numbers are down y-axis in Canvas
		double dx = this.getLeadingPt().x - this.getTrailingPt().x;
		return Math.atan2(dy, dx);
	}
}
