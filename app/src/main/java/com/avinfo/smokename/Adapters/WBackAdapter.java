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

public class WBackAdapter extends RecyclerView.Adapter<WBackAdapter.CBackHolder> {

    public int[] cblist = new int[] {R.drawable.normalsmoke_00001,R.drawable.normalsmoke_00002,R.drawable.normalsmoke_00003,R.drawable.normalsmoke_00004,R.drawable.normalsmoke_00005,R.drawable.normalsmoke_00006,R.drawable.normalsmoke_00007,R.drawable.normalsmoke_00008,R.drawable.normalsmoke_00009,R.drawable.normalsmoke_00010
            ,R.drawable.normalsmoke_00011,R.drawable.normalsmoke_00012,R.drawable.normalsmoke_00013,R.drawable.normalsmoke_00014,R.drawable.normalsmoke_00015,R.drawable.normalsmoke_00016,R.drawable.normalsmoke_00017,R.drawable.normalsmoke_00018,R.drawable.normalsmoke_00019,R.drawable.normalsmoke_00020
            ,R.drawable.normalsmoke_00021,R.drawable.normalsmoke_00022,R.drawable.normalsmoke_00023,R.drawable.normalsmoke_00024,R.drawable.normalsmoke_00025,R.drawable.normalsmoke_00026,R.drawable.normalsmoke_00027,R.drawable.normalsmoke_00028,R.drawable.normalsmoke_00029,R.drawable.normalsmoke_00030
            ,R.drawable.normalsmoke_00031,R.drawable.normalsmoke_00032,R.drawable.normalsmoke_00033,R.drawable.normalsmoke_00034,R.drawable.normalsmoke_00035,R.drawable.normalsmoke_00036,R.drawable.normalsmoke_00037,R.drawable.normalsmoke_00038,R.drawable.normalsmoke_00039,R.drawable.normalsmoke_00040
            ,R.drawable.normalsmoke_00041,R.drawable.normalsmoke_00042,R.drawable.normalsmoke_00043,R.drawable.normalsmoke_00044,R.drawable.normalsmoke_00045,R.drawable.normalsmoke_00046,R.drawable.normalsmoke_00047,R.drawable.normalsmoke_00048,R.drawable.normalsmoke_00049,R.drawable.normalsmoke_00050
            ,R.drawable.normalsmoke_00051,R.drawable.normalsmoke_00052,R.drawable.normalsmoke_00053,R.drawable.normalsmoke_00054,R.drawable.normalsmoke_00055,R.drawable.normalsmoke_00056,R.drawable.normalsmoke_00057,R.drawable.normalsmoke_00058,R.drawable.normalsmoke_00059,R.drawable.normalsmoke_00060
            ,R.drawable.normalsmoke_00061,R.drawable.normalsmoke_00062,R.drawable.normalsmoke_00063,R.drawable.normalsmoke_00064,R.drawable.normalsmoke_00065,R.drawable.normalsmoke_00066,R.drawable.normalsmoke_00067,R.drawable.normalsmoke_00068,R.drawable.normalsmoke_00069,R.drawable.normalsmoke_00070};

    private Activity activity;

    public WBackAdapter(EditingActivity editingActivity) {
        activity = editingActivity;
    }

    @NonNull
    @Override
    public CBackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smoke12item, parent, false);
        return new CBackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CBackHolder holder, final int position) {

        holder.smoke12it.setImageResource(cblist[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditingActivity.background.setImageResource(0);
                addStrickerView(cblist[position]);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cblist.length;
    }

    public class CBackHolder extends RecyclerView.ViewHolder{

        private ImageView smoke12it;

        public CBackHolder(@NonNull View itemView) {
            super(itemView);

            smoke12it = itemView.findViewById(R.id.smoke12it);

        }

    }

    private void addStrickerView(int sticker) {
        final StickerView stickerView = new StickerView(activity);
        stickerView.setImageResource(sticker);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        backsticker.addView(stickerView, layoutParams);
        setCurrentEdit(stickerView);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() { backsticker.removeView(stickerView);
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
