package com.aya.sakan.ui.addPost.postImages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.aya.sakan.R;
import com.bumptech.glide.Glide;

public class DisplayImgActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView selectedImage;
    public static final String TAG = "DisplayImgActivity";
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_img);

        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent(); // get Intent which was set from adapter of Previous Activity

        Uri uri = Uri.parse(intent.getStringExtra("image"));
        Glide.with(this).load(uri).into(selectedImage);

        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {
                    //do your stuff
                    Log.d(TAG, "onSwipe: up");
                    DisplayImgActivity.super.onBackPressed();
                }
                if (direction == Direction.down) {
                    //do your stuff
                    Log.d(TAG, "onSwipe: down");
                    DisplayImgActivity.super.onBackPressed();
                }
                return true;
            }


        });
        selectedImage.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }
}
