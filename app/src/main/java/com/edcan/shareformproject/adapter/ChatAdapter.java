package com.edcan.shareformproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.edcan.shareformproject.databinding.RowChatReceiveBinding;
import com.edcan.shareformproject.databinding.RowChatSendBinding;
import com.edcan.shareformproject.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private String user;
    private List<ChatModel> list;

    public ChatAdapter(String user) {
        this.list = new ArrayList<>();
        this.user = user;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(user)) return 0;
        else return 1;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new ChatHolder(RowChatSendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else
            return new ChatHolder(RowChatReceiveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatModel item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItem(List<ChatModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    static class ChatHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ChatHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatModel item) {
            if (binding instanceof RowChatSendBinding) ((RowChatSendBinding) binding).setItem(item);
            else ((RowChatReceiveBinding) binding).setItem(item);
        }
    }
}

