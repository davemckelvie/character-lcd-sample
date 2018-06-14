package com.example.characterlcdsample;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.PeripheralManager;

import java.util.List;

import nz.geek.android.things.driver.display.CharacterDisplay;
import nz.geek.android.things.driver.display.I2cLcdCharacterDisplay;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

  CharacterDisplay lcd;
  CharacterDisplay lcd2;
  CharacterDisplay lcd3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initDisplay1();
    initDisplay2();
    initDisplay3();

  }

  private void initDisplay1() {
    // create a display builder with the LCD module width and height
    I2cLcdCharacterDisplay.Builder builder = I2cLcdCharacterDisplay.builder(40, 4);

    // set port pin to LCD pin mapping, and PCF8574(A) address
    builder.rs(0).rw(1).e(2).e2(3).data(4, 5, 6, 7).address(5)
            .withBus(busList().get(0));

    // build and use the display
    lcd = builder.build();
    lcd.connect();

    // write message to the display, the first argument
    // is the LCD line (row) number
    lcd.print(1, "Hello Android Things! It actually works!");
    lcd.print(2, "I don't believe it. It must be true.");
    lcd.print(3, "OMG This is such a big display,");
    lcd.print(4, "I could write a novel, a very short one.");
  }

  private void initDisplay2() {
    // create a display builder with the LCD module width and height
    I2cLcdCharacterDisplay.Builder builder = I2cLcdCharacterDisplay.builder(16, 4);

    // set port pin to LCD pin mapping, and PCF8574(A) address
    builder.rs(0).rw(1).e(2).bl(3).data(4, 5, 6, 7).address(7)
            .withBus(busList().get(0));

    // build and use the display
    lcd2 = builder.build();
    lcd2.connect();
    lcd2.enableBackLight(true);

    // write message to the display, the first argument
    // is the LCD line (row) number
    lcd2.print(1, "Three displays?");
    lcd2.print(2, "Now you're just");
    lcd2.print(3, "showing off");
    lcd2.print(4, "I mean, really.");
  }

  private void initDisplay3() {
    // create a display builder with the LCD module width and height
    I2cLcdCharacterDisplay.Builder builder = I2cLcdCharacterDisplay.builder(40, 2);

    // set port pin to LCD pin mapping, and PCF8574(A) address
    builder.rs(0).rw(1).e(2).bl(3).data(4, 5, 6, 7).address(6)
            .withBus(busList().get(0));

    // build and use the display
    lcd3 = builder.build();
    lcd3.connect();
    lcd3.enableBackLight(true);

    // write message to the display, the first argument
    // is the LCD line (row) number
    lcd3.print(1, "Wait a minute, two displays, what gives?");
    lcd3.print(2, "That's just CRRAAZZZYYY!?!?");
  }


  private List<String> busList() {
    return PeripheralManager.getInstance().getI2cBusList();
  }

  @Override
  protected void onDestroy() {
    if (lcd != null) {
      lcd.disconnect();
    }

    if (lcd2 != null) {
      lcd2.disconnect();
    }

    if (lcd3 != null) {
      lcd3.disconnect();
    }
    super.onDestroy();
  }
}
