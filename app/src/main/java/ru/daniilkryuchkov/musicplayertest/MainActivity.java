package ru.daniilkryuchkov.musicplayertest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton controlButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageView albumImage;
    private TextView musicName;
    private boolean isPlaying = false;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        albumImage = findViewById(R.id.albumImg);
        albumImage.setImageResource(R.drawable.rickrollimg);

        musicName = findViewById(R.id.musicName);
        musicName.setText("Rick Astley");

        controlButton = findViewById(R.id.controlButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.rickrollmusic);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false;
            }
        });
    }

    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void playAndStop(View v){
        if (!isPlaying) {
            isPlaying = true;
            controlButton.setImageResource(R.drawable.ic_pause);
            try{
                mediaPlayer.start();
                startPlayProgressUpdater();
            }catch (IllegalStateException e) {
                mediaPlayer.pause();
            }
        }else {
            isPlaying = false;
            controlButton.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
        }
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }else{
            mediaPlayer.pause();
        }
    }


}
