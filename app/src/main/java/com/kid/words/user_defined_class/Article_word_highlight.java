package com.kid.words.user_defined_class;

/**
 * Created by guotao on 15/7/6.
 */
public class Article_word_highlight {
    private int first_word_location;
    private String word;

    public void setFirst_word_location(int first_word_location) {
        this.first_word_location = first_word_location;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFirst_word_location() {

        return first_word_location;
    }

    public String getWord() {
        return word;
    }
}
