/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Stier Automation LLC
 * PROJECT       :  Pi4J :: Java Examples
 * FILENAME      :  PBtest.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2019 Stier Automation
 * %%
 * This program will
 * #L%
 */
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import com.pi4j.util.ConsoleColor;

/**
 * This example code demonstrates how to setup a listener
 * for GPIO pin state changes on the RaspberryPi.
 *
 * @author Robert Savage
 */
public class PBTest {

    /**
     * [ARGUMENT/OPTION "--pin (#)" | "-p (#)" ]
     * This example program accepts an optional argument for specifying the GPIO pin (by number)
     * to use with this GPIO listener example. If no argument is provided, then GPIO #1 will be used.
     * -- EXAMPLE: "--pin 4" or "-p 0".
     *
     * [ARGUMENT/OPTION "--pull (up|down|off)" | "-l (up|down|off)" | "--up" | "--down" ]
     * This example program accepts an optional argument for specifying pin pull resistance.
     * Supported values: "up|down" (or simply "1|0").   If no value is specified in the command
     * argument, then the pin pull resistance will be set to PULL_UP by default.
     * -- EXAMPLES: "--pull up", "-pull down", "--pull off", "--up", "--down", "-pull 0", "--pull 1", "-l up", "-l down".
     *
     * @param args
     * @throws InterruptedException
     * @throws PlatformAlreadyAssignedException
     */
    public static void main(String args[]) throws InterruptedException, PlatformAlreadyAssignedException {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "GPIO Listen Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        //Use the Broadcom Gpio Pin Numbering Scheme
	GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));


        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // by default we will use gpio pin #01; however, if an argument
        // has been provided, then lookup the pin by address
        Pin pin = CommandArgumentParser.getPin(
                RaspiBcmPin.class,    // pin provider class to obtain pin instance from
                RaspiBcmPin.GPIO_16,  // default pin if no pin argument found
                args);             // argument array to search in

        // by default we will use gpio pin PULL-UP; however, if an argument
        // has been provided, then use the specified pull resistance
        PinPullResistance pull = CommandArgumentParser.getPinPullResistance(
                PinPullResistance.PULL_DOWN,  // default pin pull resistance if no pull argument found
                args);                      // argument array to search in

        // provision gpio pin #02 as an input pin with its internal pull resistor set to UP or DOWN
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(pin, pull);

        // unexport the GPIO pin when program exits
        myButton.setShutdownOptions(true);

        // prompt user that we are ready
        console.println(" ... Successfully provisioned [" + pin + "] with PULL resistance = [" + pull + "]");
        console.emptyLine();
        console.box("Please complete the [" + pin + "] circuit and see",
                "the listener feedback here in the console.");
        console.emptyLine();

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                console.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " +
                        ConsoleColor.conditional(
                                event.getState().isHigh(), // conditional expression
                                ConsoleColor.GREEN,        // positive conditional color
                                ConsoleColor.RED,          // negative conditional color
                                event.getState()));        // text to display
            }

        });

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // forcefully shutdown all GPIO monitoring threads and scheduled tasks
        gpio.shutdown();
    }
}
