package airflowww;

// this class will be responsible for all the calculations
public abstract class Calculate {
	final static double ACCEL_GRAVITY = 9.80665;	// acceleration of gravity in meters per second
	
	// reference: https://en.wikipedia.org/wiki/Drag_coefficient
	// flowSpeed == power of wind
	public static double dragCoeff(double dragForce, double massDensity, double flowSpeed, double refArea) {
		return (2 * dragForce) / (massDensity * Math.pow(flowSpeed, 2) * refArea);
	}
	
	//Reynold's number
	
	// estimates the derivative of a point using Euler's method
	public static double derive(double x) {
		return 0;
	}
	
	// estimates integral using Riemann Sum
	public static double integrate(double lowBound, double highBound) {
		double sum = 0;
		/*
		for (int i = 0; i < some value; i++) {
			
		}
		*/
		return sum;
	}
	
	public static double thrustForce() {	// some changeable value 
		return 0;
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
	public static double dragForce(double massDensity, double flowSpeed, double dragCoeff, double refArea) {
		return 0.5 * massDensity * Math.pow(flowSpeed,  2) * dragCoeff * refArea;
	}
	
	public static double liftForce(double mass) {	// this one requires integrals
		return 0;
	}
	
	/** 
	 * calculating gravitational force using F = ma
	 * @param mass - in kg
	 * @return force of gravity in Newtons
	 */
	public static double gravityForce(double mass) {
		return mass * ACCEL_GRAVITY;
	}
	
	public static double netForceX() {
		return thrustForce() - dragForce();
	}
	
	public static double netForceY() {
		return liftForce() - gravityForce();
	}
	
	public static double netForce() {
		
	}
}
