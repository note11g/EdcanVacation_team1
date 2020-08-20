package com.example.edcan_project_shareform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.edcan_project_shareform.databinding.FragmentFourthBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class FourthFragment extends Fragment {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private Context mContext;
    private FragmentFourthBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static Fragment newInstance() {
        return new FourthFragment();
    }

    // TODO: Rename and change types of parameters

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container,false);

        binding.buttonFourthLogout.setOnClickListener(v -> Logout());

        return binding.getRoot();
    }

    private void Logout(){
        UserCache.clear(mContext);
        firebaseAuth.signOut();
        startActivity(new Intent(mContext, LoginActivity.class));
        if(getActivity()!=null)
            getActivity().finish();
    }
}