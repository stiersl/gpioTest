package com.stevenstier;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OneWireTemperatureFileReader {
  private Path myPath;
  private File myFile;
  private String fileName;
  private String currentTemperature;
  private boolean quality;
  private boolean fileExists = false;
	
  public OneWireTemperatureFileReader(String fileName) {
    this.fileName = fileName;
    myPath = FileSystems.getDefault().getPath(fileName);
    myFile = new File(fileName);
    if (myFile.exists() && myFile.isFile()) {
      this.fileExists = true;
    } else {
      System.out.println("The file:" + fileName + " Doesn't Exist!");
    } 
  }

	public String getFileName() {
	  return this.fileName;
	}

	public String getCurrentTemperature() {
	  return this.currentTemperature;
	}

	public boolean getQuality() {
	  return this.quality;
	}
	public boolean isFileExists() {
	    return this.fileExists;
	  }

	public String readTemp() {
	  String result = "??";
	  boolean quality = false;
	  int iniPos, endPos;
	  String strTemp = "";
	  String strTempIdentifier = "t=";
	  String strCrc = "";
	  String strCrcIdentifier = "crc=";
	  List<String> lines; // used to store each line of in the file

	  if (fileExists) {
	    try {
	      // move all the file over to the list array.
    		lines = Files.readAllLines(myPath, Charset.defaultCharset());
    		// Scroll through each line looking for the identifiers
    		for (final String line : lines) {
    		// Get the crc Element
    		if (line.contains(strCrcIdentifier)) {
    		  iniPos = line.indexOf(strCrcIdentifier) + 4;
    		  endPos = iniPos + 2;
    		  strCrc = line.substring(iniPos, endPos);
    		  }
    		// Get the Temperature from the file
    		if (line.contains(strTempIdentifier)) {
    			iniPos = line.indexOf(strTempIdentifier) + 2;
    			endPos = line.length();
    			strTemp = line.substring(iniPos, endPos);
    		  }
    		}
          if (strCrc.equals("00")) {
            System.out.println("Temperature signal crc is OO");
    			}
    			else {
    					try{ 
    						result = String.format("%.1f",ConvertToDegF(Double.parseDouble(strTemp)/1000));
    						quality = true;
    					}
    					catch (Exception e) {
    						System.out.println("Error converting to Decimal:");
    						quality = false;
    					}	
    			 	}
    			 } catch (final IOException ex) {
    			System.out.println("Error while reading file:" + ex.getMessage());
    			quality = false;
    		}
	    }
	  this.quality = quality;
	  if (quality == false) {
	    result = "??";
	  }
	  this.currentTemperature = result;
	  return result;
	    
	}

	public static double ConvertToDegF(double tempDegC) {
		double result = 0.0d;
		result = (tempDegC * (double) 9 / 5) + 32.0; // Concert to deg f
		result = result * 10.0; // these next three line should round the temp to 1 decimal place
		result = Math.round(result);
		result = result / 10.0;
		return result;
	}
}