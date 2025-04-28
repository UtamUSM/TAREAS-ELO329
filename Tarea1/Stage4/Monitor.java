import java.io.PrintStream;

public class Monitor extends Subscriber {

    private PrintStream out;

    public Monitor(String name, String topicName, PrintStream out) {
        super(name, topicName);
        this.out =  out;
    }

    public void update(String message) {
        String[] parts = message.trim().split("\\s+");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double distance = Math.sqrt(x * x + y * y);

        if (distance > 500) {
            out.println(name + " " + topicName + " La distancia de " + topicName + " a los 500 permitidos, distancia actual: " + String.format("%.2f", distance));
        }
    }

    
}