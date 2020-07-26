package com.stevenstier;
import com.stevenstier.OneWireTemperatureFileReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OneWireTemperatureFileReaderTest {

@Test
public void TempFileReader_accepts_a_filename() {
    OneWireTemperatureFileReader sut = new OneWireTemperatureFileReader("Test");
    assertEquals("Test", sut.getFileName());
}

@Test
public void TempFileReader_accepts_testfile() {
      String testfilename = getClass().getClassLoader().getResource("w1_slave").getFile();
      
      OneWireTemperatureFileReader sut = new OneWireTemperatureFileReader(testfilename);
      assertEquals("72.1", sut.readTemp());
      assertEquals("72.1", sut.getCurrentTemperature());
      assertTrue(sut.isFileExists());
      assertTrue(sut.getQuality());
    }
   

@Test
public void TempFileReader_accepts_CRC_INtestfile() {
      String testfilename = getClass().getClassLoader().getResource("w1_fail").getFile();
      OneWireTemperatureFileReader sut = new OneWireTemperatureFileReader(testfilename);
      assertEquals("??", sut.readTemp());
      assertTrue(sut.isFileExists());
      assertFalse(sut.getQuality());
      
    }
@Test
public void TempFileReader_accepts_emptytestfile() {
      String testfilename = getClass().getClassLoader().getResource("w1_empty").getFile();
      OneWireTemperatureFileReader sut = new OneWireTemperatureFileReader(testfilename);
      assertEquals("??", sut.readTemp());
      assertTrue(sut.isFileExists());
      assertFalse(sut.getQuality());
      
    } 

@Test
public void TempFileReader_accepts_bad_filePassed() {
  OneWireTemperatureFileReader sut = new OneWireTemperatureFileReader("xx");
      assertEquals("??", sut.readTemp());
      assertFalse(sut.isFileExists());
      assertFalse(sut.getQuality());
      
    }

}