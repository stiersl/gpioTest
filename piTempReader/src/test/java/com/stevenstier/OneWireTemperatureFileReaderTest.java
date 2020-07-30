package com.stevenstier;

import com.stevenstier.OneWireTemperatureFileReader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class OneWireTemperatureFileReaderTest {

	private static final String DEVICENAME_GOOD =  "28-0000081bf384";
	private static final String DEVICENAME_BAD =  "28-0000081bf385";

	private OneWireTemperatureFileReader sutGood;
	private OneWireTemperatureFileReader sutBad;

	@Before
	public void setup() {
		sutGood = new OneWireTemperatureFileReader(DEVICENAME_GOOD);	
		sutBad= new OneWireTemperatureFileReader(DEVICENAME_BAD);
	}
	@Test
	public void Full_filename_is_constructed_correctly() {
		assertEquals(sutGood.getW1_DEVICE_PATH() + DEVICENAME_GOOD + sutGood.getFILE_NAME(), sutGood.getFileName());
	}
	@Test
	public void Data_is_returned_correctly_good_deviceFile() {
		sutGood.readTemp();
		assertNotNull(sutGood.getCurrentTemperature());
		assertTrue(sutGood.isFileExists());
		assertTrue(sutGood.getQuality());
	}
	
	@Test
	public void Data_is_returned_correctly_bad_deviceFile() {
		assertEquals("??",sutBad.readTemp());
		assertEquals("??", sutBad.getCurrentTemperature());
		assertFalse(sutBad.isFileExists());
		assertFalse(sutBad.getQuality());
	}

}