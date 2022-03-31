package com.avinfo.smokename.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avinfo.smokename.Activity.EditingActivity;
import com.avinfo.smokename.R;
import com.avinfo.smokename.Utils.StickerView;

import static com.avinfo.smokename.Activity.EditingActivity.backsticker;
import static com.avinfo.smokename.Activity.EditingActivity.foresticker;
import static com.avinfo.smokename.Adapters.MainAdapter.mCurrentView;

public class TextstickerAdapter extends RecyclerView.Adapter<TextstickerAdapter.SmokeHolder> {

    public int[] cblist = new int[] {R.drawable.text_00001,R.drawable.text_00002,R.drawable.text_00003,R.drawable.text_00004,R.drawable.text_00005,R.drawable.text_00006,R.drawable.text_00007,R.drawable.text_00008,R.drawable.text_00009,R.drawable.text_00010
            ,R.drawable.text_00011,R.drawable.text_00012,R.drawable.text_00013,R.drawable.text_00014,R.drawable.text_00015,R.drawable.text_00016,R.drawable.text_00017,R.drawable.text_00018,R.drawable.text_00019,R.drawable.text_00020
            ,R.drawable.text_00021,R.drawable.text_00022,R.drawable.text_00023,R.drawable.text_00024,R.drawable.text_00025,R.drawable.text_00026,R.drawable.text_00027,R.drawable.text_00028,R.drawable.text_00029,R.drawable.text_00030
            ,R.drawable.text_00031,R.drawable.text_00032,R.drawable.text_00033,R.drawable.text_00034,R.drawable.text_00035,R.drawable.text_00036,R.drawable.text_00037,R.drawable.text_00038,R.drawable.text_00039,R.drawable.text_00040
            ,R.drawable.text_00041,R.drawable.text_00042,R.drawable.text_00043,R.drawable.text_00044,R.drawable.text_00045,R.drawable.text_00046,R.drawable.text_00047,R.drawable.text_00048,R.drawable.text_00049,R.drawable.text_00050
            ,R.drawable.text_00051,R.drawable.text_00052,R.drawable.text_00053,R.drawable.text_00054,R.drawable.text_00055,R.drawable.text_00056,R.drawable.text_00057,R.drawable.text_00058,R.drawable.text_00059,R.drawable.text_00060
            ,R.drawable.text_00061,R.drawable.text_00062,R.drawable.text_00063,R.drawable.text_00064,R.drawable.text_00065,R.drawable.text_00066,R.drawable.text_00067,R.drawable.text_00068,R.drawable.text_00069,R.drawable.text_00070};


    Activity activity;

    public TextstickerAdapter(EditingActivity editingActivity) {
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

        holder.smoke12it.setImageResource(cblist[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                EditingActivity.background.setImageResource(cblist[position]);
//                EditingActivity.foreground.setImageResource(cblist[position]);
                addStrickerView(cblist[position]);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cblist.length;
    }

    public class SmokeHolder extends RecyclerView.ViewHolder{

        private ImageView smoke12it;

        public SmokeHolder(@NonNull View itemView) {
            super(itemView);

            smoke12it = itemView.findViewById(R.id.smoke12it);

        }
    }

    private void addStrickerView(int sticker) {
        final StickerView stickerView = new StickerView(activity);
        stickerView.setImageResource(sticker);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        foresticker.addView(stickerView, layoutParams);
        setCurrentEdit(stickerView);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                foresticker.removeView(stickerView);
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
