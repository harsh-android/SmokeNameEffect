package com.avinfo.smokename.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avinfo.smokename.Activity.EditingActivity;
import com.avinfo.smokename.Activity.MainActivity;
import com.avinfo.smokename.R;
import com.avinfo.smokename.Utils.StickerView;
import com.avinfo.smokename.Utils.UtilsData;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAd;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import java.util.EnumSet;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.avinfo.smokename.Activity.EditingActivity.smoke12;
import static com.avinfo.smokename.Activity.EditingActivity.color_back;
import static com.avinfo.smokename.Activity.EditingActivity.color_fore;
import static com.avinfo.smokename.Activity.EditingActivity.stickerfram;
import static com.avinfo.smokename.Activity.EditingActivity.textsticker;
import static com.avinfo.smokename.Activity.EditingActivity.white_back;
import static com.avinfo.smokename.Activity.EditingActivity.white_fore;
import static com.avinfo.smokename.Adapters.ColorAdapter.color_array;
import static com.avinfo.smokename.Adapters.PatternAdapter.pattern_array;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public int[] mainiconlist = new int[]{R.drawable.over_background_00001, R.drawable.background, R.drawable.foreground/*, R.drawable.normalsmoke_00012, R.drawable.normalsmoke_00014*/, R.drawable.text, R.drawable.add_image, R.drawable.text_00007};

    public String[] maintextlist = new String[]{"Smoke", "Color Background", "Color Foreground"/*, "White Background", "White Foreground"*/, "Add Text", "Add Image", "Text Sticker"};

    private ImageView back;
    private EditText text_edit;
    private LinearLayout text_add,text_size,text_color,text_style,text_pattern,text_blur;
    private SeekBar text_seek;
    private TextView text_sticker,size_done,color_done,style_done,pattern_done,blur_done;
    private ImageView text_done,text_to_sticker;
    private GridView color_grid,style_grid,pattern_grid;
    private LinearLayout text_size_layout,text_color_layout,text_style_layout,text_pattern_layout,text_blur_layout;
    private LabelToggle no;
    private SingleSelectToggleGroup blur_group;
    private Bitmap bitmap;
    private Shader shader;
    private ImageView add,size,color1,style,pattern,blur;
    private CardView add_text_card;
    public static ImageView pixel,flash;
    public static StickerView mCurrentView;
    public static Bitmap framebitmap;
    private static Bitmap b;
    private static Canvas c;


    Activity activity;

    private InterstitialAd interstitialAd;

    public MainAdapter(EditingActivity editingActivity) {
        activity = editingActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }

        interstitialAd = new InterstitialAd(
                activity,
                UtilsData.FB_Interstitial_AD);

        interstitialAd.loadAd(EnumSet.of(CacheFlag.VIDEO));


        holder.text1.setSelected(true);

        holder.icon1.setImageResource(mainiconlist[position]);
        holder.text1.setText(maintextlist[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (position) {

                    case 0:
                        Visible(smoke12);
                        Gone(color_back, color_fore, white_back, white_fore,textsticker);
                        break;

                    case 1:
                        Visible(color_back);
                        Gone(smoke12, color_fore, white_back, white_fore,textsticker);
                        break;

                    case 2:
                        Visible(color_fore);
                        Gone(color_back, smoke12, white_back, white_fore,textsticker);
                        break;

                    /*case 3:
                        Visible(white_back);
                        Gone(color_back, color_fore, smoke12, white_fore,textsticker);
                        break;

                    case 4:
                        Visible(white_fore);
                        Gone(color_back, color_fore, white_back, smoke12,textsticker);
                        break;*/

                    case 3:

//                TODO Text Dialog Start

                        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.text_dialog);
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        back = (ImageView) dialog.findViewById(R.id.back);
                        text_to_sticker = (ImageView) dialog.findViewById(R.id.text_to_sticker);
                        text_done = (ImageView) dialog.findViewById(R.id.text_done);
                        add = (ImageView) dialog.findViewById(R.id.add);
                        size = (ImageView) dialog.findViewById(R.id.size);
                        color1 = (ImageView) dialog.findViewById(R.id.color);
                        style = (ImageView) dialog.findViewById(R.id.style);
                        pattern = (ImageView) dialog.findViewById(R.id.pattern);
                        blur = (ImageView) dialog.findViewById(R.id.blur);

                        text_blur_layout = (LinearLayout) dialog.findViewById(R.id.text_blur_layout);
                        text_pattern_layout = (LinearLayout) dialog.findViewById(R.id.text_pattern_layout);
                        text_style_layout = (LinearLayout) dialog.findViewById(R.id.text_style_layout);
                        text_color_layout = (LinearLayout) dialog.findViewById(R.id.text_color_layout);
                        text_size_layout = (LinearLayout) dialog.findViewById(R.id.text_size_layout);
                        text_add = (LinearLayout) dialog.findViewById(R.id.text_add);
                        text_size = (LinearLayout) dialog.findViewById(R.id.text_size);
                        text_color = (LinearLayout) dialog.findViewById(R.id.text_color);
                        text_style = (LinearLayout) dialog.findViewById(R.id.text_style);
                        text_pattern = (LinearLayout) dialog.findViewById(R.id.text_pattern);
                        text_blur = (LinearLayout) dialog.findViewById(R.id.text_blur);

                        no = (LabelToggle) dialog.findViewById(R.id.no);
                        blur_group = (SingleSelectToggleGroup) dialog.findViewById(R.id.blur_group);

                        add_text_card = (CardView) dialog.findViewById(R.id.add_text_card);

                        pattern_grid = (GridView) dialog.findViewById(R.id.pattern_grid);
                        style_grid = (GridView) dialog.findViewById(R.id.style_grid);
                        color_grid = (GridView) dialog.findViewById(R.id.color_grid);

                        blur_done = (TextView) dialog.findViewById(R.id.blur_done);
                        pattern_done = (TextView) dialog.findViewById(R.id.pattern_done);
                        style_done = (TextView) dialog.findViewById(R.id.style_done);
                        size_done = (TextView) dialog.findViewById(R.id.size_done);
                        color_done = (TextView) dialog.findViewById(R.id.color_done);
                        text_sticker = (TextView) dialog.findViewById(R.id.text_sticker);

                        text_seek = (SeekBar) dialog.findViewById(R.id.text_seek);

                        text_edit = (EditText) dialog.findViewById(R.id.text_edit);

                        add_text_card.setVisibility(View.GONE);

                        add.setColorFilter(R.color.textadd);
                        size.setColorFilter(R.color.textsize);
                        color1.setColorFilter(R.color.textcolor);
                        style.setColorFilter(R.color.textstyle);
                        pattern.setColorFilter(R.color.textpattern);
                        blur.setColorFilter(R.color.textblur);

                        no.setChecked(true);

                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        text_blur.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hideSoftKeyboard(text_edit);
                                add_text_card.setVisibility(View.GONE);
                                text_style_layout.setVisibility(View.GONE);
                                text_size_layout.setVisibility(View.GONE);
                                text_color_layout.setVisibility(View.GONE);
                                text_pattern_layout.setVisibility(View.GONE);

                                if (text_sticker.getText().length() != 0) {

                                    if (text_blur_layout.getVisibility() == View.GONE) {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                        text_blur_layout.startAnimation(animation);
                                        text_blur_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                        text_blur_layout.startAnimation(animation);
                                        text_blur_layout.setVisibility(View.GONE);
                                    }

                                }

                            }
                        });

                        blur_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();

                            }
                        });

                        blur_group.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {

                                switch (checkedId) {

                                    case R.id.no:
                                        text_sticker.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                                        // Clear any previous MaskFilter
                                        text_sticker.getPaint().setMaskFilter(null);
                                        break;

                                    case R.id.inner:
                                        applyBlurMaskFilter(text_sticker, BlurMaskFilter.Blur.INNER);
                                        break;

                                    case R.id.normal:
                                        applyBlurMaskFilter(text_sticker, BlurMaskFilter.Blur.NORMAL);
                                        break;

                                    case R.id.outer:
                                        applyBlurMaskFilter(text_sticker, BlurMaskFilter.Blur.OUTER);
                                        break;

                                    case R.id.solid:
                                        applyBlurMaskFilter(text_sticker, BlurMaskFilter.Blur.SOLID);
                                        break;

                                }

                            }
                        });

                        text_to_sticker.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {

                                } else {
                                    interstitialAd.show();
                                }
                                dialog.dismiss();
                                String aa = text_sticker.getText().toString();
                                if (!aa.isEmpty()) {
                                    ImageView imageView = new ImageView(activity);
                                    text_sticker.buildDrawingCache();
                                    Bitmap bitmap = text_sticker.getDrawingCache();
                                    imageView.setImageBitmap(bitmap);
                                    framebitmap = loadBitmapFromView(imageView);
                                    framebitmap = CropBitmapTransparency(framebitmap);

                                    final StickerView stickerView = new StickerView(activity);
                                    stickerView.setBitmap(framebitmap);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                                    stickerfram.addView(stickerView, layoutParams);
                                    setCurrentEdit(stickerView);
                                    stickerView.setOperationListener(new StickerView.OperationListener() {
                                        @Override
                                        public void onDeleteClick() {
                                            stickerfram.removeView(stickerView);
                                        }

                                        @Override
                                        public void onEdit(StickerView stickerView) {

                                            mCurrentView.setInEdit(false);
                                            mCurrentView = stickerView;
                                            mCurrentView.setInEdit(true);

                                        }

                                        @Override
                                        public void onTop(StickerView stickerView) {

                                        }
                                    });


                                }


                            }
                        });


                        text_pattern.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hideSoftKeyboard(text_edit);
                                add_text_card.setVisibility(View.GONE);
                                text_style_layout.setVisibility(View.GONE);
                                text_size_layout.setVisibility(View.GONE);
                                text_color_layout.setVisibility(View.GONE);
                                text_blur_layout.setVisibility(View.GONE);


                                if (text_sticker.getText().length() != 0) {

                                    if (text_pattern_layout.getVisibility() == View.GONE) {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                        text_pattern_layout.startAnimation(animation);
                                        text_pattern_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                        text_pattern_layout.startAnimation(animation);
                                        text_pattern_layout.setVisibility(View.GONE);
                                    }

                                }

                                PatternAdapter adapter4 = new PatternAdapter(activity);
                                pattern_grid.setAdapter(adapter4);

                            }
                        });

                        pattern_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                bitmap = BitmapFactory.decodeResource(activity.getResources(), pattern_array[position]);
                                shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                                text_sticker.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                                text_sticker.getPaint().setShader(shader);

                            }
                        });

                        pattern_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                text_pattern_layout.startAnimation(animation);
                                text_pattern_layout.setVisibility(View.GONE);

                            }
                        });

                        text_style.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hideSoftKeyboard(text_edit);
                                add_text_card.setVisibility(View.GONE);
                                text_blur_layout.setVisibility(View.GONE);
                                text_size_layout.setVisibility(View.GONE);
                                text_color_layout.setVisibility(View.GONE);
                                text_pattern_layout.setVisibility(View.GONE);


                                if (text_sticker.getText().length() != 0) {

                                    if (text_style_layout.getVisibility() == View.GONE) {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                        text_style_layout.startAnimation(animation);
                                        text_style_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                        text_style_layout.startAnimation(animation);
                                        text_style_layout.setVisibility(View.GONE);
                                    }

                                    FontAdapter adapter4 = new FontAdapter(activity);
                                    style_grid.setAdapter(adapter4);
                                }
                            }
                        });

                        style_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                text_style_layout.startAnimation(animation);
                                text_style_layout.setVisibility(View.GONE);
                            }
                        });

                        style_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                switch (position) {
                                    case 0:
                                        Typeface typeface0 = Typeface.createFromAsset(activity.getAssets(), "font36.TTF");
                                        text_sticker.setTypeface(typeface0);
                                        break;

                                    case 1:
                                        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "font1.ttf");
                                        text_sticker.setTypeface(typeface);
                                        break;

                                    case 2:
                                        Typeface typeface2 = Typeface.createFromAsset(activity.getAssets(), "font2.ttf");
                                        text_sticker.setTypeface(typeface2);
                                        break;

                                    case 3:
                                        Typeface typeface3 = Typeface.createFromAsset(activity.getAssets(), "font3.ttf");
                                        text_sticker.setTypeface(typeface3);
                                        break;

                                    case 4:
                                        Typeface typeface4 = Typeface.createFromAsset(activity.getAssets(), "font4.TTF");
                                        text_sticker.setTypeface(typeface4);
                                        break;

                                    case 5:
                                        Typeface typeface5 = Typeface.createFromAsset(activity.getAssets(), "font5.ttf");
                                        text_sticker.setTypeface(typeface5);
                                        break;

                                    case 6:
                                        Typeface typeface6 = Typeface.createFromAsset(activity.getAssets(), "font6.TTF");
                                        text_sticker.setTypeface(typeface6);
                                        break;

                                    case 7:
                                        Typeface typeface7 = Typeface.createFromAsset(activity.getAssets(), "font7.ttf");
                                        text_sticker.setTypeface(typeface7);
                                        break;

                                    case 8:
                                        Typeface typeface8 = Typeface.createFromAsset(activity.getAssets(), "font8.ttf");
                                        text_sticker.setTypeface(typeface8);
                                        break;

                                    case 9:
                                        Typeface typeface9 = Typeface.createFromAsset(activity.getAssets(), "font9.ttf");
                                        text_sticker.setTypeface(typeface9);
                                        break;

                                    case 10:
                                        Typeface typeface10 = Typeface.createFromAsset(activity.getAssets(), "font10.TTF");
                                        text_sticker.setTypeface(typeface10);
                                        break;

                                    case 11:
                                        Typeface typeface11 = Typeface.createFromAsset(activity.getAssets(), "font11.ttf");
                                        text_sticker.setTypeface(typeface11);
                                        break;

                                    case 12:
                                        Typeface typeface12 = Typeface.createFromAsset(activity.getAssets(), "font12.ttf");
                                        text_sticker.setTypeface(typeface12);
                                        break;

                                    case 13:
                                        Typeface typeface13 = Typeface.createFromAsset(activity.getAssets(), "font13.otf");
                                        text_sticker.setTypeface(typeface13);
                                        break;

                                    case 14:
                                        Typeface typeface14 = Typeface.createFromAsset(activity.getAssets(), "font14.TTF");
                                        text_sticker.setTypeface(typeface14);
                                        break;

                                    case 15:
                                        Typeface typeface15 = Typeface.createFromAsset(activity.getAssets(), "font15.ttf");
                                        text_sticker.setTypeface(typeface15);
                                        break;

                                    case 16:
                                        Typeface typeface16 = Typeface.createFromAsset(activity.getAssets(), "font16.TTF");
                                        text_sticker.setTypeface(typeface16);
                                        break;

                                    case 17:
                                        Typeface typeface17 = Typeface.createFromAsset(activity.getAssets(), "font17.ttf");
                                        text_sticker.setTypeface(typeface17);
                                        break;

                                    case 18:
                                        Typeface typeface18 = Typeface.createFromAsset(activity.getAssets(), "font18.ttf");
                                        text_sticker.setTypeface(typeface18);
                                        break;

                                    case 19:
                                        Typeface typeface19 = Typeface.createFromAsset(activity.getAssets(), "font19.ttf");
                                        text_sticker.setTypeface(typeface19);
                                        break;

                                    case 20:
                                        Typeface typeface20 = Typeface.createFromAsset(activity.getAssets(), "font20.ttf");
                                        text_sticker.setTypeface(typeface20);
                                        break;
                                    case 21:
                                        Typeface typeface21 = Typeface.createFromAsset(activity.getAssets(), "font21.ttf");
                                        text_sticker.setTypeface(typeface21);
                                        break;
                                    case 22:
                                        Typeface typeface22 = Typeface.createFromAsset(activity.getAssets(), "font22.ttf");
                                        text_sticker.setTypeface(typeface22);
                                        break;

                                    case 23:
                                        Typeface typeface23 = Typeface.createFromAsset(activity.getAssets(), "font23.ttf");
                                        text_sticker.setTypeface(typeface23);
                                        break;

                                    case 24:
                                        Typeface typeface24 = Typeface.createFromAsset(activity.getAssets(), "font24.ttf");
                                        text_sticker.setTypeface(typeface24);
                                        break;

                                    case 25:
                                        Typeface typeface25 = Typeface.createFromAsset(activity.getAssets(), "font25.ttf");
                                        text_sticker.setTypeface(typeface25);
                                        break;

                                    case 26:
                                        Typeface typeface26 = Typeface.createFromAsset(activity.getAssets(), "font26.ttf");
                                        text_sticker.setTypeface(typeface26);
                                        break;

                                    case 27:
                                        Typeface typeface27 = Typeface.createFromAsset(activity.getAssets(), "font27.ttf");
                                        text_sticker.setTypeface(typeface27);
                                        break;

                                    case 28:
                                        Typeface typeface28 = Typeface.createFromAsset(activity.getAssets(), "font28.TTF");
                                        text_sticker.setTypeface(typeface28);
                                        break;

                                    case 29:
                                        Typeface typeface29 = Typeface.createFromAsset(activity.getAssets(), "font29.ttf");
                                        text_sticker.setTypeface(typeface29);
                                        break;

                                    case 30:
                                        Typeface typeface30 = Typeface.createFromAsset(activity.getAssets(), "font30.ttf");
                                        text_sticker.setTypeface(typeface30);
                                        break;

                                    case 31:
                                        Typeface typeface31 = Typeface.createFromAsset(activity.getAssets(), "font31.otf");
                                        text_sticker.setTypeface(typeface31);
                                        break;

                                    case 32:
                                        Typeface typeface32 = Typeface.createFromAsset(activity.getAssets(), "font32.ttf");
                                        text_sticker.setTypeface(typeface32);
                                        break;

                                    case 33:
                                        Typeface typeface33 = Typeface.createFromAsset(activity.getAssets(), "font33.OTF");
                                        text_sticker.setTypeface(typeface33);
                                        break;

                                    case 34:
                                        Typeface typeface34 = Typeface.createFromAsset(activity.getAssets(), "font34.ttf");
                                        text_sticker.setTypeface(typeface34);
                                        break;

                                    case 35:
                                        Typeface typeface35 = Typeface.createFromAsset(activity.getAssets(), "font35.TTF");
                                        text_sticker.setTypeface(typeface35);
                                        break;

                                    case 36:
                                        Typeface typeface36 = Typeface.createFromAsset(activity.getAssets(), "font37.otf");
                                        text_sticker.setTypeface(typeface36);
                                        break;

                                    case 37:
                                        Typeface typeface37 = Typeface.createFromAsset(activity.getAssets(), "font38.otf");
                                        text_sticker.setTypeface(typeface37);
                                        break;

                                    case 38:
                                        Typeface typeface38 = Typeface.createFromAsset(activity.getAssets(), "font39.otf");
                                        text_sticker.setTypeface(typeface38);
                                        break;

                                    case 39:
                                        Typeface typeface39 = Typeface.createFromAsset(activity.getAssets(), "font40.otf");
                                        text_sticker.setTypeface(typeface39);
                                        break;

                                    case 40:
                                        Typeface typeface40 = Typeface.createFromAsset(activity.getAssets(), "font41.otf");
                                        text_sticker.setTypeface(typeface40);
                                        break;

                                    case 41:
                                        Typeface typeface41 = Typeface.createFromAsset(activity.getAssets(), "font42.otf");
                                        text_sticker.setTypeface(typeface41);
                                        break;

                                    case 42:
                                        Typeface typeface42 = Typeface.createFromAsset(activity.getAssets(), "font43.otf");
                                        text_sticker.setTypeface(typeface42);
                                        break;

                                    case 43:
                                        Typeface typeface43 = Typeface.createFromAsset(activity.getAssets(), "font44.otf");
                                        text_sticker.setTypeface(typeface43);
                                        break;

                                    case 44:
                                        Typeface typeface44 = Typeface.createFromAsset(activity.getAssets(), "font45.otf");
                                        text_sticker.setTypeface(typeface44);
                                        break;

                                    case 45:
                                        Typeface typeface45 = Typeface.createFromAsset(activity.getAssets(), "font46.otf");
                                        text_sticker.setTypeface(typeface45);
                                        break;

                                    case 46:
                                        Typeface typeface46 = Typeface.createFromAsset(activity.getAssets(), "font47.otf");
                                        text_sticker.setTypeface(typeface46);
                                        break;

                                    case 47:
                                        Typeface typeface47 = Typeface.createFromAsset(activity.getAssets(), "font48.otf");
                                        text_sticker.setTypeface(typeface47);
                                        break;

                                    case 48:
                                        Typeface typeface48 = Typeface.createFromAsset(activity.getAssets(), "font49.otf");
                                        text_sticker.setTypeface(typeface48);
                                        break;

                                    case 49:
                                        Typeface typeface49 = Typeface.createFromAsset(activity.getAssets(), "font50.otf");
                                        text_sticker.setTypeface(typeface49);
                                        break;

                                    case 50:
                                        Typeface typeface50 = Typeface.createFromAsset(activity.getAssets(), "font51.otf");
                                        text_sticker.setTypeface(typeface50);
                                        break;

                                    case 51:
                                        Typeface typeface51 = Typeface.createFromAsset(activity.getAssets(), "font52.otf");
                                        text_sticker.setTypeface(typeface51);
                                        break;

                                    case 52:
                                        Typeface typeface52 = Typeface.createFromAsset(activity.getAssets(), "font53.otf");
                                        text_sticker.setTypeface(typeface52);
                                        break;

                                    case 53:
                                        Typeface typeface53 = Typeface.createFromAsset(activity.getAssets(), "font54.otf");
                                        text_sticker.setTypeface(typeface53);
                                        break;

                                    case 54:
                                        Typeface typeface54 = Typeface.createFromAsset(activity.getAssets(), "font55.otf");
                                        text_sticker.setTypeface(typeface54);
                                        break;

                                    case 55:
                                        Typeface typeface55 = Typeface.createFromAsset(activity.getAssets(), "font56.otf");
                                        text_sticker.setTypeface(typeface55);
                                        break;

                                    case 56:
                                        Typeface typeface56 = Typeface.createFromAsset(activity.getAssets(), "font57.otf");
                                        text_sticker.setTypeface(typeface56);
                                        break;

                                    case 57:
                                        Typeface typeface57 = Typeface.createFromAsset(activity.getAssets(), "font58.otf");
                                        text_sticker.setTypeface(typeface57);
                                        break;

                                    case 58:
                                        Typeface typeface58 = Typeface.createFromAsset(activity.getAssets(), "font59.otf");
                                        text_sticker.setTypeface(typeface58);
                                        break;

                                    case 59:
                                        Typeface typeface59 = Typeface.createFromAsset(activity.getAssets(), "font60.otf");
                                        text_sticker.setTypeface(typeface59);
                                        break;

                                    case 60:
                                        Typeface typeface60 = Typeface.createFromAsset(activity.getAssets(), "font61.otf");
                                        text_sticker.setTypeface(typeface60);
                                        break;

                                    case 61:
                                        Typeface typeface61 = Typeface.createFromAsset(activity.getAssets(), "font62.otf");
                                        text_sticker.setTypeface(typeface61);
                                        break;

                                    case 62:
                                        Typeface typeface62 = Typeface.createFromAsset(activity.getAssets(), "font63.otf");
                                        text_sticker.setTypeface(typeface62);
                                        break;

                                    case 63:
                                        Typeface typeface63 = Typeface.createFromAsset(activity.getAssets(), "font64.otf");
                                        text_sticker.setTypeface(typeface63);
                                        break;

                                    case 64:
                                        Typeface typeface64 = Typeface.createFromAsset(activity.getAssets(), "font65.otf");
                                        text_sticker.setTypeface(typeface64);
                                        break;

                                    case 65:
                                        Typeface typeface65 = Typeface.createFromAsset(activity.getAssets(), "font66.otf");
                                        text_sticker.setTypeface(typeface65);
                                        break;

                                    case 66:
                                        Typeface typeface66 = Typeface.createFromAsset(activity.getAssets(), "font67.otf");
                                        text_sticker.setTypeface(typeface66);
                                        break;

                                    case 67:
                                        Typeface typeface67 = Typeface.createFromAsset(activity.getAssets(), "font68.otf");
                                        text_sticker.setTypeface(typeface67);
                                        break;

                                    case 68:
                                        Typeface typeface68 = Typeface.createFromAsset(activity.getAssets(), "font69.otf");
                                        text_sticker.setTypeface(typeface68);
                                        break;

                                    case 69:
                                        Typeface typeface69 = Typeface.createFromAsset(activity.getAssets(), "font70.otf");
                                        text_sticker.setTypeface(typeface69);
                                        break;

                                    case 70:
                                        Typeface typeface70 = Typeface.createFromAsset(activity.getAssets(), "font71.otf");
                                        text_sticker.setTypeface(typeface70);
                                        break;

                                    case 71:
                                        Typeface typeface71 = Typeface.createFromAsset(activity.getAssets(), "font72.otf");
                                        text_sticker.setTypeface(typeface71);
                                        break;

                                    case 72:
                                        Typeface typeface72 = Typeface.createFromAsset(activity.getAssets(), "font73.otf");
                                        text_sticker.setTypeface(typeface72);
                                        break;

                                    case 73:
                                        Typeface typeface73 = Typeface.createFromAsset(activity.getAssets(), "font74.otf");
                                        text_sticker.setTypeface(typeface73);
                                        break;

                                    case 74:
                                        Typeface typeface74 = Typeface.createFromAsset(activity.getAssets(), "font75.otf");
                                        text_sticker.setTypeface(typeface74);
                                        break;

                                    case 75:
                                        Typeface typeface75 = Typeface.createFromAsset(activity.getAssets(), "font76.otf");
                                        text_sticker.setTypeface(typeface75);
                                        break;

                                    case 76:
                                        Typeface typeface76 = Typeface.createFromAsset(activity.getAssets(), "font77.otf");
                                        text_sticker.setTypeface(typeface76);
                                        break;

                                    case 77:
                                        Typeface typeface77 = Typeface.createFromAsset(activity.getAssets(), "font78.otf");
                                        text_sticker.setTypeface(typeface77);
                                        break;

                                    case 78:
                                        Typeface typeface78 = Typeface.createFromAsset(activity.getAssets(), "font79.otf");
                                        text_sticker.setTypeface(typeface78);
                                        break;

                                    case 79:
                                        Typeface typeface79 = Typeface.createFromAsset(activity.getAssets(), "font80.otf");
                                        text_sticker.setTypeface(typeface79);
                                        break;

                                    case 80:
                                        Typeface typeface80 = Typeface.createFromAsset(activity.getAssets(), "font81.ttf");
                                        text_sticker.setTypeface(typeface80);
                                        break;

                                    case 81:
                                        Typeface typeface81 = Typeface.createFromAsset(activity.getAssets(), "font82.ttf");
                                        text_sticker.setTypeface(typeface81);
                                        break;

                                    case 82:
                                        Typeface typeface82 = Typeface.createFromAsset(activity.getAssets(), "font83.ttf");
                                        text_sticker.setTypeface(typeface82);
                                        break;

                                    case 83:
                                        Typeface typeface83 = Typeface.createFromAsset(activity.getAssets(), "font84.ttf");
                                        text_sticker.setTypeface(typeface83);
                                        break;

                                    case 84:
                                        Typeface typeface84 = Typeface.createFromAsset(activity.getAssets(), "font85.ttf");
                                        text_sticker.setTypeface(typeface84);
                                        break;

                                    case 85:
                                        Typeface typeface85 = Typeface.createFromAsset(activity.getAssets(), "font86.ttf");
                                        text_sticker.setTypeface(typeface85);
                                        break;

                                    case 86:
                                        Typeface typeface86 = Typeface.createFromAsset(activity.getAssets(), "font87.ttf");
                                        text_sticker.setTypeface(typeface86);
                                        break;

                                    case 87:
                                        Typeface typeface87 = Typeface.createFromAsset(activity.getAssets(), "font88.ttf");
                                        text_sticker.setTypeface(typeface87);
                                        break;

                                    case 88:
                                        Typeface typeface88 = Typeface.createFromAsset(activity.getAssets(), "font89.ttf");
                                        text_sticker.setTypeface(typeface88);
                                        break;

                                    case 89:
                                        Typeface typeface89 = Typeface.createFromAsset(activity.getAssets(), "font90.ttf");
                                        text_sticker.setTypeface(typeface89);
                                        break;

                                    case 90:
                                        Typeface typeface90 = Typeface.createFromAsset(activity.getAssets(), "font91.ttf");
                                        text_sticker.setTypeface(typeface90);
                                        break;

                                    case 91:
                                        Typeface typeface91 = Typeface.createFromAsset(activity.getAssets(), "font92.ttf");
                                        text_sticker.setTypeface(typeface91);
                                        break;

                                    case 92:
                                        Typeface typeface92 = Typeface.createFromAsset(activity.getAssets(), "font93.ttf");
                                        text_sticker.setTypeface(typeface92);
                                        break;

                                    case 93:
                                        Typeface typeface93 = Typeface.createFromAsset(activity.getAssets(), "font94.ttf");
                                        text_sticker.setTypeface(typeface93);
                                        break;

                                    case 94:
                                        Typeface typeface94 = Typeface.createFromAsset(activity.getAssets(), "font95.ttf");
                                        text_sticker.setTypeface(typeface94);
                                        break;

                                    case 95:
                                        Typeface typeface95 = Typeface.createFromAsset(activity.getAssets(), "font96.ttf");
                                        text_sticker.setTypeface(typeface95);
                                        break;

                                    case 96:
                                        Typeface typeface96 = Typeface.createFromAsset(activity.getAssets(), "font97.ttf");
                                        text_sticker.setTypeface(typeface96);
                                        break;

                                    case 97:
                                        Typeface typeface97 = Typeface.createFromAsset(activity.getAssets(), "font98.ttf");
                                        text_sticker.setTypeface(typeface97);
                                        break;

                                    case 98:
                                        Typeface typeface98 = Typeface.createFromAsset(activity.getAssets(), "font99.ttf");
                                        text_sticker.setTypeface(typeface98);
                                        break;

                                }
                            }
                        });


                        text_size.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hideSoftKeyboard(text_edit);
                                add_text_card.setVisibility(View.GONE);
                                text_style_layout.setVisibility(View.GONE);
                                text_blur_layout.setVisibility(View.GONE);
                                text_color_layout.setVisibility(View.GONE);
                                text_pattern_layout.setVisibility(View.GONE);


                                if (text_sticker.getText().length() != 0) {

                                    if (text_size_layout.getVisibility() == View.GONE) {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                        text_size_layout.startAnimation(animation);
                                        text_size_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                        text_size_layout.startAnimation(animation);
                                        text_size_layout.setVisibility(View.GONE);
                                    }

                                }
                            }
                        });

                        text_color.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                hideSoftKeyboard(text_edit);
                                add_text_card.setVisibility(View.GONE);
                                text_style_layout.setVisibility(View.GONE);
                                text_size_layout.setVisibility(View.GONE);
                                text_blur_layout.setVisibility(View.GONE);
                                text_pattern_layout.setVisibility(View.GONE);

                                if (text_sticker.getText().length() != 0) {

                                    if (text_color_layout.getVisibility() == View.GONE) {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                        text_color_layout.startAnimation(animation);
                                        text_color_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                        text_color_layout.startAnimation(animation);
                                        text_color_layout.setVisibility(View.GONE);
                                    }

                                    ColorAdapter adapter4 = new ColorAdapter(activity);
                                    color_grid.setAdapter(adapter4);

                                }
                            }
                        });

                        color_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                text_sticker.getPaint().setShader(null);

                                text_sticker.setTextColor(Color.parseColor("#" + color_array[position]));
                            }
                        });

                        color_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                text_color_layout.startAnimation(animation);
                                text_color_layout.setVisibility(View.VISIBLE);

                            }
                        });

                        size_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                text_size_layout.startAnimation(animation);
                                text_size_layout.setVisibility(View.GONE);

                            }
                        });

                        text_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                text_sticker.setTextSize(progress);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });

                        text_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (text_edit.getText().length() == 0) {

                                } else {

                                    hideSoftKeyboard(text_edit);

                                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                    add_text_card.startAnimation(animation);
                                    add_text_card.setVisibility(View.GONE);

                                    String set = text_edit.getText().toString();
                                    text_sticker.setText(set);

                                }

                            }
                        });

                        text_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (add_text_card.getVisibility() == View.GONE) {
                                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_top);
                                    add_text_card.startAnimation(animation);
                                    add_text_card.setVisibility(View.VISIBLE);
                                } else {
                                    hideSoftKeyboard(text_edit);

                                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move_bottom);
                                    add_text_card.startAnimation(animation);
                                    add_text_card.setVisibility(View.GONE);
                                }
                            }
                        });


//                TODO Text Dialog Is Finish

                        break;

                    case 4:
                        if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            activity.startActivityForResult(Intent.createChooser(intent,"complete Action Using.."),2);
                        } else {
                            // Ad was loaded, show it!
                            interstitialAd.show();
                        }
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
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                activity.startActivityForResult(Intent.createChooser(intent,"complete Action Using.."),2);
                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {
                                super.onLoggingImpression(ad);
                            }
                        });
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        activity.startActivityForResult(Intent.createChooser(intent,"complete Action Using.."),2);
                        break;

                    case 5:
                        Visible(textsticker);
                        Gone(color_back, color_fore, white_back, white_fore,smoke12);
                        break;

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return maintextlist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon1;
        private TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon1 = itemView.findViewById(R.id.icon1);
            text1 = itemView.findViewById(R.id.text1);

        }
    }

    public void Gone(RecyclerView view1, RecyclerView view2, RecyclerView view3, RecyclerView view4,RecyclerView view5) {

        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        view5.setVisibility(View.GONE);

    }

    public void Visible(RecyclerView view) {
        view.setVisibility(View.VISIBLE);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    protected void applyBlurMaskFilter(TextView tv, BlurMaskFilter.Blur style){
        /*
            MaskFilter
                Known Direct Subclasses
                    BlurMaskFilter, EmbossMaskFilter

                MaskFilter is the base class for object that perform transformations on an
                alpha-channel mask before drawing it. A subclass of MaskFilter may be installed
                into a Paint. Blur and emboss are implemented as subclasses of MaskFilter.

        */
        /*
            BlurMaskFilter
                This takes a mask, and blurs its edge by the specified radius. Whether or or not to
                include the original mask, and whether the blur goes outside, inside, or straddles,
                the original mask's border, is controlled by the Blur enum.
        */
        /*
            public BlurMaskFilter (float radius, BlurMaskFilter.Blur style)
                Create a blur maskfilter.

            Parameters
                radius : The radius to extend the blur from the original mask. Must be > 0.
                style : The Blur to use
            Returns
                The new blur maskfilter
        */
        /*
            BlurMaskFilter.Blur
                INNER : Blur inside the border, draw nothing outside.
                NORMAL : Blur inside and outside the original border.
                OUTER : Draw nothing inside the border, blur outside.
                SOLID : Draw solid inside the border, blur outside.
        */
        /*
            public float getTextSize ()
                Returns the size (in pixels) of the default text size in this TextView.
        */

        // Define the blur effect radius
        float radius = tv.getTextSize()/10;

        // Initialize a new BlurMaskFilter instance
        BlurMaskFilter filter = new BlurMaskFilter(radius,style);

        /*
            public void setLayerType (int layerType, Paint paint)
                Specifies the type of layer backing this view. The layer can be LAYER_TYPE_NONE,
                LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE.

                A layer is associated with an optional Paint instance that controls how the
                layer is composed on screen.

            Parameters
                layerType : The type of layer to use with this view, must be one of
                    LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE
                paint : The paint used to compose the layer. This argument is optional and
                    can be null. It is ignored when the layer type is LAYER_TYPE_NONE
        */
        /*
            public static final int LAYER_TYPE_SOFTWARE
                Indicates that the view has a software layer. A software layer is backed by
                a bitmap and causes the view to be rendered using Android's software rendering
                pipeline, even if hardware acceleration is enabled.
        */

        // Set the TextView layer type
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        /*
            public MaskFilter setMaskFilter (MaskFilter maskfilter)
                Set or clear the maskfilter object.

                Pass null to clear any previous maskfilter. As a convenience, the parameter
                passed is also returned.

            Parameters
                maskfilter : May be null. The maskfilter to be installed in the paint
            Returns
                maskfilter
        */

        // Finally, apply the blur effect on TextView text
        tv.getPaint().setMaskFilter(filter);
    }

    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0)   // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(-2, -2);
            b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    private void setCurrentEdit(final StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);

//        if (seekBar.getVisibility() == View.INVISIBLE) {
//            seekBar.setVisibility(View.VISIBLE);
//        }

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                mCurrentView.setAlpha(i);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

    }


}
