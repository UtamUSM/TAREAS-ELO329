import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class T1Stage1 {
   public static void main (String args[]) {
      if (args.length != 0) {
            System.out.println("Usage: java T1Stage1");
            System.exit(-1);
         }
      T1Stage1 stage = new T1Stage1();
      stage.setupSimulator();
      stage.runSimulation();
   }
   public void setupSimulator() {  // create main objects from code
      Broker broker = new Broker();
      streamer = new Publisher("Streamer", broker, "Notificaciones");
      try {
         Follower seguidor=new Follower("Seguidor", "Notificaciones", new PrintStream("seguidor.txt"));
         broker.subscribe(seguidor);
      } catch (FileNotFoundException e) {
         System.out.println("Error: No se pudo crear o acceder al archivo 'seguidor.txt'");
         System.out.println("Detalles del error: " + e.getMessage());
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