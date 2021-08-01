package net.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.adapter.VideoAdapter;
import net.basicmodel.MainActivity;
import net.basicmodel.R;
import net.basicmodel.VideoActivity;
import net.entity.VideoEntity;
import net.interFace.OnItemClickListener;
import net.utils.Contanst;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements OnItemClickListener {

    private ArrayList<VideoEntity> data = null;
    private ProgressBar progressBar;
    private RecyclerView recycler;
    private TextView emptyView;
    private VideoAdapter adapter = null;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View root) {
        progressBar = root.findViewById(R.id.progressBar);
        recycler = root.findViewById(R.id.recycler);
        emptyView = root.findViewById(R.id.emptyView);
    }

    private void initData() {
        data = Contanst.historys;
        progressBar.setVisibility(View.GONE);
        if (data.size() > 0) {
            emptyView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            adapter = new VideoAdapter(data, getActivity(), getActivity(), Contanst.TYPE_VIDEOS);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recycler.setLayoutManager(manager);
            recycler.setAdapter(adapter);
            adapter.setListener(this);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(@NotNull View view, int position, int type) {
        VideoEntity entity = adapter.getData().get(position);
        String url = entity.getUrl();
        String name = entity.getName();
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        getActivity().startActivity(intent);
    }
}
