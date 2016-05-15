package com.example.wuyin.richtextdemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        //1、让文本和图片一起显示
        text1.setText(showTextWithImage("我是文本[大兵]",R.mipmap.dabing));

        //2、让某段文字变颜色
        text2.setText(showTextWithColor("我喜欢你,然而你不知道", Color.BLUE));

        //3、让某段文字可以被点击并跳转
        String text = "详情请点击<a href = 'http://www.baidu.com'>百度</a>";
        Spanned spanned = Html.fromHtml(text);
        text3.setText(spanned);
        text3.setTextColor(Color.BLUE);
        text3.setMovementMethod(LinkMovementMethod.getInstance());  //设置可以点击超链接

        //4、让某段玩奶子可以被点击自定义点击的逻辑判断
        String string = "小白、大白、等人喜欢一个人。。。";
        SpannableString ss = new SpannableString(string);
        MyUrlSpan span = new MyUrlSpan(string.substring(0,string.indexOf("、")));
        ss.setSpan(span,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        text4.setText(ss);
        text4.setMovementMethod(LinkMovementMethod.getInstance());


    }

    /**
     * 显示的文字变颜色
     * @param text   要显示的文本
     * @param color   需要显示的颜色
     * @return   需要变颜色的文本
     */
    private CharSequence showTextWithColor(String text, int color) {

        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        int end = text.indexOf(",");
        ss.setSpan(colorSpan,0,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 文本和图片一起显示
     *
     * @param s   显示的文本
     * @param image    要显示的图片
     * @return   需要显示为图片的文本
     */
    private SpannableString showTextWithImage(String s, int image) {

        SpannableString ss = new SpannableString(s);
        Drawable drawable = getResources().getDrawable(image);

        //设置边界
        drawable.setBounds(0,0,60,60);
        ImageSpan span = new ImageSpan(drawable);

        int start = s.indexOf("[");
        int end = s.indexOf("]")+1;

        ss.setSpan(span,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;

    }

    /**
     * 初始化布局控件
     */
    private void initViews() {
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);

    }

    class MyUrlSpan extends URLSpan{

        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(MainActivity.this, getURL(), Toast.LENGTH_SHORT).show();
        }
    }
}
