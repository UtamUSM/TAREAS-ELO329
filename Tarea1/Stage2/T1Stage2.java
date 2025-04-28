import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class T1Stage2 {
   public static void main (String args[]) {
      if (args.length != 0) {
            System.out.println("Usage: java T1Stage2");
            System.exit(-1);
         }
      T1Stage2 stage = new T1Stage2();
      stage.setupSimulator();
      stage.runSimulation();
   }
   public void setupSimulator() {  // create main objects from code
      Broker broker = new Broker();
      
      try {
         // Read configuration from config.txt
         File configFile = new File("config.txt");
         Scanner configScanner = new Scanner(configFile);
         
         // Process each line in the config file
         while (configScanner.hasNextLine()) {
            String line = configScanner.nextLine();
            String[] parts = line.split(" ");
            
            if (parts.length > 0) {
               String firstWord = parts[0];
               
               if (firstWord.equals("publicador")) {
                  // Create a Publisher
                  String name = parts[1];
                  String topicName = parts[2];
                  streamer = new Publisher(name, broker, topicName);
               } 
               else if (firstWord.equals("suscriptor")) {
                  // Create a Recorder
                  String name = parts[2];
                  String topicName = parts[3];
                  String outputFile = parts[4];
                  Recorder recorder = new Recorder(name, topicName, new PrintStream(outputFile));
                  broker.subscribe(recorder);
               }
            }
         }
         
         configScanner.close();
         
         // Check if we have a publisher
         if (streamer == null) {
            System.out.println("Error: No se encontró un publicador en el archivo de configuración");
            System.exit(-1);
         }
         
      } catch (FileNotFoundException e) {
         System.out.println("Error: No se pudo encontrar el archivo de configuración 'config.txt'");
         System.out.println("Detalles del error: " + e.getMessage());
         System.exit(-1);
      } catch (Exception e) {
         System.out.println("Error al procesar el archivo de configuración: " + e.getMessage());
         System.exit(-1);
      }
   }
   public void runSimulation() {
      Scanner inputEvent = new Scanner(System.in);
      while (inputEvent.hasNextLine()) {
         String message = inputEvent.nextLine();
         streamer.publishNewEvent(message);
      }
   }
   private Publisher streamer;
}
