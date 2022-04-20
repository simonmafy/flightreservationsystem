package sait.frs.application;

import sait.frs.gui.*;

/**
 * Flight Lookup and Reservation Program. - This program allows to book a
 * flight, search for a flight Reserve for a flight, and change flights.
 *
 * @author Simon Ma, Andrew Vergara
 * @version March 6/2020
 * 
 *          /** Application driver.
 * 
 */
public class AppDriver {

	/**
	 * Entry point to Java application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}

}
