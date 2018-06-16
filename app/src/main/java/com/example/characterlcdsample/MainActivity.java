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

  // how many characters wide is your display?
  private static final int LCD_WIDTH = 16;

  // How many characters high is your display?
  private static final int LCD_HEIGHT = 4;

  CharacterDisplay lcd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initDisplay();
  }


  private void initDisplay() {
    // create a display builder with the LCD module width and height
    I2cLcdCharacterDisplay.Builder builder = I2cLcdCharacterDisplay.builder(LCD_WIDTH, LCD_HEIGHT);

    // set port pin to LCD pin mapping, and PCF8574(A) address
    builder.rs(0).rw(1).e(2).bl(3).data(4, 5, 6, 7).address(7)
            .withBus(busList().get(0));

    // build and use the display
    lcd = builder.build();
    lcd.connect();
    lcd.enableBackLight(true);

    // write message to the display, the first argument
    // is the LCD line (row) number
    lcd.print(1, "Android Things!");
    lcd.print(2, "You're great.");
  }

  private List<String> busList() {
    return PeripheralManager.getInstance().getI2cBusList();
  }

  @Override
  protected void onDestroy() {

    // disconnect from the display to close the i2cDevice
    if (lcd != null) {
      lcd.disconnect();
    }

    super.onDestroy();
  }
}
