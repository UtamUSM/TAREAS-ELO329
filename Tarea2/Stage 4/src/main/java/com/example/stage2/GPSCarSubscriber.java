package com.example.stage2;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GPSCarSubscriber extends Subscriber {
    private final Circle circle;
    private final Label coordLabel;
    private final Pane mapPane;

    public GPSCarSubscriber(String name, String topicName) {
        super(name, topicName);

        circle = new Circle(8, Color.RED);
        mapPane = new Pane();
        mapPane.setPrefSize(400, 350);
        mapPane.getChildren().add(circle);

        coordLabel = new Label("x=0.0 y=0.0");
        HBox bottomBox = new HBox(coordLabel);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(mapPane);
        root.setBottom(bottomBox);

        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Seguimiento del auto");
            stage.setScene(new Scene(root, 400, 400));
            stage.show();
        });
    }

    @Override
    public void update(String message) {
        // Mensaje esperado: "<tiempo> <x> <y>" — pero solo usamos x e y
        String[] parts = message.trim().split("\\s+");
        if (parts.length == 3) {
            try {
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                Platform.runLater(() -> {
                    circle.setCenterX(x);
                    circle.setCenterY(y);
                    coordLabel.setText(String.format("x=%.1f y=%.1f", x, y));
                });
            } catch (Exception e) {
                System.err.println("Error al procesar mensaje GPS: " + message);
            }
        }
    }
}
