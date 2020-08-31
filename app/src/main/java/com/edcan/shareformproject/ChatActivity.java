package com.edcan.shareformproject;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edcan.shareformproject.adapter.ChatAdapter;
import com.edcan.shareformproject.databinding.ActivityChatBinding;
import com.edcan.shareformproject.model.ChatModel;
import com.edcan.shareformproject.model.NowShareModel;
import com.edcan.shareformproject.util.LinearLayoutManagerWrapper;
import com.edcan.shareformproject.util.TimeUtil;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import gun0912.tedkeyboardobserver.BaseKeyboardObserver;
import gun0912.tedkeyboardobserver.TedKeyboardObserver;

public class ChatActivity extends AppCompatActivity {

    private ObservableArrayList<ChatModel> items = new ObservableArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private NowShareModel nowShareModel;

    private ActivityChatBinding binding;

    String roomId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setItems(items);
        binding.setActivity(this);
        binding.setText("");

        binding.toolbarChat.setNavigationOnClickListener(v -> finish());

        nowShareModel = (NowShareModel) getIntent().getSerializableExtra("now_share");
        if (nowShareModel == null) {
            roomId = myUidAndYourUid();
            NowShareModel nowShareModel1 = new NowShareModel();
            nowShareModel1.setName(getNick());
            Toast.makeText(this, "카카오톡, 문자 등의 메신저로의 유도는 사기일 수 있습니다.\n주의해주세요.", Toast.LENGTH_SHORT).show();
            binding.setNowShare(nowShareModel1);
        }else{
            roomId = nowShareModel.getRoom();
            binding.setNowShare(nowShareModel);
        }




        LinearLayoutManagerWrapper wrapper = new LinearLayoutManagerWrapper(
                this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(wrapper);

        ChatAdapter adapter = new ChatAdapter(UserCache.getUser(this).getUid());
        binding.recyclerView.setAdapter(adapter);

        //scroll to bottom when keyboard up
        if(items.size()!=0)
        new TedKeyboardObserver(this).listen((BaseKeyboardObserver.OnKeyboardListener) isShow ->
                scrollRecyclerToPosition(binding.recyclerView, items.size()-1));

        firebaseFirestore
                .collection("personal_chat")
                .document(roomId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                        return;
                    }
                    if (value == null) {return;}

                    items.clear();

                    NowShareModel model = value.toObject(NowShareModel.class);
                    if (model == null) return;
                    items.addAll(model.getMessage());
                    if(items.size()!=0)
                    scrollRecyclerToPosition(binding.recyclerView, items.size()-1);
                });
    }

    public void sendMessage(String msg) {
        if (msg.isEmpty()) return;

        binding.setText("");

        ChatModel model = new ChatModel();
        model.setFrom(UserCache.getUser(this).getUid());
        model.setText(msg);
        model.setTime(TimeUtil.getTimeByFormat("yyyy/MM/dd HH:mm:ss"));

        firebaseFirestore
                .collection("personal_chat")
                .document(roomId)
                .update("message", FieldValue.arrayUnion(ChatModel.toMap(model)));
    }

    @Nullable
    private void scrollRecyclerToPosition(RecyclerView rcv, int pos){
        rcv.smoothScrollToPosition(pos);
    }

    private String myUidAndYourUid(){
        return getIntent().getStringExtra("roomId");
    }
    private String getNick(){
        return getIntent().getStringExtra("nick");
    }
}
