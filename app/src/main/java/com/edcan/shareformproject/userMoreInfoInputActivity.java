package com.edcan.shareformproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.edcan.shareformproject.databinding.ActivityUserMoreInfoInputBinding;
import com.edcan.shareformproject.model.UserModel;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class userMoreInfoInputActivity extends AppCompatActivity {

    private ActivityUserMoreInfoInputBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more_info_input);

        if (user == null) {
            Toast.makeText(this, "user 정보가 없습니다. 로그인화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, GoogleLoginActivity.class));
        }


        String name = user.getDisplayName();
        String uid = user.getUid();
        String profile = user.getPhotoUrl() != null ? user.getPhotoUrl().toString()
                : getString(R.string.dummy_image_link);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_more_info_input);
        binding.setNick(name);
        binding.setPhoneNum(getPhoneNumber());
        binding.setBankAcc("");
        binding.setBankAccCheck("");
        binding.setUid(uid);

        //스피너 처리
        spinner = findViewById(R.id.selc_bank_moreInfoReg);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.setBankName(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.btnMoreInfoReg.setOnClickListener(view -> {
            register(binding.getNick(), binding.getPhoneNum(), binding.getBankName(), binding.getBankAcc(), binding.getBankAccCheck(), uid, profile);
        });

    }

    private void register(String nick, String phoneNum, String bankName, String bankAcc, String bankAccCheck, String uid, String profile) {

        if (nick.isEmpty() || phoneNum.isEmpty() || bankName.isEmpty() || bankAcc.isEmpty() || bankAccCheck.isEmpty()) {
            Toast.makeText(this, "입력하지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bankAcc.equals(bankAccCheck)) {
            Toast.makeText(this, "계좌번호와 계좌번호 확인이 서로 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNum.contains("-")) {
            phoneNum = phoneNum.replace("-", "");
        }

        String finalPhoneNum = phoneNum;
        firebaseFirestore
                .collection("userInfo")
                .document(uid)
                .set(new UserModel(nick, phoneNum, bankName, bankAcc, uid, profile))
                .addOnSuccessListener(runnable -> {
                    UserCache.setUser(this, new UserModel(nick, finalPhoneNum, bankName, bankAcc, uid, profile));
                    Toast.makeText(this, "축하드려요! 회원가입 되셨습니다:)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(this, "firestore input error : " + e, Toast.LENGTH_LONG).show());

    }

    @SuppressLint("MissingPermission")
    private String getPhoneNumber() {
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = "";
        try {
            if (telephony.getLine1Number() != null) {
                phoneNumber = telephony.getLine1Number();
            } else {
                if (telephony.getSimSerialNumber() != null) {
                    phoneNumber = telephony.getSimSerialNumber();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "전화번호를 자동으로 불러오지 못했습니다", Toast.LENGTH_SHORT).show();
        }

        if (phoneNumber.startsWith("+82"))
            phoneNumber = phoneNumber.replace("+82", "0"); // +8210xxxxyyyy 로 시작되는 번호

        phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());

        return phoneNumber;
    }


}