package com.edcan.shareformproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edcan.shareformproject.R;
import com.edcan.shareformproject.ViewPostActivity;
import com.edcan.shareformproject.WritePostActivity;
import com.edcan.shareformproject.adapter.ExploreAdapter;
import com.edcan.shareformproject.databinding.FragmentExploreBinding;
import com.edcan.shareformproject.databinding.RowExploreBinding;
import com.edcan.shareformproject.model.ExploreModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;


public class ExploreFragment extends Fragment {

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    private ObservableArrayList<ExploreModel> items1 = new ObservableArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Context mContext;
    private FragmentExploreBinding binding;
    private RowExploreBinding binding1;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        binding1 = DataBindingUtil.inflate(inflater, R.layout. row_explore, container, false);

        binding1.setImage("@drawable/netflix_logo");



        binding.recyclerExplore.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        );

        ExploreAdapter adapter = new ExploreAdapter();
        binding.recyclerExplore.setAdapter(adapter);

        adapter.setOnItemClickListener((view, item) -> {
            Intent intent = new Intent(mContext, ViewPostActivity.class);
            intent.putExtra("explore", item);
            startActivity(intent);
        });

        adapter.setOnItemLongClickListener((view, item) -> {
            return true;
        });

        binding.setItems1(items1);

        binding.febExplore.setOnClickListener(view -> startActivity(new Intent(mContext, WritePostActivity.class)));


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPosts();
    }

    private void getPosts(){

        items1.clear();
        firebaseFirestore
                .collection("post")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot d : queryDocumentSnapshots){
                        items1.add(d.toObject(ExploreModel.class));
                    }

                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa", Locale.ENGLISH);
                    Collections.sort(items1, (memoModel, t1) -> {
                        try {
                            return format.parse(t1.getTime()).compareTo(format.parse(memoModel.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    });
                }).addOnFailureListener(e-> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());

    }


}