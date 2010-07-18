package com.exercise.AndroidVideoView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class AndroidVideoView extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
    
      VideoView myVideoView = (VideoView)findViewById(R.id.videoview);
    
      String viewSource ="***SOURCE HERE****"; //3gp
    
      myVideoView.setVideoURI(Uri.parse(viewSource));
      myVideoView.setMediaController(new MediaController(this));
      myVideoView.requestFocus();
      myVideoView.start();

  }
}