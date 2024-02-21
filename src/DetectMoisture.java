import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.util.TimerTask;

public class DetectMoisture extends TimerTask {
    private final SSD1306 theOLedObject;
    private final Pin moistureSensor;
    private final Pin waterPump;

    public DetectMoisture(SSD1306 theOledObject, Pin moistureSensor, Pin waterPump) {
        this.theOLedObject = theOledObject;
        this.moistureSensor = moistureSensor;
        this.waterPump = waterPump;
    }

    public void run() {
        int moisture = (int) moistureSensor.getValue();
        theOLedObject.clear();
        theOLedObject.getCanvas().setTextsize(2);
        theOLedObject.getCanvas().drawString(0,0, "Value: " + moisture);
        try {
            if (moisture >= 550) {
                theOLedObject.getCanvas().drawString(0, 30, "Dry soil.");
                theOLedObject.display();
                waterPump.setValue(1);
            } else {
                theOLedObject.getCanvas().drawString(0, 30, "Wet soil.");
                theOLedObject.display();
                waterPump.setValue(0);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
