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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deepfakeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VideoActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 200;
    private ConstraintLayout uploadBtn;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String FileAddress;
    FloatingActionButton homeBtn;
    private ConstraintLayout detect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        detect =findViewById(R.id.detectnowvideo);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoActivity.this,NotDeepFakedActivity.class));
            }
        });
        homeBtn=findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoActivity.this,IntroActivity.class));
            }
        });
        uploadBtn=findViewById(R.id.uploadvideo);
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
        Toast.makeText(VideoActivity.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent openGallery = new Intent(Intent.ACTION_PICK);
        openGallery.setType("image/*");
        startActivityForResult(openGallery,REQUEST_GALLERY);
    }
    private void RequestPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(VideoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(VideoActivity.this,"Please give permission to uplaod file", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(VideoActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    private boolean CheckPermission()
    {
        int result= ContextCompat.checkSelfPermission(VideoActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
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
                    Toast.makeText(VideoActivity.this,"Permission Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(VideoActivity.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GALLERY && resultCode== Activity.RESULT_OK)
        {
            //Uri uri =data.getData();
            String filePath= getRealPathFromUri(data.getData(),VideoActivity.this);
            Log.d("File Path : "," "+filePath);
            FileAddress=filePath;
            Log.d("Stored Path :",FileAddress);
        }
    }
    public String getRealPathFromUri(Uri uri,Activity activity){
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