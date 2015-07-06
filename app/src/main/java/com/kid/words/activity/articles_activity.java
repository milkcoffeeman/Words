package com.kid.words.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kid.words.R;
import com.kid.words.database.DBDao_Articles;
import com.kid.words.database.DBDao_Words;
import com.kid.words.tool.ArticleDivide;
import com.kid.words.user_defined_class.Article_word;
import com.kid.words.user_defined_class.Word;

import java.io.InputStream;

/**
 * Created by guotao on 15/7/5.
 */
public class articles_activity extends ActionBarActivity implements View.OnClickListener {

    DBDao_Articles db = new DBDao_Articles(this);
    DBDao_Words DBDao_words = new DBDao_Words(this);

    private Article_word word_get = null;

    private TextView tv_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        //声明一个子线程，用于给出提示
        Thread newThread;
        newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(articles_activity.this, R.string.first_login,
                        Toast.LENGTH_LONG).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        });
        newThread.start(); //启动线程

        InputStream inputStream = getResources().openRawResource(R.raw.billgates);
        //读取jobs.txt
        com.kid.words.ReadTXT readtxt = new com.kid.words.ReadTXT();
        String outputStr = readtxt.readtxt(inputStream);
        //文章提取单词算法，并将词库导入sqlite
        ArticleDivide articledivide = new ArticleDivide();
        //删除数据库原有内容
        db.deleteall();
        //将数据库的自增id置为0
        db.initId();

        articledivide.articledivide(db, DBDao_words, outputStr);

        tv_article = (TextView) findViewById(R.id.tv_article);
        //该textview设置为可滚动
        tv_article.setMovementMethod(ScrollingMovementMethod.getInstance());
        SpannableString sp = new SpannableString(outputStr);

        //高亮特定单词
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        for(int j=1; j <= db.getCount(); j++){
            int firstLocation = outputStr.indexOf(db.find(j).getWord());
            final int temp = j;
            //设置高亮样式并添加点击响应弹出窗口
            sp.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);       //设置文件颜色
                    ds.setUnderlineText(true);      //设置下划线
                }

                @Override
                public void onClick(View widget) {
                    Word word_get = new Word();
                    word_get = DBDao_words.find(DBDao_words.findByWord(db.find(temp).getWord()));
                    builder.setMessage(word_get.getPronunciation()+"\n"+word_get.getExample())
                            .setTitle(word_get.getWord())
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    //创建对话框
                    AlertDialog ad = builder.create();
                    //显示对话框
                    ad.show();
                }
            }, firstLocation, firstLocation+db.find(j).getWord().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_article.setMovementMethod(LinkMovementMethod.getInstance());
        tv_article.setText(sp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
