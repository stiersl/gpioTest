
/**
 * This Class will test the Relay attached to GPIO PIN 21. 
 * It will turn it on and off.
 * 
 * @author Steven 
 * @version 1.0.0
 */
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
public class RelayTest {
	
    public static void main(String[] args) throws InterruptedException {
	System.out.println("Starting Main...");

        //Use the Broadcom Gpio Pin Numbering Scheme
	GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));   	
        
	// get a handle to the GPIO controller
    	final GpioController gpio = GpioFactory.getInstance();
        
        // creating the pin with parameter PinState.HIGH
        // will instantly power up the pin
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "PinLED", PinState.HIGH);
        System.out.println("Relay is: ON");
        
        // wait 2 seconds
        Thread.sleep(2000);
        
        // turn off GPIO
        pin.low();
        System.out.println("Relay is: OFF");
        // wait 1 second
        Thread.sleep(1000);
        // turn on GPIO for 1 second and then off
        System.out.println("Relay is: ON for 1 second");
        pin.pulse(1000, true);
        
        // release the GPIO controller resources
        gpio.shutdown();
    }
}
