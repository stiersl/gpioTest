package com.stevenstier;

import java.io.IOException;
import java.util.Date;

public class Application 
{
	// device name //
	// this is for the while waterproof sensor
	public static String DEVICE_CODE = null;
	// this is for the black waterproof senso
	//private static final String 

	public static void main(String[] args) throws IOException {

		System.out.println("==================================================================");
		System.out.println("|                Raspi 1 wire temperature Sensing                |");
		System.out.println("==================================================================");
		// get the property values
		AppGetPropertyValues properties = new AppGetPropertyValues();
		properties.getPropValues();
		
		if (DEVICE_CODE != null) {
			OneWireTemperatureFileReader myTempFileReader = new OneWireTemperatureFileReader(DEVICE_CODE);

			try {
				System.out.println("Press Any Key to exit reading !");
				do {
				Date time = new Date(System.currentTimeMillis());
				System.out.println(time +"| " + myTempFileReader.readTemp() + " \u00B0F");
				// wait 2 seconds
				Thread.sleep(2000);
				} while (System.in.available() == 0);
			} catch (Exception e) {
				System.out.println("Exception in Main:");
			}
		} else {
			System.out.println("ERROR: devicecode is null. Program Exiting. ");
		}
		System.out.println("==================================================================");
		System.out.println("|                    Program Exiting                             |");
		System.out.println("==================================================================");

	}
 }
