package com.edcan.shareformproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edcan.shareformproject.databinding.RowNowShareBinding;
import com.edcan.shareformproject.databinding.RowNowShareBinding;
import com.edcan.shareformproject.model.NowShareModel;
import com.edcan.shareformproject.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class NowShareAdapter extends RecyclerView.Adapter<NowShareAdapter.NowShareHolder> {

    private List<NowShareModel> list;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, NowShareModel item);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, NowShareModel item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public NowShareAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public NowShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NowShareHolder(RowNowShareBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NowShareHolder holder, int position) {
        NowShareModel item = list.get(position);
        holder.bind(item, mOnItemClickListener, mOnItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItem(List<NowShareModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    static class NowShareHolder extends RecyclerView.ViewHolder {

        private RowNowShareBinding binding;

        NowShareHolder(RowNowShareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(NowShareModel item, OnItemClickListener listener, OnItemLongClickListener longListener) {
            binding.setItem(item);
            itemView.setOnClickListener(view -> listener.onItemClick(view, item));
            itemView.setOnLongClickListener(view -> longListener.onItemLongClick(view, item));
        }
    }
}

