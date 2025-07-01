package com.example.stage2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoFollower extends Subscriber {
   public VideoFollower(String name, String topicName) {
      super(name, topicName);

      videoList = FXCollections.observableArrayList();
      comboBox = new ComboBox<>(videoList);
      comboBox.setPromptText("Selecciona video");
      comboBox.setPrefWidth(400);

      comboBox.setOnAction(e -> {
         String selected = comboBox.getValue();
         if (selected != null) {
            playVideo(selected);
         }
      });

      view = new HBox();
      view.getChildren().addAll(new Label(topicName + " -> " + name + ": "), comboBox);
   }

   @Override
   public void update(String message) {
      Platform.runLater(() -> {
         // Evitar duplicados consecutivos
         videoList.remove(message);
         videoList.add(0, message); // último video al inicio

         if (videoList.size() > 3) {
            videoList.remove(3); // mantener solo los últimos 3
         }

         comboBox.setItems(FXCollections.observableArrayList(videoList));
      });
   }

   public HBox getView() {
      return view;
   }

   private void playVideo(String url) {
      try {
         Media media = new Media(url);
         MediaPlayer mediaPlayer = new MediaPlayer(media);
         MediaView mediaView = new MediaView(mediaPlayer);

         // Barra de progreso
         Slider progressSlider = new Slider(0, 100, 0);
         mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
               progressSlider.setValue(
                       newTime.toMillis() / media.getDuration().toMillis() * 100
               );
            }
         });

         progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
               mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(progressSlider.getValue() / 100));
            }
         });

         // Control de volumen
         Slider volumeSlider = new Slider(0, 1, 0.5);
         mediaPlayer.volumeProperty().bindBidirectional(volumeSlider.valueProperty());

         // Botón Play/Pause
         Label labelVolume = new Label("Volumen:");
         Button playPause = new Button("⏸");
         playPause.setOnAction(e -> {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
               mediaPlayer.pause();
               playPause.setText("▶");
            } else {
               mediaPlayer.play();
               playPause.setText("⏸");
            }
         });

         HBox controls = new HBox(10, labelVolume, volumeSlider, playPause);
         VBox root = new VBox(10, mediaView, progressSlider, controls);
         Scene scene = new Scene(root, 640, 500);

         Stage videoStage = new Stage();
         videoStage.setTitle("Reproduciendo video");
         videoStage.setScene(scene);

         videoStage.setOnCloseRequest(e -> mediaPlayer.stop());
         videoStage.show();

         mediaPlayer.play();
      } catch (Exception ex) {
         System.err.println("Error al reproducir el video: " + ex.getMessage());
      }
   }

   private final HBox view;
   private final ComboBox<String> comboBox;
   private final ObservableList<String> videoList;
}
