package com.kid.words.tool;

import com.kid.words.database.DBDao_Articles;
import com.kid.words.database.DBDao_Words;
import com.kid.words.user_defined_class.Article_word;

/**
 * Created by guotao on 15/7/5.
 */
public class ArticleDivide {

    public void articledivide(DBDao_Articles db, DBDao_Words DBDao_words, String outputStr){

        //文章中提取单词的方法
        String[] words = outputStr.split("[^a-zA-Z]+");
        for(int i=0; i<words.length; i++){
            //Log.e("test",words[i]);
            if((DBDao_words.findByWord(words[i]))>0 && db.findByWord(words[i]) ==-1){
                Article_word article_word = new Article_word();
                article_word.setWord_id(DBDao_words.findByWord(words[i]));
                article_word.setWord(words[i].trim());
                db.save(article_word);

                //Log.e("id",db.findByWord(words[i])+"");
                //Log.e("article_word",article_word.getWord()+"___"+article_word.getWord_id());
            }
        }
    }
}
