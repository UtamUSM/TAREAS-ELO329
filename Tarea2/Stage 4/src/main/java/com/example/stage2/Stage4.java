package com.example.stage2;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class Stage4 extends Application {
    private VBox vBoxLeft, vBoxRight;
    private Broker broker;
    public void start(Stage primaryStage) {
        broker = new Broker();
        MenuBar menuBar = new MenuBar();
        Menu menuPublisher = new Menu("Publisher");
        Menu menuSubscriber = new Menu("Subscriber");
        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);
        MenuItem menuItemVideoPub = new MenuItem("Video");
        MenuItem menuItemGPSPub = new MenuItem("Car's GPS");
        menuPublisher.getItems().addAll(menuItemVideoPub, menuItemGPSPub);
        MenuItem menuItemVideoSubs = new MenuItem("Video");
        MenuItem menuItemGPSSubs = new MenuItem("Car's GPS");
        menuSubscriber.getItems().addAll(menuItemVideoSubs, menuItemGPSSubs);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        vBoxLeft = new VBox(5);
        vBoxLeft.setAlignment(Pos.CENTER);
        borderPane.setLeft(vBoxLeft);
        vBoxRight = new VBox(10);
        vBoxRight.setAlignment(Pos.CENTER);
        borderPane.setRight(vBoxRight);
        ScrollPane scrollPane = new ScrollPane(borderPane);
        Scene scene = new Scene(scrollPane, 800, 400);
        primaryStage.setTitle("Publisher-Subscriber Simulator"); // Set the window title
        primaryStage.setScene(scene); // Place the scene in the window
        primaryStage.show(); // Display the window
        primaryStage.setResizable(true);
        Stage selectorStage = new Stage();
// Handle menu actions
        menuItemVideoPub.setOnAction(e -> addVideoPub());
        menuItemGPSPub.setOnAction(e -> {
            try {
                addGPSCarPub(selectorStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuItemVideoSubs.setOnAction(e-> addVideoSubs());
        menuItemGPSSubs.setOnAction(e-> addCarSubs());
        //??
    }
    private String getInputSting (String prompt){
        String string="default";
        TextInputDialog dialog = new TextInputDialog(string);
        dialog.setTitle(prompt);
        dialog.setHeaderText("Please enter your "+ prompt);
        dialog.setContentText(prompt+":");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent() ) return result.get();
        else return string;
    }
    private void addGPSCarPub(Stage stage) throws FileNotFoundException {
        String name = getInputSting("GPS Publisher Name");
        String topic =getInputSting("GPS Publisher Topic");
        FileChooser fileChooser = new FileChooser();
        System.out.println("ayudaaaaaa");
        fileChooser.setTitle("Abrir archivo");
        stage.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        Scanner scan = new Scanner(selectedFile);
        vBoxLeft.getChildren().add(new GPSCarPublisher(name,broker,topic,scan).getView());
    }

    private void addVideoPub() {
        String name = getInputSting("Video Publisher Name");
        String topic =getInputSting("Video Publisher Topic");
        vBoxLeft.getChildren().add(new VideoPublisher(name, broker, topic ).getView());
    }
    private void addVideoSubs() {
        String name = getInputSting("Video Subscriber Name");
        String topic =getInputSting("Video Subscriber Topic");
        VideoFollower videoFollower = new VideoFollower(name, topic);
        if (broker.subscribe(videoFollower))  // to check that topic already exists
            vBoxRight.getChildren().add(videoFollower.getView());
    }

    private void addCarSubs() {
        String name = getInputSting("GPS Subscriber Name");
        String topic =getInputSting("GPS Subscriber Topic");
        CarTracker carTracker = new CarTracker(name,topic);
        broker.subscribe(carTracker);

    }

    private void addGPSSubs() {
        String name = getInputSting("GPS Subscriber Name");
        String topic = getInputSting("GPS Topic");

        GPSCarSubscriber gpsSub = new GPSCarSubscriber(name, topic);
        if (broker.subscribe(gpsSub)) {
            // No se agrega a vBoxRight porque se abre en su propio Stage
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}