package com.edcan.shareformproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.edcan.shareformproject.databinding.ActivityViewPostBinding;
import com.edcan.shareformproject.fragment.NowShareFragment;
import com.edcan.shareformproject.model.NowShareModel;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPostActivity extends AppCompatActivity {


    private ActivityViewPostBinding binding;
    private ObservableArrayList<NowShareModel> items = new ObservableArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private int i = 0;
    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_post);

        nick = getIntent().getStringExtra("explore_nick");

        binding.setContent(getIntent().getStringExtra("explore"));
        binding.setNick("제안자("+nick+"님)와 채팅하기");
        binding.setHead("인원 : "+getIntent().getStringExtra("explore_head"));
        binding.setPlatform("플랫폼 : "+getIntent().getStringExtra("explore_platform"));
        binding.setPeriod("기간 : "+getIntent().getStringExtra("explore_period"));

        String goToUid = getIntent().getStringExtra("explore_uid");

        binding.btnPostGotoChat.setOnClickListener(view -> createChatRoom(goToUid));

    }


    private void createChatRoom(String uid) {

        final String[] room1 = new String[1];
        final boolean[] isIt = {false};


        List<String> member = new ArrayList<>();
        member.add(uid);
        member.add(UserCache.getUser(this).getUid());



        firebaseFirestore
                .collection("personal_chat")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for(DocumentSnapshot d : queryDocumentSnapshots){
                        NowShareModel model = d.toObject(NowShareModel.class);
                        List<String> mem = model.getMember();

                        try {
                            if (mem.contains(uid) && mem.contains(UserCache.getUser(this).getUid())) {
                                isIt[0] = true;
                                break;
                            }
                        }catch(Exception e){

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("member", member);

                            List<HashMap<String, String>> message = new ArrayList<>();
                            map.put("message", message);

                            firebaseFirestore
                                    .collection("personal_chat")
                                    .document()
                                    .set(map)
                                    .addOnSuccessListener(runnable -> {
                                        createChatRoom(uid);
                                    });

                            isIt[0]=true;

                        }

                        i++;
                    }

                    //중복체크 끝!

                    //이미 채팅방이 있으면 만들지 않음/대신 채팅방으로 이동.
                    //중복체크가 안됨
                    if (isIt[0]){
                        room1[0] = queryDocumentSnapshots.getDocuments().get(i).getId();
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra("roomId", room1[0]);
                        intent.putExtra("nick",nick);
                        startActivity(intent);
                        return;
                    }
                else {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("member", member);

                        List<HashMap<String, String>> message = new ArrayList<>();
                        map.put("message", message);

                        firebaseFirestore
                                .collection("personal_chat")
                                .document()
                                .set(map)
                                .addOnSuccessListener(runnable -> {
                                    createChatRoom(uid);
                                });
                    }
                });
    }

}