package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edcan.shareformproject.databinding.ActivityEditUserInfoBinding;
import com.edcan.shareformproject.databinding.ActivityUserMoreInfoInputBinding;
import com.edcan.shareformproject.model.UserModel;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class EditUserInfoActivity extends AppCompatActivity {

    private ActivityEditUserInfoBinding binding;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_info);

        String nick = UserCache.getUser(this).getNick();
        String phone = UserCache.getUser(this).getPhone();
        String bank = UserCache.getUser(this).getBank();
        String acc = UserCache.getUser(this).getAcc();

        binding.setNick(nick);
        binding.setPhoneNum(phone);
        binding.setBankName(bank);
        binding.setBankAcc(acc);
        binding.setBankAccCheck("");

        boolean[] isRan = {false};

        //스피너
        spinner = findViewById(R.id.selc_bank_edtUserInfo);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isRan[0]){
                    isRan[0] =true;
                    if (bank.equals("IBK기업은행"))
                        adapterView.setSelection(0);
                    else if (bank.equals("KDB산업은행"))
                        adapterView.setSelection(1);
                    else if (bank.equals("농협"))
                        adapterView.setSelection(2);
                    else if (bank.equals("수협"))
                        adapterView.setSelection(3);
                    else if (bank.equals("KB국민은행"))
                        adapterView.setSelection(4);
                    else if (bank.equals("우리은행"))
                        adapterView.setSelection(5);
                    else if (bank.equals("신한은행"))
                        adapterView.setSelection(6);
                    else if (bank.equals("하나은행"))
                        adapterView.setSelection(7);
                    else if (bank.equals("SC제일은행"))
                        adapterView.setSelection(8);
                    else if (bank.equals("케이뱅크"))
                        adapterView.setSelection(9);
                    else if (bank.equals("카카오뱅크"))
                        adapterView.setSelection(10);
                }
                binding.setBankName(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });//spinner end


        binding.btnEdtEdtUserInfo.setOnClickListener(view -> {
            click(binding.getNick(), binding.getPhoneNum(), binding.getBankName(), binding.getBankAcc(), binding.getBankAccCheck(), UserCache.getUser(this).getUid());
        });

    }




    private void click(String nick, String phoneNum, String bankName, String bankAcc, String bankAccCheck, String uid){

        if (nick.isEmpty() || phoneNum.isEmpty() || bankName.isEmpty() || bankAcc.isEmpty() || bankAccCheck.isEmpty()) {
            Toast.makeText(this, "입력하지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bankAcc.equals(bankAccCheck)) {
            Toast.makeText(this, "계좌번호와 계좌번호 확인이 서로 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNum.contains("-"))
            phoneNum = phoneNum.replace("-", "");
        String finalPhoneNum = phoneNum;
        String profile = UserCache.getUser(this).getProfile();

        firebaseFirestore
                .collection("userInfo").document(uid)
                .update(
                        "nick", nick,
                        "phone", finalPhoneNum,
                        "bank",bankName,
                        "acc",bankAcc
                ).addOnSuccessListener(runnable -> {
                    UserCache.clear(this);
                    UserCache.setUser(this, new UserModel(nick, finalPhoneNum, bankName, bankAcc, uid, profile));
                    Toast.makeText(this, "변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
        }).addOnFailureListener(e -> Toast.makeText(this, "변경되지 않았습니다\ne:"+e, Toast.LENGTH_LONG).show());

    }

}