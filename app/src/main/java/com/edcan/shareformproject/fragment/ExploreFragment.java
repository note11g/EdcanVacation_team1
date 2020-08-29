package com.edcan.shareformproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.edcan.shareformproject.R;
import com.edcan.shareformproject.databinding.FragmentExploreBinding;


public class ExploreFragment extends Fragment {

    public static ExploreFragment newInstance(){
        return new ExploreFragment();
    }

    private Context mContext;

    private FragmentExploreBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container,false);
        return binding.getRoot();
    }
}