package com.avinfo.smokename.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.avinfo.smokename.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    CropImageView crop;
    ImageView done,back2;
    private TextView a1_1,a3_4,original,a3_2,a16_9;
    public static Bitmap bitmap1;
    public static int aa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        back2 = (ImageView)findViewById(R.id.back2);
        done = (ImageView)findViewById(R.id.done_crop);
        crop = (CropImageView) findViewById(R.id.crop);
//        a1_1 = (TextView)findViewById(R.id.a1_1);
//        a3_2 = (TextView)findViewById(R.id.a3_2);
//        original = (TextView)findViewById(R.id.original);
//        a3_4 = (TextView)findViewById(R.id.a3_4);
//        a16_9 = (TextView)findViewById(R.id.a16_9);

            crop.setImageBitmap(EditingActivity.finalEditedImage);

//        a1_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             crop.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.RATIO_3_4);
//            }
//        });
//
//        a3_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                crop.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.RATIO_4_3);
//            }
//        });
//
//        original.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                crop.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.CUSTOM);
//            }
//        });
//
//        a3_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                crop.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.RATIO_9_16);
//            }
//        });
//
//        a16_9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                crop.setCropMode(com.isseiaoki.simplecropview.CropImageView.CropMode.RATIO_16_9);
//            }
//        });
//


        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa = 1;
                bitmap1 = crop.getCroppedImage();
//                Intent intent=new Intent(CropActivity.this,EditingActivity.class);
//                startActivity(intent);
                finish();
            }
        });


    }
}
