package com.avinfo.smokename.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avinfo.smokename.R;
import com.avinfo.smokename.Utils.UtilsData;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.MediaView;
import com.facebook.ads.MediaViewListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSION_REQUEST = 99;
    private ImageView edit, share, more;

    //    Facebook
    private NativeAdLayout nativeAdLayout;
    private NativeAd nativeAd;
    private AdOptionsView adOptionsView;
    private MediaView nativeAdMedia;
    String TAG = "Native";
    private LinearLayout adChoicesContainer;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(this);
        more = findViewById(R.id.more);
        more.setOnClickListener(this);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);


        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }

        interstitialAd = new InterstitialAd(
                MainActivity.this,
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
                startActivity(new Intent(MainActivity.this, EditingActivity.class));
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                super.onLoggingImpression(ad);
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            // do nothing
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            // do nothing
        }



//        Facebook
        nativeAdLayout = findViewById(R.id.native_ad_container);
        adChoicesContainer = findViewById(R.id.ad_choices_container);

        try {

            nativeAd = new NativeAd(MainActivity.this, UtilsData.FB_Native_AD);
            nativeAd.loadAd();
            nativeAd.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    if (nativeAd == ad) {

                        Log.e(TAG, "onMediaDownloaded");
                    }
                }

                @Override
                public void onError(Ad ad, AdError adError) {
//                if (nativeAdStatus != null) {
//                    nativeAdStatus.setText("Ad failed to load: " + error.getErrorMessage());
//                }
//                Toast.makeText(getContext(), "Ad Failed To Load: " + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd == null || nativeAd != ad) {
                        // Race condition, load() called again before last ad was displayed
                        return;
                    }

                    if (nativeAdLayout == null) {
                        return;
                    }

                    // Unregister last ad
                    nativeAd.unregisterView();

//                if (nativeAdStatus != null) {
//                    nativeAdStatus.setText("");
//                }

                    if (adChoicesContainer != null) {
                        adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
                        adChoicesContainer.removeAllViews();
                        adChoicesContainer.addView(adOptionsView, 0);
                    }

                    inflateAd(nativeAd, nativeAdLayout);

                    // Registering a touch listener to log which ad component receives the touch event.
                    // We always return false from onTouch so that we don't swallow the touch event (which
                    // would prevent click events from reaching the NativeAd control).
                    // The touch listener could be used to do animations.
                    nativeAd.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                int i = view.getId();
                                if (i == R.id.native_ad_call_to_action) {
                                    Log.e(TAG, "Call to action button clicked");
                                } else if (i == R.id.native_ad_media) {
                                    Log.e(TAG, "Main image clicked");
                                } else {
                                    Log.e(TAG, "Other ad component clicked");
                                }
                            }
                            return false;
                        }
                    });
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Log.e(TAG, "onLoggingImpression");
                    nativeAdLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            });

        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.edit:
                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    startActivity(new Intent(MainActivity.this, EditingActivity.class));
                } else {
                    // Ad was loaded, show it!
                    interstitialAd.show();
                }
                break;
            case R.id.share:
                if (isReadStorageAllowed()) {

                    try {
                        Toast.makeText(MainActivity.this, "Share done", Toast.LENGTH_SHORT).show();
                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(),
                                b, "Title", null);
                        Uri imageUri = Uri.parse(path);
                        share.putExtra(Intent.EXTRA_TEXT, "*Smoke Effect* is Best Application for Smoke Photo And Name Editing" + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "\n\n" +
                                "*1.* Create Your Name Smoke Image" + "\n" +
                                "*2.* Create Your Photo Smoke Image" + "\n" +
                                "*3.* Best Smoke Effect" + "\n" +
                                "*4.* 200+ Smoke" + "\n" +
                                "*5.* 100+ Font Style" + "\n" +
                                "*6.* And Last You Can Crop Your Image" + "\n\n\n" +
                                "Please Install this Application on your \uD83D\uDCF2 and Give 5 Star \uD83C\uDF1F Rating with Long Review \uD83D\uDCDD" + "\n\n" +
                                "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47" + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "\n\n" +
                                "\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F");
                        share.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(share, "Share App Link Using..."));
                    } catch (Exception e) {
                        Log.e("Share", "onClick: " + e.getMessage());
                    }
                } else {
                    requestStoragePermission();
                }

//                File filepath = Environment.getExternalStorageDirectory();
//                String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String FileName = ts + ".jpg";
//                String _uri2 = filepath.getAbsolutePath() + "/" + "PicsEffect" + "/" + FileName;
//                _url = _uri2;//used in share image

//                create_Save_Image();
//
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/*");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Smoke Art  :- \n\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(_url)));
//                startActivity(Intent.createChooser(shareIntent, "Share Image using"));

                break;

            case R.id.more:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:AV Tube")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=AV Tube")));
                }
                break;

        }

    }

//    private void bannerAd() {
//
//        RelativeLayout bannerAdContainer;
//        AdView bannerAdView = null;
//
//        bannerAdContainer = findViewById(R.id.bannerAdContainer);
//
//        if (bannerAdView != null) {
//            bannerAdView.destroy();
//            bannerAdView = null;
//        }
//
////        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
//        bannerAdView = new AdView(this, UtilsData.FB_Banner_AD, AdSize.BANNER_HEIGHT_50);
//
//        bannerAdContainer.addView(bannerAdView);
//        bannerAdView.loadAd();
//
//        bannerAdView.setAdListener(new AbstractAdListener() {
//            @Override
//            public void onError(Ad ad, AdError error) {
//                super.onError(ad, error);
////                Toast.makeText(ActivityMain.this, error.getErrorMessage() , Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void inflateAd(NativeAd nativeAd, View adView) {
        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        nativeAdMedia.setListener(getMediaViewListener());

        // Setting the Text
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        sponsoredLabel.setText(R.string.sponsored);

        // You can use the following to specify the clickable areas.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdIcon);
        clickableViews.add(nativeAdMedia);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(
                nativeAdLayout,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

        // Optional: tag views
        NativeAdBase.NativeComponentTag.tagView(nativeAdIcon, NativeAdBase.NativeComponentTag.AD_ICON);
        NativeAdBase.NativeComponentTag.tagView(nativeAdTitle, NativeAdBase.NativeComponentTag.AD_TITLE);
        NativeAdBase.NativeComponentTag.tagView(nativeAdBody, NativeAdBase.NativeComponentTag.AD_BODY);
        NativeAdBase.NativeComponentTag.tagView(nativeAdSocialContext, NativeAdBase.NativeComponentTag.AD_SOCIAL_CONTEXT);
        NativeAdBase.NativeComponentTag.tagView(nativeAdCallToAction, NativeAdBase.NativeComponentTag.AD_CALL_TO_ACTION);
    }

    private static MediaViewListener getMediaViewListener() {
        final String TAG = "Native";
        return new MediaViewListener() {
            @Override
            public void onVolumeChange(MediaView mediaView, float volume) {
                Log.e(TAG, "MediaViewEvent: Volume " + volume);
            }

            @Override
            public void onPause(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: Paused");
            }

            @Override
            public void onPlay(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: Play");
            }

            @Override
            public void onFullscreenBackground(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: FullscreenBackground");
            }

            @Override
            public void onFullscreenForeground(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: FullscreenForeground");
            }

            @Override
            public void onExitFullscreen(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: ExitFullscreen");
            }

            @Override
            public void onEnterFullscreen(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: EnterFullscreen");
            }

            @Override
            public void onComplete(MediaView mediaView) {
                Log.e(TAG, "MediaViewEvent: Completed");
            }
        };
    }

    final String[] permision = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                return true;
            } else {
                requestPermissions(permision, 100);
            }
        } else {
            return true;
        }
        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
    }

}
