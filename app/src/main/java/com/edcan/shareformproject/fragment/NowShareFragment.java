package com.edcan.shareformproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edcan.shareformproject.ChatActivity;
import com.edcan.shareformproject.R;
import com.edcan.shareformproject.adapter.NowShareAdapter;
import com.edcan.shareformproject.databinding.FragmentNowshareBinding;
import com.edcan.shareformproject.model.ChatModel;
import com.edcan.shareformproject.model.NowShareModel;
import com.edcan.shareformproject.model.UserModel;
import com.edcan.shareformproject.util.LinearLayoutManagerWrapper;
import com.edcan.shareformproject.util.UserCache;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NowShareFragment extends Fragment {

    public static NowShareFragment newInstance() {
        return new NowShareFragment();
    }

    private ObservableArrayList<NowShareModel> items = new ObservableArrayList<>();

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private Context mContext;
    private FragmentNowshareBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nowshare, container, false);
        binding.setTestUid("");

        LinearLayoutManagerWrapper wrapper = new LinearLayoutManagerWrapper(
                mContext, LinearLayoutManager.VERTICAL, false);
        binding.recyclerChat.setLayoutManager(wrapper);

        NowShareAdapter adapter = new NowShareAdapter();
        binding.recyclerChat.setAdapter(adapter);
        binding.setItems(items);

        adapter.setOnItemClickListener((view, item) -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("now_share", item);
            startActivity(intent);
        });
        adapter.setOnItemLongClickListener((view, item) -> true);

        firebaseFirestore
                .collection("personal_chat")
                .whereArrayContains("member", UserCache.getUser(mContext).getUid())
                .addSnapshotListener((value, error) -> {
                    items.clear();
                    if (error != null) {
                        error.printStackTrace();
                        return;
                    }
                    for (DocumentSnapshot d : value) addChatFromSnapshot(d);
                });


        binding.button.setOnClickListener(view -> createChatRoom(binding.getTestUid()));


        return binding.getRoot();
    }


    private void addChatFromSnapshot(DocumentSnapshot d) {
        NowShareModel model = d.toObject(NowShareModel.class);
        if (model == null) return;

        model.setRoom(d.getId());

        List<ChatModel> chat = model.getMessage();

        //ternary goes brrr
        model.setText(!chat.isEmpty() ?
                (chat.get(chat.size() - 1).getFrom().equals(UserCache.getUser(mContext).getUid()) ? "회원님: " : "")
                        .concat(chat.get(chat.size() - 1).getText())
                : "아직 대화 내용이 없습니다.");

        model.getMember().remove(UserCache.getUser(mContext).getUid());
        String uid = model.getMember().get(0);

        firebaseFirestore
                .collection("userInfo")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> D = queryDocumentSnapshots.getDocuments();
                    if(D.size()==0)
                        return;
                    UserModel user = D.get(0).toObject(UserModel.class);

                    if (user == null) return;

                    model.setImage(user.getProfile());
                    model.setName(user.getNick());

                    items.add(model);
                });
    }

    //테스트용 코드, 참고해도 됨
    public void createChatRoom(String uid) {

        List<String> member = new ArrayList<>();
        member.add(uid);
        member.add(UserCache.getUser(mContext).getUid());

        firebaseFirestore
                .collection("personal_chat")
                .whereArrayContains("member", member)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    //이미 채팅방이 있으면 만들지 않음
                    if (!queryDocumentSnapshots.getDocuments().isEmpty()) return;

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("member", member);

                    List<HashMap<String, String>> message = new ArrayList<>();
                    map.put("message", message);

                    firebaseFirestore
                            .collection("personal_chat")
                            .document()
                            .set(map)
                            .addOnSuccessListener(runnable -> {
                                Toast.makeText(mContext, "생성 완료!", Toast.LENGTH_SHORT).show();
                            });
                });
    }
}

