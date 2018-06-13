package airflowww;

import java.awt.Point;
import java.awt.geom.Point2D;

public class AirFoil extends Figure {
	//private String typeOfAirFoil;
	private double dragCoeff;
	private double liftCoeff;
	private double chordLength;
	private Point leadingPt;	// leading edge
	private Point trailingPt;	// trailing edge
	
	// type of airfoils: "symmetrical", "high camber", "flat"
	public AirFoil(double[] xs, double[] ys) {
		super(xs, ys);
		leadingPt = this.findLeadingPt();
		trailingPt = this.findTrailingPt();
		chordLength = this.findChordLength();
		dragCoeff = this.findDragCoeff();
	}
	
	public AirFoil() {
		this(new double[] { 0.0 }, new double[] { 0.0 });
	}
	
	// getter methods
	public double getDragCoeff() {
		return this.dragCoeff;
	}
	
	public double getLiftCoeff() {
		return this.liftCoeff;
	}
	
	public double getChordLength() {
		return this.chordLength;
	}
	

	public Point getLeadingEdge() {
		return this.leadingPt;
	}
	
	public Point getTrailingEdge() {
		return this.trailingPt;
	}
	
	// leading edge would be the point in the front of airfoil with the maximum curvature (smallest radius) 
	private Point findLeadingPt() {
		Point2D.Double pt1 = new Point2D.Double(xs[0], ys[0]);
		Point2D.Double pt2 = new Point2D.Double(xs[1], ys[1]);
		Point2D.Double pt3 = new Point2D.Double(xs[2], ys[2]);
		
		Point leadingPt = new Point((int) pt2.getX(), (int) pt2.getY());
		double smallestRadius = this.findRadiusFromChordAndPt(pt1, pt3, pt2);
		
		for (int i = 2; i < xs.length - 1; i++) {
			Point2D.Double ptA = new Point2D.Double(xs[i - 1], ys[i - 1]);
			Point2D.Double ptC = new Point2D.Double(xs[i], ys[i]);
			Point2D.Double ptB = new Point2D.Double(xs[i + 1], ys[i + 1]);
			if (smallestRadius > this.findRadiusFromChordAndPt(ptA, ptB, ptC)) {
				smallestRadius = this.findRadiusFromChordAndPt(ptA, ptB, ptC);
				leadingPt = new Point((int) ptC.getX(), (int) ptC.getY());
			}
		}
		/*
		double yArrow = Controller.flowArrow.findCenterOfMass().getY();
		for (int i = 0; i < xs.length; i++) {
			if (ys[i] == yArrow) {
				leadingPt = new Point((int) xs[i], (int) ys[i]);
			}	
		}
		*/
		return leadingPt;
	}
	
	// chord will be connected from ptA to ptB, ptC will be the point that forms a right angle with midpoint and either ptA or ptB
	private double findRadiusFromChordAndPt(Point2D.Double ptA, Point2D.Double ptB, Point2D.Double ptC) {
		double chordLength = ptA.distance(ptB);
		double avgX = (ptB.getX() - ptA.getX()) / 2;
		double avgY = (ptB.getY() - ptA.getY()) / 2;
		Point2D.Double midPt = new Point2D.Double(avgX, avgY);
		
		// finding radius using a manipulated form of pythagorean theorem 
		// r^2 = base^2 + (r - distance between ptC and midPt)^2 ==> r = (base^2 + (distance between ptC and midPt)^2) / 2(distance between ptC and midPt)
		double base = chordLength / 2;			// base of triangle with vertices: ptA, midPt, center of circle
		double height = ptC.distance(midPt);	// height of triangle with vertices: ptA, ptC, midPt
		double radius = (Math.pow(base, 2) + Math.pow(height, 2)) / (2 * height);
		return radius;
	}
	
	// trailing edge would be the point in the front of airfoil with the maximum curvature (smallest radius) 
	// trailing edge would be the point farthest away from flow direction
	private Point findTrailingPt() {
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
	
	private double findChordLength() {
		return leadingPt.distance(trailingPt);
	}
	
	// airfoils use the square of the chord length as the reference area 
	// since airfoil chords are usually defined with a length of 1,
	public double findReferenceArea() {
		return Math.pow(findChordLength(), 2) ;
	}
	
	/** 
	 * calculating drag force using F = 0.5 * C_d * p * V^2 * A
	 * reference: https://en.wikipedia.org/wiki/Drag_equation
	 * @param massDensity	- mass density of fluid (air in this case), can be predefined
	 * @param flowSpeed		- velocity of air flow relative to object
	 * @param dragCoeff		- drag coefficient based on airfoil's shape, can use predefined values
	 * @param refArea		- reference area, airfoils use the square of the chord length as the reference area
	 * 						  since airfoil chords are usually defined with a length of 1,
	 * @return
	 */
	public double dragForce(double dragCoeff, double fluidDensity, double flowSpeed, double refArea) {
		return 0.5 * dragCoeff * fluidDensity * Math.pow(flowSpeed,  2)  * refArea;
	}
	
	/**
	 * calculating lift force using F = 0.5 * C_l * p * V^2 * A
	 * reference: https://www.grc.nasa.gov/WWW/K-12/airplane/lifteq.html
	 * @param liftCoeff
	 * @param fluidDensity
	 * @param flowSpeed
	 * @param refArea
	 * @return
	 */
	public double liftForce(double liftCoeff, double fluidDensity, double flowSpeed, double refArea) {	
		return 0.5 * liftCoeff * fluidDensity * Math.pow(flowSpeed, 2) * refArea;
	}
	
	public double liftForce() { // this one requires integrals with dynamic pressure
		return 0.0;
	}
	
	// finding the angle between the chord line and the flight path (x-axis by default)
	public double findAngleOfAttack() {
		double dy = this.trailingPt.y - this.leadingPt.y;	// trailingPt goes first since positive numbers are down y-axis in Canvas
		double dx = this.leadingPt.x - this.trailingPt.x;
		return Math.atan2(dy, dx);
	}
	
	private double findDragCoeff() {
		if (this.typeOfAirFoil.equals("symmetrical")) {
			return 0.0;
		} else if (this.typeOfAirFoil.equals("high camber")) {
			return 0.0;
		} else if (this.typeOfAirFoil.equals("flat")) {
			return 0.0;
		} else {
			return 0.0;
		}
	}

}
