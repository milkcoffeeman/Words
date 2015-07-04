package com.kid.words;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView tv_word;
    private TextView tv_explain;
    private TextView tv_example;
    private Button btn_next;

    //words.db数据库
    DBDao db = new DBDao(this);
    Word word_get = null;

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
                    Toast.makeText(MainActivity.this, "第一次进入app，需加载数据库，请稍等",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }
            });
            newThread.start(); //启动线程

            InputStream inputStream = getResources().openRawResource(R.raw.words);
            //读取words.txt
            ReadTXT readtxt = new ReadTXT();
            String outputStr = readtxt.readtxt(inputStream);
            //分词算法，并将词库导入sqlite
            WordDivide worddivide = new WordDivide();
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
            Toast.makeText(this, "数据库加载成功！",
                    Toast.LENGTH_LONG).show();
        }

        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_explain = (TextView) findViewById(R.id.tv_explain);
        tv_example = (TextView) findViewById(R.id.tv_example);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                int random_num = (int) (Math.random() * db.getCount());
                if (db.find(random_num) != null) {
                    word_get = db.find(random_num);
                    Log.e("test", word_get.getId() + "------" + word_get.getWord() + "------" + word_get.getExplain() + "------" + word_get.getExample());
                    tv_word.setText(word_get.getWord());
                    tv_explain.setText(word_get.getExplain());
                    tv_example.setText(word_get.getExample());
                }
                break;
            default:
                break;
        }
    }

}
