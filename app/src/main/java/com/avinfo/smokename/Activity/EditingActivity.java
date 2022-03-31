package com.avinfo.smokename.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avinfo.smokename.Adapters.CBackAdapter;
import com.avinfo.smokename.Adapters.CForeAdapter;
import com.avinfo.smokename.Adapters.MainAdapter;
import com.avinfo.smokename.Adapters.Smoke12Adapter;
import com.avinfo.smokename.Adapters.TextstickerAdapter;
import com.avinfo.smokename.Adapters.WBackAdapter;
import com.avinfo.smokename.Adapters.WForeAdapter;
import com.avinfo.smokename.R;
import com.avinfo.smokename.Utils.UtilsData;
import com.bumptech.glide.Glide;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAd;

import java.util.EnumSet;

import static com.avinfo.smokename.Adapters.MainAdapter.mCurrentView;

public class EditingActivity extends AppCompatActivity {

    private RecyclerView mainitem;
    public static RecyclerView smoke12,color_back,color_fore,white_back,white_fore,textsticker;
    public static Uri imageurl;
    private LinearLayout remove;

    private InterstitialAd interstitialAd;

    public int[] d_smoke1 = new int[] {R.drawable.background_00001,R.drawable.background_00002,R.drawable.background_00003,R.drawable.background_00004,R.drawable.background_00005,R.drawable.background_00006,R.drawable.background_00007,R.drawable.background_00008,R.drawable.background_00009,R.drawable.background_00010
            ,R.drawable.background_00011,R.drawable.background_00012,R.drawable.background_00013,R.drawable.background_00014,R.drawable.background_00015,R.drawable.background_00016,R.drawable.background_00017,R.drawable.background_00018,R.drawable.background_00019,R.drawable.background_00020
            ,R.drawable.background_00021,R.drawable.background_00022,R.drawable.background_00023,R.drawable.background_00024,R.drawable.background_00025,R.drawable.background_00026,R.drawable.background_00027,R.drawable.background_00028,R.drawable.background_00029,R.drawable.background_00030
            ,R.drawable.background_00031,R.drawable.background_00032,R.drawable.background_00033,R.drawable.background_00034,R.drawable.background_00035,R.drawable.background_00036,R.drawable.background_00037,R.drawable.background_00038,R.drawable.background_00039};

    public int[] d_smoke2 = new int[] {R.drawable.over_background_00001,R.drawable.over_background_00002,R.drawable.over_background_00003,R.drawable.over_background_00004,R.drawable.over_background_00005,R.drawable.over_background_00006,R.drawable.over_background_00007,R.drawable.over_background_00008,R.drawable.over_background_00009,R.drawable.over_background_00010
            ,R.drawable.over_background_00011,R.drawable.over_background_00012,R.drawable.over_background_00013,R.drawable.over_background_00014,R.drawable.over_background_00015,R.drawable.over_background_00016,R.drawable.over_background_00017,R.drawable.over_background_00018,R.drawable.over_background_00019,R.drawable.over_background_00020
            ,R.drawable.over_background_00021,R.drawable.over_background_00022,R.drawable.over_background_00023,R.drawable.over_background_00024,R.drawable.over_background_00025,R.drawable.over_background_00026,R.drawable.over_background_00027,R.drawable.over_background_00028,R.drawable.over_background_00029,R.drawable.over_background_00030
            ,R.drawable.over_background_00031,R.drawable.over_background_00032,R.drawable.over_background_00033,R.drawable.over_background_00034,R.drawable.over_background_00035,R.drawable.over_background_00036,R.drawable.over_background_00037,R.drawable.over_background_00038,R.drawable.over_background_00039};


    public static ImageView background,foreground,profile,donedone,back;
    public static FrameLayout stickerfram,backsticker,foresticker;
    public static Bitmap finalEditedImage;
    private FrameLayout mainimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        back = findViewById(R.id.back);
        remove = findViewById(R.id.remove);
        donedone = findViewById(R.id.done);
        profile = findViewById(R.id.profile);
        stickerfram = findViewById(R.id.stickerfram);
        backsticker = findViewById(R.id.backsticker);
        foresticker = findViewById(R.id.foresticker);
        background = findViewById(R.id.background);
        foreground = findViewById(R.id.foreground);
        mainitem = findViewById(R.id.mainitem);
        smoke12 = findViewById(R.id.smoke12);
        color_back = findViewById(R.id.color_back);
        color_fore = findViewById(R.id.color_fore);
        white_back = findViewById(R.id.white_back);
        white_fore = findViewById(R.id.white_fore);
        textsticker = findViewById(R.id.textsticker);
        mainimg = findViewById(R.id.mainimg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (profile.getDrawable()!=null){
            remove.setVisibility(View.VISIBLE);
        } else {
            remove.setVisibility(View.GONE);
        }

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setImageResource(0);
                remove.setVisibility(View.GONE);
            }
        });

//        ADS
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }

        interstitialAd = new InterstitialAd(
                EditingActivity.this,
                UtilsData.FB_Interstitial_AD);

        interstitialAd.loadAd(EnumSet.of(CacheFlag.VIDEO));
        interstitialAd.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
            }

            @Override
            public void onAdClicked(Ad ad) {
                super.onAdClicked(ad);
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                super.onInterstitialDisplayed(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }

                finalEditedImage = getMainFrameBitmap(mainimg);

                Intent intent = new Intent(EditingActivity.this,FinalActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                super.onLoggingImpression(ad);
            }
        });

        donedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    if (mCurrentView != null) {
                        mCurrentView.setInEdit(false);
                    }

                    finalEditedImage = getMainFrameBitmap(mainimg);

                    Intent intent = new Intent(EditingActivity.this,FinalActivity.class);
                    startActivity(intent);
                } else {
                    // Ad was loaded, show it!
                    interstitialAd.show();
                }

            }
        });


        RecyclerView.LayoutManager manager = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        MainAdapter adapter = new MainAdapter(EditingActivity.this);
        mainitem.setLayoutManager(manager);
        mainitem.setAdapter(adapter);

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Smoke12Adapter adapter1 = new Smoke12Adapter(EditingActivity.this);
        smoke12.setLayoutManager(manager1);
        smoke12.setAdapter(adapter1);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        CBackAdapter adapter2 = new CBackAdapter(EditingActivity.this);
        color_back.setLayoutManager(manager2);
        color_back.setAdapter(adapter2);

        RecyclerView.LayoutManager manager3 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        CForeAdapter adapter3 = new CForeAdapter(EditingActivity.this);
        color_fore.setLayoutManager(manager3);
        color_fore.setAdapter(adapter3);

        RecyclerView.LayoutManager manager4 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        WBackAdapter adapter4 = new WBackAdapter(EditingActivity.this);
        white_back.setLayoutManager(manager4);
        white_back.setAdapter(adapter4);

        RecyclerView.LayoutManager manager5 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        WForeAdapter adapter5 = new WForeAdapter(EditingActivity.this);
        white_fore.setLayoutManager(manager5);
        white_fore.setAdapter(adapter5);

        RecyclerView.LayoutManager manager6 = new LinearLayoutManager(EditingActivity.this,LinearLayoutManager.HORIZONTAL,false);
        TextstickerAdapter adapter6 = new TextstickerAdapter(EditingActivity.this);
        textsticker.setLayoutManager(manager6);
        textsticker.setAdapter(adapter6);

        stickerfram.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              try {
                  if (mCurrentView != null) {
                      mCurrentView.setInEdit(false);
                  }
              }catch (Exception e){}
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            imageurl = data.getData();
//            profile.setImageURI(imageurl);
            remove.setVisibility(View.VISIBLE);
            background.setImageResource(0);
            foreground.setImageResource(0);
            Glide.with(EditingActivity.this).load(imageurl).into(profile);
        }

    }

    private Bitmap getMainFrameBitmap(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

}
