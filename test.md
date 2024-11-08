```java
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyRobot {

    private Robot robot;
    private Random random;

    public MyRobot() throws Exception {
        // Initialize the Robot instance and Random instance
        this.robot = new Robot();
        this.random = new Random();
    }

    // Method to press Alt+Tab
    public void pressAltTab() {
        // Press Alt key
        robot.keyPress(KeyEvent.VK_ALT);
        // Press Tab key
        robot.keyPress(KeyEvent.VK_TAB);
        // Release Tab key
        robot.keyRelease(KeyEvent.VK_TAB);
        // Release Alt key
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    // Method to start Alt+Tab at random intervals
    public void startSwitching() {
        while (true) {
            try {
                // Call the method to press Alt+Tab
                pressAltTab();

                // Wait for a random interval between 10 to 30 seconds
                int interval = 10 + random.nextInt(21); // Random interval between 10 and 30
                System.out.println("Switching in " + interval + " seconds.");

                // Sleep for the random interval
                TimeUnit.SECONDS.sleep(interval);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted. Stopping Alt+Tab switching.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Create an instance of MyRobot and start switching
            MyRobot myRobot = new MyRobot();
            myRobot.startSwitching();
        } catch (Exception e) {
            System.err.println("Failed to initialize MyRobot: " + e.getMessage());
        }
    }
}

```
