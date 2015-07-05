package com.kid.words.user_defined_class;

/**
 * Created by guotao on 15/6/29.
 */
public class Word {
    private String word;
    private String pronunciation;
    private String example;
    private int id;

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
