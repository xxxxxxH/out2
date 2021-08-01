package net.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.basicmodel.R;
import net.entity.VideoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import net.interFace.OnItemClickListener;
import net.utils.Contanst;
import net.utils.Utils;
import net.utils.ScreenUtils;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<VideoEntity> data;
    private Context context;
    private Activity activity;
    private int type;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ArrayList<VideoEntity> getData() {
        return data;
    }

    public VideoAdapter(ArrayList<VideoEntity> data, Context context, Activity activity, int type) {
        this.data = data;
        this.context = context;
        this.activity = activity;
        this.type = type;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_video, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        VideoEntity entity = data.get(position);
        switch (type) {
            case Contanst.TYPE_VIDEOS:
                holder.top.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
                params.height = ScreenUtils.getScreenSize(activity)[0] / 3;
                holder.imageView.setLayoutParams(params);
                Glide.with(context).load(entity.getUrl()).into(holder.imageView);
                holder.name.setText(TextUtils.isEmpty(entity.getName()) ? "" : entity.getName());
                holder.duration.setText(TextUtils.isEmpty(entity.getDuration()) ? "" : entity.getDuration());
                holder.size.setText(TextUtils.isEmpty(entity.getSize()) ? "" : entity.getSize());
                holder.date.setText(TextUtils.isEmpty(entity.getTime()) ? "" : entity.getTime());
                break;
            case Contanst.TYPE_FOLDERS:
                holder.top.setVisibility(View.GONE);
                ///storage/emulated/0/DCIM/Camera/VID_20191218_220825.mp4
                break;
            case Contanst.TYPE_HISTORY:

                break;
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    int position = holder.getLayoutPosition();
                    listener.onItemClick(view,position,type);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout root;
        RelativeLayout top;
        LinearLayout details;
        ImageView imageView;
        TextView duration;
        TextView name;
        TextView size;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.video_item_root);
            top = itemView.findViewById(R.id.video_item_top);
            details = itemView.findViewById(R.id.video_item_details);
            imageView = itemView.findViewById(R.id.video_item_img);
            duration = itemView.findViewById(R.id.video_item_duration);
            name = itemView.findViewById(R.id.video_item_name);
            size = itemView.findViewById(R.id.video_item_size);
            date = itemView.findViewById(R.id.video_item_time);
        }
    }
}
