package com.example.administrator.csmap2.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.csmap2.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class layout5 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignActivity";
    Button signinbutton;
    String CHAT_NAME = "";
    String USER_NAME = "";
    FirebaseUser user;
    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;
    ListView mListView,mListView2,mListView3;
    ListViewAdapter adapter,adapter2,adapter3,adapter4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout5);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        signinbutton = (Button) findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);
        user = mAuth.getCurrentUser();
        adapter = new ListViewAdapter();
        adapter2 = new ListViewAdapter();
        adapter3 = new ListViewAdapter();
        adapter4 = new ListViewAdapter();
        mListView = (ListView) findViewById(R.id.detail_locate);
        mListView2 = (ListView) findViewById(R.id.detail_menu);
        mListView3 = (ListView) findViewById(R.id.detail_price);
        mListView.setAdapter(adapter);
        mListView2.setAdapter(adapter2);
        mListView3.setAdapter(adapter4);
        chat_view.setAdapter(adapter3);
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.locate_icon),"서울특별시 성북구 정릉동 891-1");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.phone_icon),"02-913-9702");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 09:00 - 21:00 연중무휴");
        adapter2.addItem("돈까스 카레");
        adapter2.addItem("프리미엄 스페셜 카레");
        adapter2.addItem("돈까스 카레 우동");
        adapter2.addItem("스페셜 카레");
        adapter2.addItem("버섯 카레");
        adapter2.addItem("버섯 카레 우동");
        adapter4.addItem("                 6500원 ");
        adapter4.addItem("                 10000원");
        adapter4.addItem("                 7500원 ");
        adapter4.addItem("                 9500원 ");
        adapter4.addItem("                 6000원 ");
        adapter4.addItem("                 6500원 ");
        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(layout5.this);
                dialog.setContentView(R.layout.activity_fullimage);
                ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                switch(view.getId()){
                    case R.id.image1 :
                        iv.setImageResource(R.drawable.bukak_main);
                        break;
                    case R.id.image2:
                        iv.setImageResource(R.drawable.bukak2);
                        break;
                    case R.id.image3:
                        iv.setImageResource(R.drawable.bukak3);
                        break;
                    case R.id.image4:
                        iv.setImageResource(R.drawable.bukak4);
                        break;
                }
                dialog.show();
            }
        };
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                if(user!=null){
                    CHAT_NAME = user.getDisplayName();
                    USER_NAME = user.getEmail();
                }
            }
        });

        chat_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString());
                databaseReference.child("북악루").push().setValue(chat);
                chat_edit.setText("");
            }
        });
        ImageButton button1 = (ImageButton)findViewById(R.id.image1);
        button1.setOnClickListener(onClickListener);
        ImageButton button2 = (ImageButton)findViewById(R.id.image2);
        button2.setOnClickListener(onClickListener);
        ImageButton button3 = (ImageButton)findViewById(R.id.image3);
        button3.setOnClickListener(onClickListener);
        ImageButton button4 = (ImageButton)findViewById(R.id.image4);
        button4.setOnClickListener(onClickListener);
        databaseReference.child("북악루").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatDTO chatData = dataSnapshot.getValue(ChatDTO.class);  // chatData를 가져오고
                adapter3.addItem(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectFailed" + connectionResult);
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }
}