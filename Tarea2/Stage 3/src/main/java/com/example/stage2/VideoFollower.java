package com.example.stage2;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
      view = new HBox();
      video = new Button("Nothing yet");

      // Al presionar el botón, se reproduce el video actual
      video.setOnAction(e -> {
         if (lastUrl != null && !lastUrl.isEmpty()) {
            playVideo(lastUrl);
         }
      });

      view.getChildren().addAll(new Label(" " + topicName + " -> " + name + ": "), video);
   }

   @Override
   public void update(String message) {
      lastUrl = message;
      video.setText(message);
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
         Slider progressSlider = new Slider();
         progressSlider.setMin(0);
         progressSlider.setMax(100);
         progressSlider.setValue(0);

         // Sincronizar barra de progreso con reproducción
         mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
               progressSlider.setValue(
                       newTime.toMillis() / media.getDuration().toMillis() * 100
               );
            }
         });

         // Permitir buscar en la reproducción
         progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
               mediaPlayer.seek(media.getDuration().multiply(progressSlider.getValue() / 100));
            }
         });

         // Control de volumen
         Slider volumeSlider = new Slider(0, 1, 0.5);
         mediaPlayer.volumeProperty().bindBidirectional(volumeSlider.valueProperty());

         // Botón de Play/Pause
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

         HBox controls = new HBox(10, new Label("Volumen:"), volumeSlider, playPause);
         VBox root = new VBox(10, mediaView, progressSlider, controls);
         Scene scene = new Scene(root, 640, 500);

         Stage videoStage = new Stage();
         videoStage.setTitle("Reproduciendo video");
         videoStage.setScene(scene);

         videoStage.setOnCloseRequest(e -> {
            mediaPlayer.stop();
         });

         videoStage.show();

         mediaPlayer.play();
      } catch (Exception ex) {
         System.err.println("Error al reproducir el video: " + ex.getMessage());
      }
   }


   private final HBox view;
   private final Button video;
   private String lastUrl = "";
}
