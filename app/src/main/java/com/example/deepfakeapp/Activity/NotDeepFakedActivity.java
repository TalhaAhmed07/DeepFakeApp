package com.example.deepfakeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.deepfakeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotDeepFakedActivity extends AppCompatActivity {
FloatingActionButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_deep_faked);
        homeBtn=findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotDeepFakedActivity.this,IntroActivity.class));
            }
        });
    }
}