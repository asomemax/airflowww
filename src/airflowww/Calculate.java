package airflowww;

// this class will be responsible for all the calculations
public class Calculate {
	
	// reference: https://en.wikipedia.org/wiki/Drag_coefficient
	// flowSpeed == power of wind?
	public static double dragCoeff(double dragForce, double massDensity, double flowSpeed, double area) {	// area is reference area
		return (2 * dragForce) / (massDensity * Math.pow(flowSpeed, 2) * area);
	}
	
	// reference: https://en.wikipedia.org/wiki/Drag_equation
	public static double drag(double massDensity, double flowSpeed, double dragCoeff, double area) {
		return 0.5 * massDensity * Math.pow(flowSpeed,  2) * dragCoeff * area;
	}
	
	//Reynold's number
}
