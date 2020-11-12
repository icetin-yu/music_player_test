package com.example.music_player_test;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView songList = findViewById(R.id.myListView);

        ArrayList<String> mArrayList = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for (int i = 0 ; i < fields.length ; i++){
            mArrayList.add(fields[i].getName());
        }

        ArrayAdapter<String> xAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mArrayList);
        songList.setAdapter(xAdapter);

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mediaPlayer != null){
                    mediaPlayer.release();
                }
                int resId = getResources().getIdentifier(mArrayList.get(i),"raw",getPackageName());
                test(resId);
            }
        });


//        go = (Button) findViewById(R.id.go);
//        go.setOnClickListener(this);


    }


    public void test(int soundx) {

        Intent intent = new Intent(this,music_player.class);
        intent.putExtra("file",soundx);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

    }
}