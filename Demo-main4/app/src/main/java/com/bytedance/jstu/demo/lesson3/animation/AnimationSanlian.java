package com.bytedance.jstu.demo.lesson3.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationSanlian extends AppCompatActivity implements View.OnClickListener {
    int i=0;
    private Button mSanlian ;

    private ImageView mPicture1;
    private ImageView mPicture2;
    private ImageView mPicture3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanlian);
        //找寻Button，建立对应关系

        mSanlian = findViewById(R.id.button);
        mPicture1 = findViewById(R.id.btn_like);
        mPicture2=findViewById(R.id.btn_coin);
        mPicture3=findViewById(R.id.btn_collect);
        mSanlian.setOnClickListener(this) ;
    }
    @Override
    public void onClick(View v) {

        Animation loadAnimation;
        if(v.getId()==R.id.button) {
            //动画设置
            ObjectAnimator anim11 = ObjectAnimator.ofFloat(mPicture1, "rotation", 15);
            ObjectAnimator anim22 = ObjectAnimator.ofFloat(mPicture1, "rotation", -15);
            ObjectAnimator anim33 = ObjectAnimator.ofFloat(mPicture1, "rotation", 0);
            ObjectAnimator anim44 = ObjectAnimator.ofFloat(mPicture1, "rotation", 15);
            ObjectAnimator anim55 = ObjectAnimator.ofFloat(mPicture1, "rotation", -15);
            ObjectAnimator anim66 = ObjectAnimator.ofFloat(mPicture1, "rotation", 15);
            ObjectAnimator anim77 = ObjectAnimator.ofFloat(mPicture1, "rotation", -15);
            ObjectAnimator anim88 = ObjectAnimator.ofFloat(mPicture1, "rotation", 15);
            ObjectAnimator anim99 = ObjectAnimator.ofFloat(mPicture1, "rotation", -15);

            ObjectAnimator anim1 = ObjectAnimator.ofFloat(mPicture2, "scaleX",0.8f);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(mPicture2, "scaleY",0.8f);
            ObjectAnimator anim3 = ObjectAnimator.ofFloat(mPicture2, "scaleX",1.2f);
            ObjectAnimator anim4 = ObjectAnimator.ofFloat(mPicture2, "scaleY",1.2f);
            ObjectAnimator anim9 = ObjectAnimator.ofFloat(mPicture2, "scaleX",1f);
            ObjectAnimator anim10 = ObjectAnimator.ofFloat(mPicture2, "scaleY",1f);

            ObjectAnimator anim5 = ObjectAnimator.ofFloat(mPicture3, "scaleX",0.8f);
            ObjectAnimator anim6 = ObjectAnimator.ofFloat(mPicture3, "scaleY",0.8f);
            ObjectAnimator anim7 = ObjectAnimator.ofFloat(mPicture3, "scaleX",1.2f);
            ObjectAnimator anim8 = ObjectAnimator.ofFloat(mPicture3, "scaleY",1.2f);
            ObjectAnimator anim12 = ObjectAnimator.ofFloat(mPicture3, "scaleX",1f);
            ObjectAnimator anim13 = ObjectAnimator.ofFloat(mPicture3, "scaleY",1f);

            final AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.play(anim11);
            animatorSet2.play(anim22).after(anim11);
            animatorSet2.play(anim44).after(anim22);
            animatorSet2.play(anim55).after(anim44);
            animatorSet2.play(anim66).after(anim55);
            animatorSet2.play(anim77).after(anim66);
            animatorSet2.play(anim88).after(anim77);
            animatorSet2.playTogether(anim88,anim1,anim2,anim5,anim6);
            animatorSet2.play(anim99).after(anim88);
            animatorSet2.playTogether(anim99,anim3,anim4,anim7,anim8);
            animatorSet2.play(anim33).after(anim99);
            animatorSet2.playTogether(anim33,anim9,anim10,anim12,anim13);
            animatorSet2.setDuration(200);
            i++;
            animatorSet2.start();
            mPicture1.setImageDrawable(getResources().getDrawable(R.drawable.like_active));

            //设置延时函数
            Timer timer = new Timer();
            timer.schedule(task, 1400);//3秒后执行TimeTask的run方法
            //替换照片 实现已三连
            //本质是将mPicture替换成另一张图片
            if(i%2==0) {


            }

        }
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            mPicture2.setImageDrawable(getResources().getDrawable(R.drawable.coin_active));
            mPicture3.setImageDrawable(getResources().getDrawable(R.drawable.collect_active));
        }
    };


}







