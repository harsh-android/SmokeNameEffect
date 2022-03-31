package com.avinfo.smokename.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avinfo.smokename.Activity.EditingActivity;
import com.avinfo.smokename.R;

public class Smoke12Adapter extends RecyclerView.Adapter<Smoke12Adapter.SmokeHolder> {

    public int[] d_smoke1 = new int[] {0,R.drawable.background_00001,R.drawable.background_00002,R.drawable.background_00003,R.drawable.background_00004,R.drawable.background_00005,R.drawable.background_00006,R.drawable.background_00007,R.drawable.background_00008,R.drawable.background_00009,R.drawable.background_00010
            ,R.drawable.background_00011,R.drawable.background_00012,R.drawable.background_00013,R.drawable.background_00014,R.drawable.background_00015,R.drawable.background_00016,R.drawable.background_00017,R.drawable.background_00018,R.drawable.background_00019,R.drawable.background_00020
            ,R.drawable.background_00021,R.drawable.background_00022,R.drawable.background_00023,R.drawable.background_00024,R.drawable.background_00025,R.drawable.background_00026,R.drawable.background_00027,R.drawable.background_00028,R.drawable.background_00029,R.drawable.background_00030
            ,R.drawable.background_00031,R.drawable.background_00032,R.drawable.background_00033,R.drawable.background_00034,R.drawable.background_00035,R.drawable.background_00036,R.drawable.background_00037,R.drawable.background_00038,R.drawable.background_00039};

    public int[] d_smoke2 = new int[] {0,R.drawable.over_background_00001,R.drawable.over_background_00002,R.drawable.over_background_00003,R.drawable.over_background_00004,R.drawable.over_background_00005,R.drawable.over_background_00006,R.drawable.over_background_00007,R.drawable.over_background_00008,R.drawable.over_background_00009,R.drawable.over_background_00010
            ,R.drawable.over_background_00011,R.drawable.over_background_00012,R.drawable.over_background_00013,R.drawable.over_background_00014,R.drawable.over_background_00015,R.drawable.over_background_00016,R.drawable.over_background_00017,R.drawable.over_background_00018,R.drawable.over_background_00019,R.drawable.over_background_00020
            ,R.drawable.over_background_00021,R.drawable.over_background_00022,R.drawable.over_background_00023,R.drawable.over_background_00024,R.drawable.over_background_00025,R.drawable.over_background_00026,R.drawable.over_background_00027,R.drawable.over_background_00028,R.drawable.over_background_00029,R.drawable.over_background_00030
            ,R.drawable.over_background_00031,R.drawable.over_background_00032,R.drawable.over_background_00033,R.drawable.over_background_00034,R.drawable.over_background_00035,R.drawable.over_background_00036,R.drawable.over_background_00037,R.drawable.over_background_00038,R.drawable.over_background_00039};

    Activity activity;

    public Smoke12Adapter(EditingActivity editingActivity) {
        activity = editingActivity;
    }

    @NonNull
    @Override
    public SmokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smoke12item, parent, false);
        return new SmokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmokeHolder holder, final int position) {

        holder.smoke12it.setImageResource(d_smoke1[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditingActivity.background.setImageResource(d_smoke1[position]);
                EditingActivity.foreground.setImageResource(d_smoke2[position]);

            }
        });

    }

    @Override
    public int getItemCount() {
        return d_smoke1.length;
    }

    public class SmokeHolder extends RecyclerView.ViewHolder{

        private ImageView smoke12it;

        public SmokeHolder(@NonNull View itemView) {
            super(itemView);

            smoke12it = itemView.findViewById(R.id.smoke12it);

        }
    }

}
