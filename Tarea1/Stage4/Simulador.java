import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class T1Stage3 {
   public static void main (String args[]) {
      if (args.length != 0) {
            System.out.println("Usage: java T1Stage2");
            System.exit(-1);
         }
      T1Stage3 stage = new T1Stage3();
      stage.setupSimulator();
      stage.runSimulation();
   }

   public T1Stage3(){
      streamers = new ArrayList<>();
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
                  Publisher publisher = new Publisher(name, broker, topicName);
                  streamers.add(publisher);
               } 
               else if (firstWord.equals("suscriptor")) {
                  // Create a suscriber
                  String tipo = parts[1];
                  String name = parts[2];
                  String topicName = parts[3];
                  String outputFile = parts[4];
                  Subscriber suscriptor = null;
                   switch (tipo) {
                       case "Registrador" -> {
                        suscriptor = new Recorder(name, topicName, new PrintStream(outputFile));
                       }
                       case "Seguidor" -> {
                        suscriptor = new Follower(name, topicName, new PrintStream(outputFile));
                       }
                       default -> {
                        System.out.println("Error: No se encontró "+ tipo  + " ese tipo de suscriptor en el programa");
                        System.exit(-1);
                       }
                   }
                  broker.subscribe(suscriptor);
               }
            }
         }
         
         configScanner.close();
         
         // Check if we have a publisher
         if (streamers.isEmpty()) {
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
         String line = inputEvent.nextLine();
         String[] parts = line.split(" ",2);
         String nombreStreamer = parts[0];
         String message = parts[1];
         Publisher publisher = null;
         // Check if exists a streamer with this name
         for(Publisher streamer : streamers){
            String nombre = streamer.getName();
            if(nombre.equals(nombreStreamer)){
               publisher = streamer;
            }
         }
         if(publisher == null){
            System.out.println("Unknown Publisher");
         }
         else{
         publisher.publishNewEvent(message);
         }
      }
   }
   private final ArrayList<Publisher> streamers;
}
