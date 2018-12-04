package com.example.administrator.csmap2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.administrator.csmap2.R;
/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends AppCompatActivity {
    private ImageButton mImageView,mImageView2,mImageView3,mImageView4,mImageView5;
    private ImageButton mImageView6,mImageView7,mImageView8,mImageView9,mImageView10;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        mImageView = (ImageButton)findViewById(R.id.image_content);
        Glide.with(this).load(R.drawable.gossy_main).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout4.class);
                startActivity(i);
            }
        });
        mImageView2 = (ImageButton) findViewById(R.id.image_content2);
        Glide.with(this).load(R.drawable.bukakroo_main).into(mImageView2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, layout5.class);
                startActivity(i);
            }
        });
        mImageView3 = (ImageButton) findViewById(R.id.image_content3);
        Glide.with(this).load(R.drawable.s_chicken).into(mImageView3);
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re3.class);
                startActivity(i);
            }
        });
        mImageView4= (ImageButton) findViewById(R.id.image_content4);
        Glide.with(this).load(R.drawable.sheep).into(mImageView4);
        mImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re4.class);
                startActivity(i);
            }
        });
        mImageView5= (ImageButton) findViewById(R.id.image_content5);
        Glide.with(this).load(R.drawable.chicken).into(mImageView5);
        mImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re5.class);
                startActivity(i);
            }
        });
        mImageView6= (ImageButton) findViewById(R.id.image_content6);
        Glide.with(this).load(R.drawable.cuttlet).into(mImageView6);
        mImageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re6.class);
                startActivity(i);
            }
        });
        mImageView7= (ImageButton) findViewById(R.id.image_content7);
        Glide.with(this).load(R.drawable.sausage).into(mImageView7);
        mImageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re7.class);
                startActivity(i);
            }
        });
        mImageView8= (ImageButton) findViewById(R.id.image_content8);
        Glide.with(this).load(R.drawable.fusion).into(mImageView8);
        mImageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re8.class);
                startActivity(i);
            }
        });
        mImageView9= (ImageButton) findViewById(R.id.image_content9);
        Glide.with(this).load(R.drawable.jeon).into(mImageView9);
        mImageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re9.class);
                startActivity(i);
            }
        });
        mImageView10= (ImageButton) findViewById(R.id.image_content10);
        Glide.with(this).load(R.drawable.chilly).into(mImageView10);
        mImageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent
                        (ContentFragment.this, re10.class);
                startActivity(i);
            }
        });

    }
}

