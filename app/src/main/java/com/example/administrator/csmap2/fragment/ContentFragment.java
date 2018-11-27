package com.example.administrator.csmap2.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.csmap2.Atm;
import com.example.administrator.csmap2.MainActivity;
import com.example.administrator.csmap2.R;
import com.example.administrator.csmap2.bus.Main2Activity;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends AppCompatActivity {
    private Button mImageView,mImageView2,mImageView3,mImageView4,mImageView5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        mImageView = (Button)findViewById(R.id.image_content);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout4.class);
                startActivity(i);
            }
        });
        mImageView2 = (Button) findViewById(R.id.image_content2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout5.class);
                startActivity(i);
            }
        });
        mImageView3 = (Button) findViewById(R.id.image_content3);
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout5.class);
                startActivity(i);
            }
        });
        mImageView4= (Button) findViewById(R.id.image_content4);
        mImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout5.class);
                startActivity(i);
            }
        });
        mImageView5= (Button) findViewById(R.id.image_content5);
        mImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout5.class);
                startActivity(i);
            }
        });
    }
}

