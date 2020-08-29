package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.edcan.shareformproject.databinding.ActivityBankAccBinding;
import com.edcan.shareformproject.util.UserCache;

public class BankAccActivity extends AppCompatActivity {

    private ActivityBankAccBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_acc);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_acc);

        binding.setBankName(UserCache.getUser(this).getBank());
        binding.setBankAcc(UserCache.getUser(this).getAcc());

    }
}