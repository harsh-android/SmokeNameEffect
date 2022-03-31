package com.avinfo.smokename.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.avinfo.smokename.R;
import com.avinfo.smokename.Utils.UtilsData;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;

public class FinalActivity extends AppCompatActivity {

    private ImageView done_image, download, share, cropimg, back;
    TextView path;
    public static String _url;
    private InterstitialAd interstitialAd;
    Bitmap bitmap25;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (CropActivity.aa==1){
                CropActivity.aa=0;
                done_image.setImageBitmap(CropActivity.bitmap1);
                bitmap25 = CropActivity.bitmap1;
            }
        }catch (Exception e){}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_final);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cropimg = findViewById(R.id.cropimg);
        cropimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinalActivity.this,CropActivity.class));
            }
        });

        bannerAd();

        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }

        interstitialAd = new InterstitialAd(
                FinalActivity.this,
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
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                super.onLoggingImpression(ad);
            }
        });

        share = (ImageView) findViewById(R.id.share);
        path = (TextView) findViewById(R.id.path);
        download = (ImageView) findViewById(R.id.download);
        done_image = (ImageView) findViewById(R.id.done_image);

        done_image.setImageBitmap(EditingActivity.finalEditedImage);
        bitmap25 = EditingActivity.finalEditedImage;

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_Save_Image();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isReadStorageAllowed()) {

                    try {
                        Toast.makeText(FinalActivity.this, "Share done", Toast.LENGTH_SHORT).show();
                        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.splash);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap25.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(FinalActivity.this.getContentResolver(),
                                bitmap25, "Title", null);
                        Uri imageUri = Uri.parse(path);
                        share.putExtra(Intent.EXTRA_TEXT, "*Smoke Effect* is Best Application for Smoke Photo And Name Editing" + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + FinalActivity.this.getPackageName() + "\n\n" +
                                "*1.* Create Your Name Smoke Image" + "\n" +
                                "*2.* Create Your Photo Smoke Image" + "\n" +
                                "*3.* Best Smoke Effect" + "\n" +
                                "*4.* 200+ Smoke" + "\n" +
                                "*5.* 100+ Font Style" + "\n" +
                                "*6.* And Last You Can Crop Your Image" + "\n\n\n" +
                                "Please Install this Application on your \uD83D\uDCF2 and Give 5 Star \uD83C\uDF1F Rating with Long Review \uD83D\uDCDD" + "\n\n" +
                                "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47" + "\n\n\n" +
                                "https://play.google.com/store/apps/details?id=" + FinalActivity.this.getPackageName() + "\n\n" +
                                "\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F\uD83D\uDE4F");
                        share.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(share, "Share App Link Using..."));
                    } catch (Exception e) {
                        Log.e("Share", "onClick: "+e.getMessage() );
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
            }
        });

    }

    public Bitmap finalEditedImage;

    private void create_Save_Image() {
//        finalEditedImage = getMainFrameBitmap(done_image);
        finalEditedImage = imageView2Bitmap(done_image);
        saveImage(finalEditedImage);

    }

    private Bitmap imageView2Bitmap(ImageView view) {
        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        return bitmap;
    }

    private Bitmap getMainFrameBitmap(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    final String[] permision = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(FinalActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(FinalActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
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
        ActivityCompat.requestPermissions(FinalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
    }


    private void saveImage(Bitmap bitmap2) {
        Log.e("TAG", "saveImageInCache is called");
        Bitmap bitmap;
        OutputStream output;

        // Retrieve the image from the res folder
        bitmap = bitmap2;

        File filepath = Environment.getExternalStorageDirectory();
        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/" + "Smoke Art");
        dir.mkdirs();

        // Create a name for the saved image
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String FileName = ts + ".png";
        File file = new File(dir, FileName);
        file.renameTo(file);
        String _uri = "file://" + filepath.getAbsolutePath() + "/" + "Smoke Art" + "/" + FileName;
        //for share image
        String _uri2 = filepath.getAbsolutePath() + "/" + "Smoke Art" + "/" + FileName;
        _url = _uri2;//used in share image
        Log.e("cache uri=", _uri);
        path.setText("Path :-" + "\t" + _url);
        Toast.makeText(this, "Image is Save JPEG", Toast.LENGTH_SHORT).show();
        MediaScannerConnection.scanFile(this, new String[]{_url}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + path + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
            }
        });
        try {
            output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void bannerAd() {

        RelativeLayout bannerAdContainer;
        AdView bannerAdView = null;

        bannerAdContainer = findViewById(R.id.bannerAdContainer);

        if (bannerAdView != null) {
            bannerAdView.destroy();
            bannerAdView = null;
        }

//        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        bannerAdView = new AdView(this, UtilsData.FB_Banner_AD, AdSize.BANNER_HEIGHT_50);

        bannerAdContainer.addView(bannerAdView);
        bannerAdView.loadAd();

        bannerAdView.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
//                Toast.makeText(ActivityMain.this, error.getErrorMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
