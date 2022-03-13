package com.example.chapter3.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.chapter3.demo.animation.AnimationActivity;
import com.example.chapter3.demo.animation.AnimationSanlian;
import com.example.chapter3.demo.animation.AnimatorTaskActivity;
import com.example.chapter3.demo.animation.FrameAnimationActivity;
import com.example.chapter3.demo.animation.LikeActivity;
import com.example.chapter3.demo.animation.LottieActivity;
import com.example.chapter3.demo.animation.RotationPropertyActivity;
import com.example.chapter3.demo.animation.ScalePropertyActivity;
import com.example.chapter3.demo.fragment.DynamicAddFragmentActivity;
import com.example.chapter3.demo.fragment.FragmentAActivity;
import com.example.chapter3.demo.fragment.LifecycleFragmentActivity;
import com.example.chapter3.demo.fragment.StaticColorActivity;
import com.example.chapter3.demo.fragment.masterdetail.ItemsListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindActivity(R.id.btn_show_fragment_a, FragmentAActivity.class);
        bindActivity(R.id.btn_fragment_add_demo, DynamicAddFragmentActivity.class);
        bindActivity(R.id.btn_fragment_arguments, StaticColorActivity.class);
        bindActivity(R.id.btn_master_detail, ItemsListActivity.class);

        bindActivity(R.id.btn_frame_animation, FrameAnimationActivity.class);
        bindActivity(R.id.btn_show_animation, AnimationActivity.class);
        bindActivity(R.id.btn_show_like, LikeActivity.class);

        bindActivity(R.id.btn_rotation_demo, RotationPropertyActivity.class);
        bindActivity(R.id.btn_scale_demo, ScalePropertyActivity.class);
        bindActivity(R.id.btn_lottie_demo, LottieActivity.class);
        bindActivity(R.id.btn_fragment_lifecycle_demo, LifecycleFragmentActivity.class);
        bindActivity(R.id.btn_animator, AnimatorTaskActivity.class);
        bindActivity(R.id.btn_sanlian, AnimationSanlian.class);
    }
    //定义上文函数
    private void bindActivity(final int btnId, final Class<?> activityClass) {
        findViewById(btnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, activityClass));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
