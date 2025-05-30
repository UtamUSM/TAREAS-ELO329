package com.example.stage2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Scanner;

/**
 * Publicador de posiciones GPS que simula el movimiento de un móvil en el tiempo.
 * Publica una nueva posición interpolada cada segundo, basada en los datos de un archivo.
 */
public class GPSCarPublisher extends Publisher {

    /**
     * Crea un publicador de GPS que publica posiciones simuladas cada segundo.
     *
     * @param name       Nombre del publicador
     * @param broker     Broker que administra los tópicos
     * @param topicName  Nombre del tópico al cual se publican las posiciones
     * @param scanner    Scanner configurado para leer el archivo de posiciones
     */
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

    /**
     * Intenta cargar el siguiente segmento de movimiento desde el archivo.
     *
     * @return true si hay más segmentos por procesar, false si se llegó al final del archivo
     */
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

    /**
     * Publica una nueva posición interpolada en base al tiempo actual.
     * Si se alcanza el final de un segmento, carga el siguiente.
     */
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

    /**
     * Retorna la vista gráfica del publicador (HBox con etiquetas).
     *
     * @return la vista del publicador
     */
    public HBox getView(){
        return view;
    }



    private HBox view;
    private Label GPS;
    private Scanner GPSfile;
    private double xi,yi,xf,yf, time_i,time,time_f;
    Timeline timeline;
}
