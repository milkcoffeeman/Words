package com.kid.words.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kid.words.R;
import com.kid.words.database.DBDao_Articles;
import com.kid.words.database.DBDao_Words;
import com.kid.words.tool.ArticleDivide;
import com.kid.words.user_defined_class.Article_word;

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

        InputStream inputStream = getResources().openRawResource(R.raw.jobs);
        //读取jobs.txt
        com.kid.words.ReadTXT readtxt = new com.kid.words.ReadTXT();
        String outputStr = readtxt.readtxt(inputStream);
        //文章提取单词算法，并将词库导入sqlite
        ArticleDivide articledivide = new ArticleDivide();
        //删除数据库原有内容
        db.deleteall();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,article_words);
        lv_article_words.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
