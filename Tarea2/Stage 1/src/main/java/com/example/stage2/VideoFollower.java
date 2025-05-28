package com.example.stage2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VideoFollower extends Subscriber {
   public VideoFollower(String name, String topicName) {
      super(name, topicName);
      //??
      view =new HBox ();
      video =new Button("Nothing yet");
      view.getChildren().addAll(new Label(" "+topicName+" -> "+name+": "),video);
      //??
   }
   public void update(String message) {
      //??
      video.setText(message);
      //??
   }
   public HBox getView() {return view;}
   private HBox view;
   private Button video;
}