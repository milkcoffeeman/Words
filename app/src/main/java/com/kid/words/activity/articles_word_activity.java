package com.kid.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kid.words.R;
import com.kid.words.database.DBDao_Articles;
import com.kid.words.database.DBDao_Words;
import com.kid.words.tool.ArticleDivide;
import com.kid.words.user_defined_class.Word;

import java.io.InputStream;

/**
 * Created by guotao on 15/7/6.
 */
public class articles_word_activity extends ActionBarActivity implements View.OnClickListener {
    DBDao_Articles db = new DBDao_Articles(this);
    DBDao_Words DBDao_words = new DBDao_Words(this);

    private TextView tv_word;
    private TextView tv_pronunciation;
    private TextView tv_example;
    private Button btn_next;
    private Button btn_articles_start;

    int words_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_word);

        //声明一个子线程，用于给出提示
        Thread newThread;
        newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(articles_word_activity.this, R.string.first_login,
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

        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_pronunciation = (TextView) findViewById(R.id.tv_pronunciation);
        tv_example = (TextView) findViewById(R.id.tv_example);
        //该textview设置为可滚动
        tv_example.setMovementMethod(ScrollingMovementMethod.getInstance());
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_articles_start = (Button) findViewById(R.id.btn_articles_start);
        btn_articles_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next:
                btn_next.setText(R.string.next);
                if (words_id < db.getCount()+1 && db.find(words_id) != null) {
                    Word word_need = new Word();
                    word_need = DBDao_words.find(db.find(words_id).getWord_id());
                    tv_word.setText(word_need.getWord());
                    tv_pronunciation.setText(word_need.getPronunciation());
                    tv_example.setText(word_need.getExample());
                    //初始化滚动条位置
                    tv_example.scrollTo(0, 0);
                    words_id++;
                }else{
                    words_id = 1;
                    Toast.makeText(articles_word_activity.this, R.string.learn_words_again,
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_articles_start:
                Intent it = new Intent(this, articles_activity.class);
                finish();
                startActivity(it);
                break;
            default:
                break;
        }
    }
}
