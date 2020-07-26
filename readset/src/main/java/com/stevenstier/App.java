package com.stevenstier;
/**
 * This Class demostrates the use of PI4J to monitor a 
 * pushbutton (connected to GPIO16)
 * and then turn on an LED (connected to GPIO23).
 *  - Demonstrates use of Event Listener Logic to handle 
 * events like a input going high.
 * @author Steven Stier Stier AUtomation LLC
 * @version 1.0.0
 */
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class App{
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting Main...");
		System.out.println("When you press the button connected to GPIO16, LED on GPIO23 should light.");
		//Use the Broadcom Gpio Pin Numbering Scheme
		GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
		// create a GPIO Contoller
		final GpioController gpio = GpioFactory.getInstance();
		// momentary push-button switch; input pin
		final GpioPinDigitalInput buttonPin = 
			gpio.provisionDigitalInputPin(RaspiPin.GPIO_16,PinPullResistance.PULL_DOWN);
		// led : output pin
		final GpioPinDigitalOutput ledPin = 
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW);
		//create event listener for button input pin
		buttonPin.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent( GpioPinDigitalStateChangeEvent event){
				if (event.getState().isLow()){
					//turn off the LED Pin
					System.out.println("turning off led");
					ledPin.setState(PinState.LOW);
				}
				else {
					//turn on the led
					System.out.println("turning on the LED");
					ledPin.setState(PinState.HIGH);
				}}
		});

		System.out.println("Press Any Key to exit Reading!.");
		try{
			do{
			} while (System.in.available() == 0);
		}catch (Exception e){ 
		System.out.println("Other exception in Run");
	  }

		// release the GPIO controller resources
		gpio.shutdown();
		System.out.println("Ending program...");
	}
	
}

