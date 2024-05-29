import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The {@code DiningPhilosophers} class sets up and starts the Dining Philosophers simulation. It
 * reads the eating time configuration from an XML file or prompts the user to input it. The class
 * initializes the forks and philosophers and starts the simulation.
 */
public class DiningPhilosophers {
  public static void main(String[] args) {
    int eatingTime = 1000; // Default value in case of failure to read from XML
    File configFile = new File("config.xml");

    try {
      if (configFile.exists()) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configFile);
        doc.getDocumentElement().normalize();

        // Read eating time from XML configuration
        Element configElement = (Element) doc.getElementsByTagName("config").item(0);
        eatingTime =
            Integer.parseInt(
                configElement.getElementsByTagName("eatingTime").item(0).getTextContent());
      } else {
        System.out.println("config.xml not found. Using default value and creating config file.");
        saveConfig(eatingTime, configFile);
      }
    } catch (Exception e) {
      System.out.println("Error reading config.xml, using default values.");
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print(
        "Enter the eating time for philosophers (in milliseconds) or press "
            + "Enter to use the config value ("
            + eatingTime
            + "): ");
    String input = scanner.nextLine();

    if (!input.trim().isEmpty()) {
      try {
        eatingTime = Integer.parseInt(input.trim());
        saveConfig(eatingTime, configFile);
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

  /**
   * Saves the eating time configuration to an XML file.
   *
   * @param eatingTime the eating time to save
   * @param configFile the XML file to save the configuration to
   */
  private static void saveConfig(int eatingTime, File configFile) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();

      // Root element
      Element rootElement = doc.createElement("config");
      doc.appendChild(rootElement);

      // Eating time element
      Element eatingTimeElement = doc.createElement("eatingTime");
      eatingTimeElement.appendChild(doc.createTextNode(Integer.toString(eatingTime)));
      rootElement.appendChild(eatingTimeElement);

      // Write the content into XML file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(configFile);
      transformer.transform(source, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
