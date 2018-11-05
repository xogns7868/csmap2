package com.example.administrator.csmap2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.csmap2.R;

public class layout4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout4);
        ListView mListView,mListView2;
        ListViewAdapter adapter,adapter2;
        adapter = new ListViewAdapter();
        adapter2 = new ListViewAdapter();
        mListView = (ListView) findViewById(R.id.detail_locate);
        mListView2 = (ListView) findViewById(R.id.detail_menu);
        mListView.setAdapter(adapter);
        mListView2.setAdapter(adapter2);
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.locate_icon),"서울특별시 성북구 정릉동 891-1");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.phone_icon),"02-909-1998");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 11:30 - 20:30연중무휴");
        adapter2.addItem("돈까스 카레                                            6500원");
        adapter2.addItem("프리미엄 스페셜 카레                                    10000원");
        adapter2.addItem("돈까스 카레 우동                                        7500원");
        adapter2.addItem("스페셜 카레                                             9500원");
        adapter2.addItem("버섯 카레                                               6000원");
        adapter2.addItem("버섯 카레 우동                                           6500원");
        adapter2.addItem("돈까스 카레                                              6500원");

        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(layout4.this);
                dialog.setContentView(R.layout.activity_fullimage);
                ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                switch(view.getId()){
                    case R.id.image1 :
                        iv.setImageResource(R.drawable.gossy);
                        break;
                        case R.id.image2:
                            iv.setImageResource(R.drawable.shirmp);
                            break;
                    case R.id.image3:
                        iv.setImageResource(R.drawable.side_menu);
                        break;
                    case R.id.image4:
                        iv.setImageResource(R.drawable.menupan1);
                        break;
                }
                dialog.show();
            }
        };

        ImageButton button1 = (ImageButton)findViewById(R.id.image1);
        button1.setOnClickListener(onClickListener);
        ImageButton button2 = (ImageButton)findViewById(R.id.image2);
        button2.setOnClickListener(onClickListener);
        ImageButton button3 = (ImageButton)findViewById(R.id.image3);
        button3.setOnClickListener(onClickListener);
        ImageButton button4 = (ImageButton)findViewById(R.id.image4);
        button4.setOnClickListener(onClickListener);
    }


}
