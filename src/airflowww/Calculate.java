package airflowww;

// this class will be responsible for all the calculations
public class Calculate {
	
	// reference: https://en.wikipedia.org/wiki/Drag_coefficient
	// flowSpeed == power of wind
	public static double dragCoeff(double dragForce, double massDensity, double flowSpeed, double refArea) {
		return (2 * dragForce) / (massDensity * Math.pow(flowSpeed, 2) * refArea);
	}
	
	// reference: https://en.wikipedia.org/wiki/Drag_equation
	public static double drag(double massDensity, double flowSpeed, double dragCoeff, double refArea) {
		return 0.5 * massDensity * Math.pow(flowSpeed,  2) * dragCoeff * refArea;
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
}
