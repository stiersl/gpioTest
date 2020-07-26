package com.stier.app;


/**
 * Get Temperature from w1 device in the RaspberryPi.
 * 
 * @author Steven Stier
 * @version (a version number or a date)
 */
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class GetTemp

{
  static final Logger logger = LoggerFactory.getLogger(GetTemp.class);
  public static void main(String[] args) throws InterruptedException 
      {
	logger.info("Entering application.");
	String W1_DEVICES_PATH = "/sys/bus/w1/devices/";
	String W1_SLAVE = "/w1_slave";
	String device_FileName = "28-0000081bf384"; 
	boolean isNotInterrupted = true;
	String strTemp;
	double tempc;
	double tempf;
	int x = 0;
        // get java path to device file
	   Path full_path = FileSystems.getDefault().getPath(W1_DEVICES_PATH+device_FileName+W1_SLAVE);	
	do{
	    strTemp = readTempFromFile(full_path);
	    //check the value of the temperature
	   
            if (strTemp != "??") {
		tempc = Double.parseDouble(strTemp) / 1000;
		tempf = (tempc*9/5) + 32.0;   
	     	logger.info("Temperature={} Deg F",tempf);
	    } else {
		logger.info("Temperature signal bad");
	    }
		// wait 2 seconds
	    Thread.sleep(2000);
	    x++;
	  }while(x<10);
	logger.info("Exiting application.");
       }
    
    

    public static String readTempFromFile(Path pathDeviceFile) {
        int iniPos, endPos;
        String strTemp = "";
	String strTempIdentifier = "t=";
	String strCrc = "";
	String strCrcIdentifier = "crc=";
        double tvalue = 0;
        List<String> lines;
        try {
            lines = Files.readAllLines(pathDeviceFile, Charset.defaultCharset());
            for(String line : lines){
		// Get the crc Element
                if(line.contains(strCrcIdentifier)){
                    iniPos = line.indexOf(strCrcIdentifier)+4;
                    endPos = iniPos +2;
                    strCrc = line.substring(iniPos, endPos);
		   logger.debug("strCrc={}",strCrc);
                }
		// Get the Temperature from the file
                if(line.contains(strTempIdentifier)){
                    iniPos = line.indexOf(strTempIdentifier)+2;
                    endPos = line.length();
                    strTemp = line.substring(iniPos, endPos);
		    logger.debug("strTemp={}",strTemp);
                }
            }        
        } catch (IOException ex) {
            //System.out.println("Error while reading file");
            logger.error("Error while reading file " + ex.getMessage());
        }
	
	if (strCrc.equals("00")) {	
	  logger.error("Temperature signal crc is OO"); 
	  return "??";
          }
	else {
		return strTemp;
            }
    }
}