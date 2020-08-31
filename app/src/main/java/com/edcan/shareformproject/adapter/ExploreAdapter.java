package com.edcan.shareformproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edcan.shareformproject.databinding.RowExploreBinding;
import com.edcan.shareformproject.model.ExploreModel;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreHolder> {

    private List<ExploreModel> list = null;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, ExploreModel item);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, ExploreModel item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public ExploreAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExploreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExploreHolder(RowExploreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreHolder holder, int position) {
        ExploreModel item = list.get(position);
        holder.bind(item, mOnItemClickListener, mOnItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItem(List<ExploreModel> items1) {
        this.list = items1;
        notifyDataSetChanged();
    }

    static class ExploreHolder extends RecyclerView.ViewHolder {

        private RowExploreBinding binding;

        ExploreHolder(RowExploreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ExploreModel item, OnItemClickListener listener, OnItemLongClickListener longListener) {
            binding.setItem(item);
            itemView.setOnClickListener(view -> listener.onItemClick(view, item));
            itemView.setOnLongClickListener(view -> longListener.onItemLongClick(view, item));
        }
    }
}

