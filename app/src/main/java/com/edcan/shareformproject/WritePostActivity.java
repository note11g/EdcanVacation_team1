package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edcan.shareformproject.databinding.ActivityViewPostBinding;
import com.edcan.shareformproject.databinding.ActivityWritePostBinding;
import com.edcan.shareformproject.model.ExploreModel;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WritePostActivity extends AppCompatActivity {

    private ActivityWritePostBinding binding;
    private Spinner spinner1, spinner2, spinner3;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_post);
        binding.setContent("");

        spinner1 = findViewById(R.id.selc_group_view_post);
        spinner2 = findViewById(R.id.selc_period_view_post);
        spinner3 = findViewById(R.id.selc_head_view_post);

        final String[] s1 = new String[1];
        final String[] s2 = new String[1];
        final String[] s3 = new String[1];

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s1[0] = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2[0] = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s3[0] = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        binding.btnNewPostUpload.setOnClickListener(view -> {
           upload(binding.getContent(), s1[0], s2[0], s3[0]);
        });
    }

    private void upload(String content, String platform, String period, String head) {
        if (content.isEmpty()) {
            Toast.makeText(this, "빈칸을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = UserCache.getUser(this).getUid();
        String nick = UserCache.getUser(this).getNick();

        ExploreModel model = new ExploreModel();
        model.setUid(uid);
        model.setNick(nick);
        model.setPlatform(platform);
        model.setPeriod(period);
        model.setHead(head);
        model.setContent(content);
        model.setTime(getTime());

        firebaseFirestore
                .collection("post")
                .document(getTime2())
                .set(model)
                .addOnSuccessListener(runnable -> {
                    Toast.makeText(this, "정상적으로 등록되었습니다!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());

    }
    private String getTime() {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa", Locale.ENGLISH).format(new Date());
    }
    private String getTime2() {
        return new SimpleDateFormat("yyyyMMdd hhmmss aa", Locale.ENGLISH).format(new Date());
    }
}