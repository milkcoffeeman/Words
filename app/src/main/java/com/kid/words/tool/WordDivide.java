package com.kid.words;

import com.kid.words.database.DBDao_Words;
import com.kid.words.user_defined_class.Word;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guotao on 15/6/29.
 */
public class WordDivide {
    public void worddivide(DBDao_Words db, String outputStr) {

        //把string按回车分隔
        String[] words = outputStr.split("\n");
        for (int i = 0; i < words.length; i++) {
            //识别是否存在中括号，存在则为音标和单词行
            Matcher m = Pattern.compile("\\[([^\\[\\]]+)\\]").matcher(words[i]);
            if (m.find()){
                Word word_input = new Word();
                //按[分离音标和单词
                int middle = 0;
                while(words[i].substring(middle,middle+1).equals("[") != true){
                    middle++;
                }
                word_input.setWord(words[i].substring(0,middle).trim());
                word_input.setPronunciation(words[i].substring(middle,words[i].length()));

                //将单词剩余部分识别输入到exmple中
                for(int j = i+1; j<words.length;j++){
                    Matcher mm = Pattern.compile("\\[([^\\[\\]]+)\\]").matcher(words[j]);
                    if (mm.find()){
                        String example = "";
                        for(int k = i+1; k < j; k++){
                            if(!checkString(words[k]))
                            {
                                example = example + words[k] + "\n";
                            }
                        }
                        word_input.setExample(example);
                        db.save(word_input);
                        break;
                    }

                    if(j == words.length-1){
                        String example = "";
                        for(int k = i+1; k < j; k++){
                            if(!checkString(words[k]))
                            {
                                example = example + words[k] + "\n";
                            }
                        }
                        word_input.setExample(example);
                        db.save(word_input);
                        break;
                    }
                }
            }

        }
       /*Word word_get = null;
        for (int j = 0; j < db.getCount(); j++) {
            if (db.find(j) != null) {
                word_get = db.find(j);
                Log.e("test", word_get.getId() + "------" + word_get.getWord() + "------" + word_get.getPronunciation() + "------" + word_get.getExample());
            }
        }*/
    }

    //匹配空白行的正则表达式：\n\s*\r
    //正则表达式，判断是否为空白行，是则返回true
    private boolean checkString(String s) {
        String regEx = "\\n\\s*\\r";
        return s.matches(regEx);
    }
}
