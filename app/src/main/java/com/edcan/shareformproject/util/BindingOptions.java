package com.edcan.shareformproject.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edcan.shareformproject.adapter.ChatAdapter;
import com.edcan.shareformproject.adapter.ExploreAdapter;
import com.edcan.shareformproject.adapter.NowShareAdapter;
import com.edcan.shareformproject.model.ChatModel;
import com.edcan.shareformproject.model.ExploreModel;
import com.edcan.shareformproject.model.NowShareModel;


public class BindingOptions {

    @BindingAdapter("imageLink")
    public static void setImageLink(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view.getContext())
                .load(link)
                .into(view);
    }

    @BindingAdapter("nowShareItem")
    public static void bindNowShareItem(RecyclerView recyclerView, ObservableArrayList<NowShareModel> items) {
        NowShareAdapter adapter = (NowShareAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

    @BindingAdapter("exploreItem")
    public static void bindExploreItem(RecyclerView recyclerView, ObservableArrayList<ExploreModel> items) {
        ExploreAdapter adapter = (ExploreAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

    @BindingAdapter("chatItem")
    public static void bindChatItem(RecyclerView recyclerView, ObservableArrayList<ChatModel> items) {
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

    @BindingAdapter("shortTime")
    public static void bindShortTime(TextView view, String time) {
        view.setText(TimeUtil.changeFormat(time, "yyyy/MM/dd HH:mm:ss", "HH:mm"));
    }

}
