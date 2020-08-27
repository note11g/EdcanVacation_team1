package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.edcan.shareformproject.databinding.ActivityGetPermissonBinding;

public class getPermissonActivity extends AppCompatActivity {


    static final int READ_SMS = 1;
    private ActivityGetPermissonBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_permisson);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_permisson);

        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if(permissonCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "SMS_READ check done", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, userMoreInfoInputActivity.class));
            finish();
        }else{
            Toast.makeText(this, "SMS 읽기 권한 없음", Toast.LENGTH_SHORT).show();


            binding.btnGetPermissonClose.setOnClickListener(view -> {
                startActivity(new Intent(this, userMoreInfoInputActivity.class));
                finish();
            });

            binding.btnGetPermisson.setOnClickListener(view -> {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)){
                    Toast.makeText(this, "전화번호를 자동으로 불러오기 위해, SMS 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_SMS }, READ_SMS);
                }
                else{
                    ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_SMS }, READ_SMS);
                }



            });
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults){
        switch(requestCode){
            case READ_SMS:{
                // If request is cancelled, the result arrays are empty.
                if(grantResults.length >0
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //권한 취득시
                    startActivity(new Intent(this, userMoreInfoInputActivity.class));
                    finish();
                }else{
                    //권한 미 취득시
                    Toast.makeText(this, "권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}