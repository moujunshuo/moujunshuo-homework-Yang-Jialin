package com.example.chapter3.demo.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter3.demo.R;

public class AnimatorTaskActivity extends AppCompatActivity {

    private Button item;
    private TextView car;
    private LinearLayout carWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_task);
        item = findViewById(R.id.my_item);
        car = findViewById(R.id.cart);
        carWrapper = findViewById(R.id.cart_wrapper);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
    }



}