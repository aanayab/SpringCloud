package mx.com.truper.springboot.acmehrsystem.utils;

public class HRUtils {

	private static int nextEmployeeNumber = 0;

	private static int nextFacilitiesSerialNumber = 100;
	
	private static int nextDeskNumber = 320;

	public static String nextEmployeeNumber() {

		return String.format("%05d", ++nextEmployeeNumber);
	}

	public static String nextFacilitiesSerialNumber() {

		return String.format("%05d", ++nextFacilitiesSerialNumber);
	}
	
	public static String nextDeskNumber() {

		return String.format("%05d", ++nextDeskNumber);
	}

}
