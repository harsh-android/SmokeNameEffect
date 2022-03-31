package com.avinfo.smokename.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.avinfo.smokename.Activity.EditingActivity;
import com.avinfo.smokename.R;

public class PatternAdapter extends BaseAdapter {

    public static int[] pattern_array = new int[] {R.drawable.pattern_plain,R.drawable.pattern1,R.drawable.pattern2,R.drawable.pattern3,R.drawable.pattern4,R.drawable.pattern5,R.drawable.pattern6,R.drawable.pattern7,R.drawable.pattern8,R.drawable.pattern9,R.drawable.pattern10
            ,R.drawable.pattern11,R.drawable.pattern12,R.drawable.pattern13,R.drawable.pattern14,R.drawable.pattern15,R.drawable.pattern16,R.drawable.pattern17,R.drawable.pattern18,R.drawable.pattern19,R.drawable.pattern20
            ,R.drawable.pattern21,R.drawable.pattern22,R.drawable.pattern23,R.drawable.pattern24,R.drawable.pattern25,R.drawable.pattern26,R.drawable.pattern27,R.drawable.pattern28,R.drawable.pattern29,R.drawable.pattern30
            ,R.drawable.pattern31,R.drawable.pattern32,R.drawable.pattern33,R.drawable.pattern34,R.drawable.pattern35,R.drawable.pattern36,R.drawable.pattern37,R.drawable.pattern38,R.drawable.pattern39,R.drawable.pattern40
            ,R.drawable.pattern41,R.drawable.pattern42,R.drawable.pattern43,R.drawable.pattern44,R.drawable.pattern45,R.drawable.pattern46,R.drawable.pattern47,R.drawable.pattern48,R.drawable.pattern49,R.drawable.pattern50
            ,R.drawable.pattern51,R.drawable.pattern52};


    Activity activity;
    LayoutInflater inflater;

    public PatternAdapter(Activity editingActivity) {

        activity = editingActivity;
        inflater = LayoutInflater.from(editingActivity);

    }

    @Override
    public int getCount() {
        return pattern_array.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.sticker, parent, false);
            holder.pattern_image = view.findViewById(R.id.sticker);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.pattern_image.setImageResource(pattern_array[position]);
//        Glide.with(activity).load(EditingActivity.pattern_array[position]).into(holder.pattern_image);


        return view;
    }

    public class ViewHolder {
        ImageView pattern_image;
    }

}
