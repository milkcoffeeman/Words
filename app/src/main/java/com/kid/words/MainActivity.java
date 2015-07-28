package com.kid.words;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kid.words.activity.articles_activity;
import com.kid.words.activity.articles_word_activity;
import com.kid.words.activity.website_open_activity;
import com.kid.words.activity.words_activity;
import com.kid.words.activity.words_evaluate_activity;
import com.kid.words.database.DBDao_Words;
import com.kid.words.tool.FileHelper;
import com.kid.words.tool.GetWebContent;
import com.kid.words.user_defined_class.Word;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //words.db数据库
    DBDao_Words db = new DBDao_Words(this);
    Word word_get = null;

    private Button btn_words;
    private Button btn_articles;
    private Button btn_artcles_word;
    private Button btn_words_evaluate;
    private Button btn_website_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一次进入软件，将导入数据库
        SharedPreferences sharedPreferences = getSharedPreferences("file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String isFirst = sharedPreferences.getString("isFirst", "true");
        if (isFirst.equals("true")) {

            //声明一个子线程，用于给出提示
            Thread newThread;
            newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,R.string.first_login,
                            Toast.LENGTH_LONG).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }
            });
            newThread.start(); //启动线程

            InputStream inputStream = getResources().openRawResource(R.raw.words);
            //读取words.txt
            com.kid.words.ReadTXT readtxt = new com.kid.words.ReadTXT();
            String outputStr = readtxt.readtxt(inputStream);
            //分词算法，并将词库导入sqlite
            com.kid.words.WordDivide worddivide = new com.kid.words.WordDivide();
            worddivide.worddivide(db, outputStr);

            //写入file，isFisrt置为false
            //实例化SharedPreferences对象（第一步）
            SharedPreferences mySharedPreferences = getSharedPreferences("file",
                    Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
            editor.putString("isFirst", "false");
            //提交当前数据
            editor.commit();
            //使用toast信息提示框提示成功写入数据
            Toast.makeText(this, R.string.database_success,
                    Toast.LENGTH_LONG).show();
        }


        //webcontent获取，载入网站上推送的文章
        //声明一个子线程，用于给出提示
        Thread newThreadWeb;
        newThreadWeb = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String webContent = null;
                try {
                    webContent =  new GetWebContent().GetWebContent(getResources().getString(R.string.WEBADDRESS));
                    FileHelper fileHelper = new FileHelper(MainActivity.this);
                    if(fileHelper.isHasSD()){
                        fileHelper.createSDFile("Words_english_article.txt");
                        fileHelper.writeSDFile(webContent, "Words_english_article.txt");
                    }
                }catch (Exception e){
                    Log.e("test", e.getMessage()+"");
                }
                if(webContent == null){
                    Toast.makeText(MainActivity.this,R.string.net_problem,
                            Toast.LENGTH_LONG).show();
                }
                Looper.loop();// 进入loop中的循环，查看消息队列

            }
        });
        newThreadWeb.start(); //启动线程


        btn_words = (Button) findViewById(R.id.btn_words);
        btn_words.setOnClickListener(this);
        btn_articles = (Button) findViewById(R.id.btn_articles);
        btn_articles.setOnClickListener(this);
        btn_artcles_word = (Button) findViewById(R.id.btn_artcles_word);
        btn_artcles_word.setOnClickListener(this);
        btn_words_evaluate = (Button) findViewById(R.id.btn_words_evaluate);
        btn_words_evaluate.setOnClickListener(this);
        btn_website_open = (Button) findViewById(R.id.btn_website_open);
        btn_website_open.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_words:
                Intent it1 = new Intent(this, words_activity.class);
                startActivity(it1);
                break;
            case R.id.btn_articles:
                Intent it2 = new Intent(this, articles_activity.class);
                startActivity(it2);
                break;
            case R.id.btn_artcles_word:
                Intent it3 = new Intent(this, articles_word_activity.class);
                startActivity(it3);
            case R.id.btn_words_evaluate:
                Intent it4 = new Intent(this, words_evaluate_activity.class);
                startActivity(it4);
            case R.id.btn_website_open:
                Intent it5 = new Intent(this, website_open_activity.class);
                startActivity(it5);
            default:
                break;
        }
    }

}
