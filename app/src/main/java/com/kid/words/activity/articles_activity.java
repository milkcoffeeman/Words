package com.kid.words.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kid.words.R;
import com.kid.words.database.DBDao_Articles;
import com.kid.words.database.DBDao_Words;
import com.kid.words.tool.ArticleDivide;
import com.kid.words.user_defined_class.Article_word;
import com.kid.words.user_defined_class.Word;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by guotao on 15/7/5.
 */
public class articles_activity extends ActionBarActivity implements View.OnClickListener {

    DBDao_Articles db = new DBDao_Articles(this);
    DBDao_Words DBDao_words = new DBDao_Words(this);

    private Article_word word_get = null;

    private TextView tv_article;
    private ListView lv_article_words;

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
        tv_article.setText(outputStr);
        lv_article_words = (ListView) findViewById(R.id.lv_articl_words);

        /*生成动态数组，加入数据*/
        final ArrayList<String> article_words = new ArrayList<String>();
        for(int i=0;i <db.getCount();i++){
            if (db.find(i) != null) {
                //Log.e("test",db.find(i).getWord());
                article_words.add(new String(db.find(i).getWord()));
            }

        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,article_words);
        lv_article_words.setAdapter(adapter);
        lv_article_words.setOnItemClickListener(new OnItemClickListener() {

            //arg0  发生点击动作的AdapterView
            //arg1 在AdapterView中被点击的视图(它是由adapter提供的一个视图)
            //arg2 视图在adapter中的位置
            //arg3 被点击元素的行id
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //将学生的详细信息在对话框里面显示
                Word word_get = new Word();
                word_get = DBDao_words.find(db.find((int)arg3+1).getWord_id());
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
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
