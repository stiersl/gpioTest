package com.stevenstier;

public class App 
{
  // path to where to find the one Wire device Files
  private static final String W1_DEVICE_PATH = "/sys/bus/w1/devices/";
  // device name //
  private static final String DEVICE_NAME = "28-0000081bf384";
  // Name of file to read
  private static final String FILE_NAME = "/w1_slave";
    
  public static void main(String[] args) {
    System.out.println("*****Temp Read Program Starting*****");
    OneWireTemperatureFileReader myTempFileReader = new OneWireTemperatureFileReader(W1_DEVICE_PATH + DEVICE_NAME + FILE_NAME);
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
