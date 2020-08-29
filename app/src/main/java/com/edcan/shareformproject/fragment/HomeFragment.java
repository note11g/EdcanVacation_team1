package com.edcan.shareformproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edcan.shareformproject.R;
import com.edcan.shareformproject.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    private Context mContext;

    private FragmentHomeBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false);
        return binding.getRoot();
    }
}