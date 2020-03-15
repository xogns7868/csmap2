package com.example.administrator.csmap2.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
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

public class re6 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

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
    private ScrollView scrollView;
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
        setContentView(R.layout.activity_layout4);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);
        user = mAuth.getCurrentUser();
        scrollView = (ScrollView)findViewById(R.id.mainscroll);
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
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.locate_icon),"서울특별시 성북구 정릉동 894-11");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.phone_icon),"02-6204-1033");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 10:30 - 22:00 연중무휴");
        adapter2.addItem("베이직 돈카츠");
        adapter2.addItem("히레카츠");
        adapter2.addItem("후추 돈카츠");
        adapter2.addItem("고추 돈카츠");
        adapter2.addItem("마늘 돈카츠");
        adapter2.addItem("고구마 돈카츠");
        adapter2.addItem("치즈 돈카츠");
        adapter2.addItem("감자치즈 돈카츠");
        adapter2.addItem("카츠카레");
        adapter2.addItem("철판 치즈 돈카츠");
        adapter2.addItem("돈카츠우동");
        adapter2.addItem("냉모밀");
        adapter2.addItem("카츠동(돈부리)");
        adapter2.addItem("가리아게동");
        adapter2.addItem("카츠 에비동");
        adapter2.addItem("얼큰돈카츠나베");
        adapter4.addItem("                8000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                8500원 ");
        adapter4.addItem("               12500원 ");
        adapter4.addItem("                8500원 ");
        adapter4.addItem("                8000원 ");
        adapter4.addItem("                7000원 ");
        adapter4.addItem("                7000원 ");
        adapter4.addItem("                9000원 ");
        adapter4.addItem("                8000원 ");
        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(re6.this);
                dialog.setContentView(R.layout.activity_fullimage);
                ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                switch(view.getId()){
                    case R.id.image1 :
                        Glide.with(re6.this).load(R.drawable.cut1).into(iv);
                        break;
                    case R.id.image2:
                        Glide.with(re6.this).load(R.drawable.cut2).into(iv);
                        break;
                    case R.id.image3:
                        Glide.with(re6.this).load(R.drawable.cut3).into(iv);
                        break;
                    case R.id.image4:
                        Glide.with(re6.this).load(R.drawable.cut4).into(iv);
                        break;
                }
                dialog.show();
            }
        };

        chat_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(chat_edit.getText().toString().equals(""))
                    return;
                ChatDTO chat = new ChatDTO(CHAT_NAME, chat_edit.getText().toString());
                databaseReference.child("도꼬돈카츠").push().setValue(chat);
                chat_edit.setText("");
            }
        });
        ImageButton button1 = (ImageButton)findViewById(R.id.image1);
        Glide.with(this).load(R.drawable.cut1).into(button1);
        button1.setOnClickListener(onClickListener);
        ImageButton button2 = (ImageButton)findViewById(R.id.image2);
        Glide.with(this).load(R.drawable.cut2).into(button2);
        button2.setOnClickListener(onClickListener);
        ImageButton button3 = (ImageButton)findViewById(R.id.image3);
        Glide.with(this).load(R.drawable.cut3).into(button3);
        button3.setOnClickListener(onClickListener);
        ImageButton button4 = (ImageButton)findViewById(R.id.image4);
        Glide.with(this).load(R.drawable.cut4).into(button4);
        button4.setOnClickListener(onClickListener);
        databaseReference.child("도꼬돈카츠").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatDTO chatData = dataSnapshot.getValue(ChatDTO.class);  // chatData를 가져오고
                String inputValue = ("익명" + "\n " + chatData.getMessage());
                refresh(inputValue);
                chat_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            scrollView.requestDisallowInterceptTouchEvent(false);
                        }else{
                            scrollView.requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });
            }
            private void refresh( String $inputValue ) {
                adapter3.addItem( $inputValue ); ;
                adapter3.notifyDataSetChanged() ;
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