package com.kid.words;

/**
 * Created by guotao on 15/6/29.
 */
public class Word {
    private String word;
    private String explain;
    private String example;
    private int id;

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getExplain() {
        return explain;
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

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
