import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.firmata.FirmataDevice;
import java.io.IOException;
import java.util.Timer;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

public class MinorProject {
    static final int D7 = 7;
    static final int A1 = 15;
    public static void main(String[] args) throws InterruptedException, IOException {
        try{
            String myUSB = "COM3";
            IODevice theArduinoObject = new FirmataDevice(myUSB);
            theArduinoObject.start();
            System.out.println("Arduino Board Started for Watering Process.");
            theArduinoObject.ensureInitializationIsDone();
            I2CDevice i2cObject = theArduinoObject.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
            SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
            theOledObject.init();
            Pin moistureSensor = theArduinoObject.getPin(A1);
            Pin waterPump = theArduinoObject.getPin(D7);
            moistureSensor.setMode(Pin.Mode.ANALOG);
            waterPump.setMode(Pin.Mode.OUTPUT);
            waterPump.setValue(0);
            Timer timer = new Timer();
            var task = new DetectMoisture(theOledObject, moistureSensor, waterPump);
            timer.schedule(task, 0, 1000);
        }
        catch (Exception e){
            System.out.println("Error connecting to board.");
        }
    }
}
