package com.example.deepfakeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.deepfakeapp.R;

public class IntroActivity extends AppCompatActivity {
private ConstraintLayout videoAct;
private ConstraintLayout audioAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //code for opacity
        ImageView imageView =(ImageView) findViewById(R.id.imageView);
        Drawable dPage_header= getResources().getDrawable(R.drawable.deepfakesres);
        dPage_header.setAlpha(220);
        imageView.setImageDrawable(dPage_header);
        videoAct=findViewById(R.id.video_detection);
        videoAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this,VideoActivity.class));
            }
        });
        audioAct=findViewById(R.id.audio_detection);
        audioAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this,AudioActivity.class));
            }
        });
    }
}