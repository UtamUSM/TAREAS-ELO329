import java.io.PrintStream;

public class Recorder extends Subscriber {
   public Recorder(String name, String topicName, PrintStream out) {
      super(name, topicName);
      this.out = out;
   }
   public void update(String message) {
      // Split the message into two numbers
      String[] numbers = message.split(" ");
      if (numbers.length == 2) {
         // Write in CSV format: name,topicName,number1,number2
         out.println(name + "," + topicName + "," + numbers[0] + "," + numbers[1]);
      }
   }
   private PrintStream out;
}
