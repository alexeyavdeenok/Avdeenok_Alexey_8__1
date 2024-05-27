import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DiningPhilosophers {
  public static void main(String[] args) {
    int eatingTime = 1000; // Default value in case of failure to read from XML

    try {
      File configFile = new File("src/config.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(configFile);
      doc.getDocumentElement().normalize();

      // Read eating time from XML configuration
      Element configElement = (Element) doc.getElementsByTagName("config").item(0);
      eatingTime =
          Integer.parseInt(
              configElement.getElementsByTagName("eatingTime").item(0).getTextContent());

    } catch (Exception e) {
      System.out.println("Error reading config.xml, using default values.");
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print(
        "Enter the eating time for philosophers (in milliseconds) or press Enter to use the config value ("
            + eatingTime
            + "): ");
    String input = scanner.nextLine();

    if (!input.trim().isEmpty()) {
      try {
        eatingTime = Integer.parseInt(input.trim());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Using default config value: " + eatingTime);
      }
    }

    // Creating the Forks
    Fork[] forks = new Fork[5];
    for (int i = 0; i < forks.length; i++) {
      forks[i] = new Fork(i);
    }

    // Creating the Philosophers
    Philosopher[] philosophers = new Philosopher[5];
    for (int i = 0; i < philosophers.length; i++) {
      Fork leftFork = forks[i];
      Fork rightFork = forks[(i + 1) % forks.length];
      philosophers[i] = new Philosopher(i, leftFork, rightFork, eatingTime);
      philosophers[i].start();
    }
  }
}
