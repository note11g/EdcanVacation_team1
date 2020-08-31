package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.edcan.shareformproject.databinding.ActivityViewPostBinding;

public class ViewPostActivity extends AppCompatActivity {


    private ActivityViewPostBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_post);

        binding.setContent(getIntent().getStringExtra("explore"));
        binding.setNick("제안자("+getIntent().getStringExtra("explore_nick")+"님)와 채팅하기");
        binding.setHead("인원 : "+getIntent().getStringExtra("explore_head"));
        binding.setPlatform("플랫폼 : "+getIntent().getStringExtra("explore_platform"));
        binding.setPeriod("기간 : "+getIntent().getStringExtra("explore_period"));

        String goToUid = getIntent().getStringExtra("explore_uid");

        Toast.makeText(this, "uid : "+goToUid, Toast.LENGTH_SHORT).show();


    }
}