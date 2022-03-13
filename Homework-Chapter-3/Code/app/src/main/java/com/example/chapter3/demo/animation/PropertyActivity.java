package com.example.chapter3.demo.animation;
//TweenAnimation --> PropertyAnimation的界面

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class PropertyActivity extends AppCompatActivity {
    //给四个Button命名
    private Button mTestButton1;
    private Button mTranslate;
    private Button mAnimationTranslate;
    private Button mTestButton2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载界面
        setContentView(R.layout.property_anim_layout);

        //找寻Button，建立对应关系
        mTestButton1 = findViewById(R.id.button);
        //属性动画
        mTranslate = findViewById(R.id.button1);


        mAnimationTranslate = findViewById(R.id.button2);
        mTestButton2 = findViewById(R.id.button3);

        //在本次代码中，setOnClickListener() 方法为按钮注册一个监听器，点击按钮时就会执行监听器中的 onClick() 方法。
        //OnClicklistener是一个接口,不能实例化,这就是一个匿名内部类。
        mTestButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用getContext获取的是当前对象所在的Context, Context通常翻译成上下文，我通常当成场景来理解。
                //Toast:是一个类，主要管理消息的提示。

                //Toast toast = Toast.makeText(context, “”, time);
                //这三个参数分别是：
                //1.当前的上下文环境；（getApplicationContext这个方法可以获取）
                //2.要显示的字符串；（就是一般的字符串，可以写在string.xml中）
                //3.显示的时间长短；（有toast默认的参数，也可以自己设定）

                //show()，表示显示这个Toast消息提醒，当程序
                //运行到这里的时候，就会显示出来，如果不调用show()方法，这个Toast对象存在，但是并不会显示，所以
                //一定不要忘记。
                Toast.makeText(v.getContext(), "点击了button1", Toast.LENGTH_LONG).show();
            }
        });

        mTestButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了button2", Toast.LENGTH_LONG).show();
            }
        });

        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translateX",300);
//                PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translateY",300);
//                ObjectAnimator.ofPropertyValuesHolder(mTestButton1,pvh1,pvh2).setDuration(1000).start();

                //ofFloat：数据为浮点类型，传进去的值列表，就表示动画时的变化范围，可以传进去任何数量的值
                ValueAnimator animator = ValueAnimator.ofFloat(0f,300f);
                animator.setDuration(1000);
                //没有设置插值器，ValueAnimator的默认插值器是加速减速插值器AccelerateDecelerateInterpolator，
                //先加速后减速

                //只让值动画是没什么意义的，我们要根据值进行一些操作。因此要增加值变化的监听。ValueAnimator
                // 提供了AnimatorUpdateListener来监听，在onAnimationUpdate方法中调用
                // animation.getAnimatedValue()获取动画最近的值，然后根据值进行操作。

                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //它的意义就是获取动画在当前运动点的值,类型与前文是ofInt还是ofFloat一致
                        //括号里的float是强制转换
                        float animatedValue = (float) animation.getAnimatedValue();

                        //函数setTranslationX和setTranslationY，api版本为11，是设置view相对原始位置的偏移量
                        //参数是偏移量（绝对位置的偏移量）
                        //即若要回到原位，需要的是setTranslationX(0)
                        mTestButton1.setTranslationX(animatedValue);
                    }
                });
                //开始动画
                animator.start();

            }
        });
        mAnimationTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android:fromXDelta：动画开始时，在X轴方向上的位置；取值类型有三种：数字；百分比；百分比+”p”;
                //数字：例如50.0，这里的单位是px像素
                //百分比：例如50%，这里是相对于自己控件宽度的百分比，实际的值是mIvImg.getWidth()*50%；
                //百分比+”p”：例如50%p，这里是表示相对于自己控件的父控件的百分比，
                //android:fromYDelta：动画开始时，在Y轴方向上的位置；取值类型同上
                TranslateAnimation animation = new TranslateAnimation(0.0f, 300f, 0.0f, 300.0f);

                //android:duration：动画持续时长
                //android:fillAfter：动画结束之后是否保持动画的最终状态；true，表示保持动画的最终状态
                //android:fillBefore：动画结束之后是否保持动画开始前的状态；true，表示恢复到动画开始前的状态
                animation.setDuration(1000);
                animation.setFillAfter(true);
                animation.setFillBefore(false);

                //开始动画
                mTestButton2.startAnimation(animation);

            }
        });

    }
}
