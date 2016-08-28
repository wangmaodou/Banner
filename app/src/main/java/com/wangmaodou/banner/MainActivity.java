package com.wangmaodou.banner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangmaodou.banner.View.Banner;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    Banner mBanner;
    Bitmap[] bitmaps;
    int mClickCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mBanner=(Banner)findViewById(R.id.main_banner);
        mButton=(Button)findViewById(R.id.main_button);

        mBanner.setBitmaps(bitmaps);
        mBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickCount=((Banner)view).getCurrentImageIndex();
                mButton.setText(mClickCount+"");
            }
        });
        mButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                mBanner.startScroll();
            }
        });

    }

    private void initData(){
        bitmaps=new Bitmap[3];
        bitmaps[0]= BitmapFactory.decodeResource(getResources(),R.drawable.arc);
        bitmaps[1]= BitmapFactory.decodeResource(getResources(),R.drawable.me);
        bitmaps[2]= BitmapFactory.decodeResource(getResources(),R.drawable.ver);
    }
}
