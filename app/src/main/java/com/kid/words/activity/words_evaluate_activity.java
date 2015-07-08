package com.kid.words.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kid.words.R;
import com.kid.words.database.DBDao_Words;
import com.kid.words.user_defined_class.Word;

/**
 * Created by guotao on 15/7/8.
 */
public class words_evaluate_activity extends ActionBarActivity implements View.OnClickListener {

    public final static int WORDS_EVALUATE_NEEDS = 40;

    int nowNum = 0;
    int rightNum = 0;
    Word word[] = new Word[WORDS_EVALUATE_NEEDS];

    DBDao_Words DBDao_words = new DBDao_Words(this);

    private TextView tv_word;
    private TextView tv_pronunciation;
    private TextView tv_example;
    private Button btn_know;
    private Button btn_unknow;
    private Button btn_start;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_evaluate);

        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_pronunciation = (TextView) findViewById(R.id.tv_pronunciation);
        tv_example = (TextView) findViewById(R.id.tv_example);
        btn_know = (Button) findViewById(R.id.btn_know);
        btn_unknow = (Button) findViewById(R.id.btn_unknow);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_know.setOnClickListener(this);
        btn_unknow.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        int randomNum[];
        randomNum = randomNumber(0,(int)DBDao_words.getCount(),WORDS_EVALUATE_NEEDS);
        for(int i=0; i<WORDS_EVALUATE_NEEDS; i++){
            word[i] = new Word();
            word[i].setWord(DBDao_words.find(randomNum[i]).getWord());
            word[i].setPronunciation(DBDao_words.find(randomNum[i]).getPronunciation());
            word[i].setExample(DBDao_words.find(randomNum[i]).getExample());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unknow:
                if(nowNum<WORDS_EVALUATE_NEEDS) {
                    tv_word.setText(word[nowNum].getWord());
                    tv_pronunciation.setText(word[nowNum].getPronunciation());
                    nowNum++;
                }else{
                    //实例化SharedPreferences对象（第一步）
                    SharedPreferences mySharedPreferences = getSharedPreferences("file",
                            Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("wordsEvaluate",wordsEvaluate()+"");
                    editor.commit();
                    Log.e("test",mySharedPreferences.getString("wordsEvaluate", "test"));
                    String wordsEvaluateOutput;
                    if(rightNum<10){
                        wordsEvaluateOutput = getResources().getString(R.string.class_four_words);
                    }
                    else if(rightNum>=10 && rightNum<20){
                        wordsEvaluateOutput = getResources().getString(R.string.class_three_words);
                    }
                    else if(rightNum >=20 && rightNum<30){
                        wordsEvaluateOutput = getResources().getString(R.string.class_two_words);
                    }
                    else{
                        wordsEvaluateOutput = getResources().getString(R.string.class_one_words);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getResources().getString(R.string.your_vocabulary_is) + "\n" + wordsEvaluateOutput)
                            .setTitle(R.string.words_evaluate)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    words_evaluate_activity.this.finish();
                                }
                            });
                    //创建对话框
                    AlertDialog ad = builder.create();
                    //显示对话框
                    ad.show();
                    btn_know.setVisibility(View.GONE);
                    btn_unknow.setVisibility(View.GONE);
                    btn_back.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_know:
                if(nowNum<WORDS_EVALUATE_NEEDS) {
                    tv_word.setText(word[nowNum].getWord());
                    tv_pronunciation.setText(word[nowNum].getPronunciation());
                    nowNum++;
                    rightNum++;
                }else{
                    //实例化SharedPreferences对象（第一步）
                    SharedPreferences mySharedPreferences = getSharedPreferences("file",
                            Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("wordsEvaluate",wordsEvaluate()+"");
                    editor.commit();

                    SharedPreferences sharedPreferences = getSharedPreferences("file",
                            Activity.MODE_PRIVATE);
                    // 使用getString方法获得value，注意第2个参数是value的默认值
                    Log.e("test", sharedPreferences.getString("wordsEvaluate", "true"));
                    String wordsEvaluateOutput;
                    if(rightNum<10){
                        wordsEvaluateOutput = getResources().getString(R.string.class_four_words);
                    }
                    else if(rightNum>=10 && rightNum<20){
                        wordsEvaluateOutput = getResources().getString(R.string.class_three_words);
                    }
                    else if(rightNum >=20 && rightNum<30){
                        wordsEvaluateOutput = getResources().getString(R.string.class_two_words);
                    }
                    else{
                        wordsEvaluateOutput = getResources().getString(R.string.class_one_words);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getResources().getString(R.string.your_vocabulary_is) + "\n" + wordsEvaluateOutput)
                            .setTitle(R.string.words_evaluate)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    words_evaluate_activity.this.finish();
                                }
                            });
                    //创建对话框
                    AlertDialog ad = builder.create();
                    //显示对话框
                    ad.show();
                    btn_know.setVisibility(View.GONE);
                    btn_unknow.setVisibility(View.GONE);
                    btn_back.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_start:
                btn_know.setVisibility(View.VISIBLE);
                btn_unknow.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.GONE);
                tv_word.setText(word[nowNum].getWord());
                tv_pronunciation.setText(word[nowNum].getPronunciation());
                nowNum++;
                break;
            case R.id.btn_back:
                words_evaluate_activity.this.finish();
                break;
            default:
                break;
        }
    }


    //产生needs个start到end的不重复数字
    public int[] randomNumber(int start, int end, int needs){
        int a[] = new int[needs];
        a[0] = (int)(Math.random()*(end-start)) + start;
        for (int i = 1; i < a.length; i++) {
            a[i] = (int)(Math.random()*(end-start)) + start;
            for (int j = 0; j < i; j++) {
                while (a[i] == a[j]) {
                    i--;
                }
            }
        }
        return a;
    }
    //判断单词量评级
    public int wordsEvaluate(){
        if(rightNum<10){
            return 4;
        }
        else if(rightNum>=10 && rightNum<20){
            return 3;
        }
        else if(rightNum >=20 && rightNum<30){
            return 2;
        }
        else{
            return 1;
        }
    }
}
