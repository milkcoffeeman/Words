package com.kid.words.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kid.words.R;
import com.kid.words.database.DBDao_Words;
import com.kid.words.user_defined_class.Word;

/**
 * Created by guotao on 15/7/5.
 */
public class words_activity extends ActionBarActivity implements View.OnClickListener {

    private TextView tv_word;
    private TextView tv_pronunciation;
    private TextView tv_example;
    private Button btn_next;

    //words.db数据库
    DBDao_Words db = new DBDao_Words(this);
    Word word_get = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_words);

        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_pronunciation = (TextView) findViewById(R.id.tv_pronunciation);
        tv_example = (TextView) findViewById(R.id.tv_example);
        //该textview设置为可滚动
        tv_example.setMovementMethod(ScrollingMovementMethod.getInstance());
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next:
                btn_next.setText(R.string.next);
                int random_num = (int) (Math.random() * db.getCount());
                if (db.find(random_num) != null) {
                    word_get = db.find(random_num);
                    //Log.e("test", word_get.getId() + "------" + word_get.getWord() + "------" + word_get.getPronunciation() + "------" + word_get.getExample());
                    tv_word.setText(word_get.getWord());
                    tv_pronunciation.setText(word_get.getPronunciation());
                    tv_example.setText(word_get.getExample());
                    //初始化滚动条位置
                    tv_example.scrollTo(0,0);
                }

                break;
            default:
                break;
        }
    }
}
