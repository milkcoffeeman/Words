package com.kid.words;

/**
 * Created by guotao on 15/6/29.
 */
public class WordDivide {
    public void worddivide(DBDao db, String outputStr) {
        //char word_list[] = outputStr.toCharArray();
        int start = 0, end = 0;
        int flag = 0;
        Word word_input = new Word();
        while (end < outputStr.toString().length()) {

            switch (flag) {
                case 0:
                    while (end < outputStr.toString().length() && checkString(outputStr.substring(end,end+1))) {
                        end++;
                    }
                    flag++;
                    word_input.setWord(outputStr.substring(start, end));
                    start = end;
                    end++;
                    break;
                case 1:
                    while (end < outputStr.toString().length() && !checkString(outputStr.substring(end,end+1))) {
                        end++;
                    }
                    word_input.setExplain(outputStr.substring(start, end));
                    flag++;
                    start = end;
                    end++;
                    break;
                case 2:
                    while (end < outputStr.toString().length() && (outputStr.charAt(end)) != '\n') {
                        end++;
                    }
                    word_input.setExample(outputStr.substring(start, end));
                    flag = 0;
                    start = end;
                    end++;
                    db.save(word_input);
                    break;
                default:
                    break;
            }

        }

       /* Word word_get = null;
        for (int j = 0; j < db.getCount(); j++) {
            if (db.find(j) != null) {
                word_get = db.find(j);
                Log.e("test", word_get.getId() + "------" + word_get.getWord() + "------" + word_get.getExplain() + "------" + word_get.getExample());
            }
        }*/
    }

    //正则表达式，判断是否为字母，是则返回true
    private boolean checkString(String s) {
        String regEx = "^[A-Za-z]+$";
        return s.matches(regEx);
    }
}
