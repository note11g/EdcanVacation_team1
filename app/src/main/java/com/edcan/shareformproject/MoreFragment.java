package com.edcan.shareformproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.edcan.shareformproject.databinding.FragmentHomeBinding;
import com.edcan.shareformproject.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MoreFragment extends Fragment {

    public static MoreFragment newInstance(){
        return new MoreFragment();
    }

    private Context mContext;
    private FirebaseAuth mAuth;
    private FragmentMoreBinding binding;
    Bitmap bitmap;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container,false);

        mAuth = FirebaseAuth.getInstance();


        binding.btnMoreLogout.setOnClickListener(view1 -> signOut());
        binding.btnMoreRevoke.setOnClickListener(view1 -> revokeAccess());
        return binding.getRoot();
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext, GoogleLoginActivity.class));
        getActivity().finish();
    }
    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
        Toast.makeText(mContext, "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext, GoogleLoginActivity.class));
        getActivity().finish();
    }
}