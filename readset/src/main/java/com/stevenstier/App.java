package com.stevenstier;
/**
 * This Class demostrates the use of PI4J to monitor a 
 * pushbutton (connected to GPIO16)
 * and then turn on an LED (connected to GPIO23).
 *  - Demonstrates use of Event Listener Logic to handle 
 * events like a input going high.
 * @author Steven Stier Stier Automation LLC
 * @version 1.0.0
 */
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class App{

	// create GPIO Contoller
	private static final GpioController gpio = GpioFactory.getInstance();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting Main...");
		System.out.println("When you press the button connected to GPIO16, LED on GPIO23 should light.");
		// **********************************************************************
		// Initialize
		//***********************************************************************
		//Use the Broadcom Gpio Pin Numbering Scheme
		GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
		
		// momentary push-button switch; input pin activates when button is pressed
		final GpioPinDigitalInput buttonPin = 
			gpio.provisionDigitalInputPin(RaspiPin.GPIO_16,PinPullResistance.PULL_DOWN);
		// led : output pin
		final GpioPinDigitalOutput ledPin = 
			gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW);
		// make sure the LED is turned off when the program shuts down
		gpio.setShutdownOptions(true, PinState.LOW, ledPin);
		//***********************************************************************
		// create the Event Listener(s)
		//***********************************************************************
		//create button event listener - uses lamba expression
		// looks for any change in the button and run this lamda
		buttonPin.addListener((GpioPinListenerDigital) (event) -> {
			if (event.getState().isLow()){
				//turn off the LED Pin
				System.out.println("turning off led");
				ledPin.setState(PinState.LOW);
			}
			else {
				//turn on the led
				System.out.println("turning on the LED");
				ledPin.setState(PinState.HIGH);
			}
		});
		// old way to do a listener event
		// buttonPin.addListener(new GpioPinListenerDigital() {
		// 	@Override
		// 	public void handleGpioPinDigitalStateChangeEvent( GpioPinDigitalStateChangeEvent event){
		// 		if (event.getState().isLow()){
		// 			//turn off the LED Pin
		// 			System.out.println("turning off led");
		// 			ledPin.setState(PinState.LOW);
		// 		}
		// 		else {
		// 			//turn on the led
		// 			System.out.println("turning on the LED");
		// 			ledPin.setState(PinState.HIGH);
		// 		}}
		// });

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

