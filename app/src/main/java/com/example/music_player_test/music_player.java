package com.example.music_player_test;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class music_player extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private SeekBar timeBar;
    int totalTime;
    TextView currentTimeLabel, remainTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.player);

        ImageButton playButton = (ImageButton) findViewById(R.id.startBtn);
        ImageButton stopButton = (ImageButton) findViewById(R.id.stopBtn);


        mediaPlayer = MediaPlayer.create(this, getIntent().getIntExtra("file",0));

        totalTime = mediaPlayer.getDuration();
//        mediaPlayer = MediaPlayer.create(this,R.raw.sound);





        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {

                    mediaPlayer.start();

                    playButton.setBackgroundResource(R.drawable.pause_icon);

                    playButton.setContentDescription(Float.toString(R.string.stop));

                } else {

                    mediaPlayer.pause();

                    playButton.setBackgroundResource(R.drawable.play_icon);
                    playButton.setContentDescription(Float.toString(R.string.start));
                }

            }
        });

        timeBar = findViewById(R.id.timeBar);

        timeBar.setMax(totalTime);
        timeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(progress);
                            timeBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );







        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
                playButton.setBackgroundResource(R.drawable.play_icon);

            }
        });

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                int currentPosition = msg.what;



                timeBar.setProgress(currentPosition);


//                String currentTime = createTimeLabel(currentPosition);
//                currentTimeLabel.setText(currentTime);
//
//                String remainingTime = "- " + createTimeLabel(totalTime - currentPosition);
//
//                remainTime.setText(remainingTime);

                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();




}

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        finish();
    }

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

}

