package com.example.deepfakeapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.deepfakeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AudioActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 200;
    private ConstraintLayout uploadBtn;
    private ConstraintLayout detect;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String FileAddress;
    FloatingActionButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        detect=findViewById(R.id.detectnowaudio);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AudioActivity.this,DeepFakedActivity.class));
            }
        });
        homeBtn=findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AudioActivity.this,IntroActivity.class));
            }
        });
        uploadBtn=findViewById(R.id.uploadAudio);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=23)
                {
                    if(CheckPermission())
                    {
                        filepicker();
                    }
                    else
                    {
                        RequestPermission();
                    }
                }
                else
                {
                    filepicker();
                }
            }
        });
    }
    private void filepicker()
    {
        Toast.makeText(AudioActivity.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,1);
    }
    private void RequestPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(AudioActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(AudioActivity.this,"Please give permission to uplaod file", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(AudioActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    private boolean CheckPermission()
    {
        int result= ContextCompat.checkSelfPermission(AudioActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(AudioActivity.this,"Permission Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AudioActivity.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                Uri uri = data.getData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==REQUEST_GALLERY && resultCode== Activity.RESULT_OK)
//        {
//            String filePath= getRealPathFromUri(data.getData(),AudioActivity.this);
//            Log.d("File Path : "," "+filePath);
//            FileAddress=filePath;
//            Log.d("Stored Path :",FileAddress);
//        }
    }
    public String getRealPathFromUri(Uri uri, Activity activity){
        Cursor cursor=activity.getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int id= cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }
}