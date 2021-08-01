package net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.basicmodel.R;
import net.entity.FolderEntity;
import net.interFace.OnItemClickListener;
import net.utils.Contanst;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    ArrayList<FolderEntity> data;
    Context context;
    OnItemClickListener listener;

    public FolderAdapter(ArrayList<FolderEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_folder, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.folderName.setText(data.get(position).getName());
        holder.videoNums.setText(String.valueOf(data.get(position).getNum()) + "videos");
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(view, pos, Contanst.TYPE_FOLDERS);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<FolderEntity> getData() {
        return data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout root;
        TextView folderName;
        TextView videoNums;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.folder_root);
            folderName = itemView.findViewById(R.id.folder_name);
            videoNums = itemView.findViewById(R.id.video_num);
        }
    }
}
