package com.edcan.shareformproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.edcan.shareformproject.databinding.FragmentHomeBinding;
import com.edcan.shareformproject.databinding.FragmentNowshareBinding;


public class NowShareFragment extends Fragment {

    public static NowShareFragment newInstance(){
        return new NowShareFragment();
    }

    private Context mContext;

    private FragmentNowshareBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nowshare, container,false);
        return binding.getRoot();
    }
}