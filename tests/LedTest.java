
/**
 * Write a description of class LedControl here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import com.pi4j.io.gpio.*;
public class LedTest {
	
    public static void main(String[] args) throws InterruptedException {
    	
	GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
        // get a handle to the GPIO controller
    	final GpioController gpio = GpioFactory.getInstance();
        
        // creating the pin with parameter PinState.HIGH
        // will instantly power up the pin
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiBcmPin.GPIO_23, "PinLED", PinState.HIGH);
        System.out.println("light is: ON");
        
        // wait 2 seconds
        Thread.sleep(2000);
        
        // turn off GPIO 1
        pin.low();
        System.out.println("light is: OFF");
        // wait 1 second
        Thread.sleep(1000);
        // turn on GPIO 1 for 1 second and then off
        System.out.println("light is: ON for 1 second");
        pin.pulse(1000, true);
        
        // release the GPIO controller resources
        gpio.shutdown();
    }
}
