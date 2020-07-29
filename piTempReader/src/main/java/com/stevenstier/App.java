package com.stevenstier;

public class App 
{
  // device name //
  private static final String DEVICE_NAME = "28-0000081bf384";
  //private static final String DEVICE_NAME = "28-00000880474d";
    
  public static void main(String[] args) {
    System.out.println("*****Temp Read Program Starting*****");
    OneWireTemperatureFileReader myTempFileReader = new OneWireTemperatureFileReader(DEVICE_NAME);
    try {
      System.out.println("Press Any Key to exit reading!.");
      do {
        
        System.out.println("Temp=" + myTempFileReader.readTemp() + " DegF");
        // wait 2 seconds
        Thread.sleep(2000);
      } while (System.in.available() == 0);
    } catch (Exception e) {
      System.out.println("Exception in Main:");
    }
    System.out.println("*****Program Exiting*****");
  }


 }
