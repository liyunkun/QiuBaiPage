package com.liyunkun.qiubaipage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        iv= (ImageView) findViewById(R.id.iv);
        Intent intent=getIntent();
        String imgPath=intent.getStringExtra("img");
        Log.d("liyunkundebugprint", "onCreate: "+imgPath);
        Picasso.with(this).load(imgPath).into(iv);
    }
}
