package com.example.chapter3.demo.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.chapter3.demo.R;

public class LikeActivity extends AppCompatActivity {

    public static final Long DURATION = 200L;

    private ImageView likeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        likeView = findViewById(R.id.like_image);
        setAnimation();
    }

    private void setAnimation() {
        final Animation step1 = AnimationUtils.loadAnimation(this, R.anim.like_step_1);
        final Animation step2 = AnimationUtils.loadAnimation(this, R.anim.like_step_2);
        final Animation step3 = AnimationUtils.loadAnimation(this, R.anim.like_step_3);

        step1.setDuration(DURATION);
        step2.setDuration(DURATION);
        step3.setDuration(DURATION);


        step1.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setAnimation(step2);
            }
        });
        step2.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setAnimation(step3);
            }
        });
        step3.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setColorFilter(Color.RED);
            }
        });
        likeView.setAnimation(step1);
    }

    static abstract class EasyListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}