package com.example.stage2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Scanner;

public class GPSCarPublisher extends Publisher {

    public GPSCarPublisher(String name, Broker broker, String topicName, Scanner scanner) {
        super(name, broker, topicName);
        view = new HBox();
        GPS = new Label();
        view.getChildren().addAll(new Label(name + "->" + topicName+":"), GPS);
        GPSfile = scanner;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> reportPosition()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private boolean loadNextSegment() {
        if (GPSfile.hasNextLine()) {
            String line = GPSfile.nextLine();
            String[] partes = line.split(" ");
            time_i =  Double.parseDouble(partes[0]);
            xi = Double.parseDouble(partes[1]);
            yi = Double.parseDouble(partes[2]);

            if (GPSfile.hasNextLine()) {
                String linea2 = GPSfile.nextLine();
                String[] partes2 = linea2.split(" ");
                time_f = Double.parseDouble(partes2[0]);
                xf = Double.parseDouble(partes2[1]);
                yf = Double.parseDouble(partes2[2]);
                return true;
            }
        }
        return false;
    }

    private void reportPosition() {
        // Si ya pasamos el intervalo, cargamos el siguiente segmento
        if (time >= time_f) {
            if (!loadNextSegment()) {
                timeline.stop(); // No hay más datos
                return;
            }
            time = time_i;
        }
        // Interpolación lineal
        double fraction = (time - time_i) / (time_f - time_i);
        double x = xi + (xf - xi) * fraction;
        double y = yi + (yf - yi) * fraction;

        String espacioTiempo = time + " " + x + " " + y;
        GPS.setText(espacioTiempo);
        publishNewEvent(espacioTiempo);

        time += 1.0; // Avanza 1 segundo virtual
    }


    public HBox getView(){return view;}
    private HBox view;
    private Label GPS;
    private Scanner GPSfile;
    private double xi,yi,xf,yf, time_i,time,time_f;
    Timeline timeline;
}
