package com.avinfo.smokename.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.avinfo.smokename.R;

public class SplashActivity extends AppCompatActivity {

    TextView smoke,effect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        smoke = findViewById(R.id.smoke);
        effect = findViewById(R.id.effect);

        Animation left = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.move_left);
        Animation right = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.move_right);

        effect.startAnimation(left);
        smoke.startAnimation(right);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },3500);

    }
}
