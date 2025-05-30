package com.example.stage2;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CarTracker extends Subscriber {
    public CarTracker(String name, String topicName) {
        super(name, topicName);
        stage = new Stage();
        car = new Circle(10, Color.RED);
        Pane root = new Pane();
        telemetry = new Label("Esperando posición");

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Seguimiento del auto");
        stage.show();

        root.getChildren().addAll(car, telemetry);
        System.out.println("AYUDADADADAD");
        show();
        //??
    }

    @Override
    public void update(String message) {
        System.out.println("ayasdasdadsdasda");
        Platform.runLater(() -> {
            try {
                String[] parts = message.split(" ");
                double t = Double.parseDouble(parts[0]);
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                car.setTranslateX(x);
                car.setTranslateY(y);

                telemetry.setText("t=" + t + " x=" + x + "y=" + y);
            } catch (Exception e) {
                telemetry.setText("Mensaje inválido: " + message);
            }
        });
    }
    public void show() {
        stage.show();
    }
    private final Stage stage;
    private final Circle car;
    private final Label telemetry;
}
