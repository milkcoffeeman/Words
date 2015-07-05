package com.kid.words.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kid.words.user_defined_class.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guotao on 15/6/29.
 */
public class DBDao_Words {
    DBOpenHelper_Words dbOpenHelperWords;

    public DBDao_Words(Context context){
        this.dbOpenHelperWords =new DBOpenHelper_Words(context);
    }
    /**
     * 添加一条数据
     * @param word
     */
    public void save(Word word){
        SQLiteDatabase db= dbOpenHelperWords.getWritableDatabase();
        db.execSQL("insert into words(word,pronunciation,example) values(?,?,?)", new Object[]{word.getWord(),word.getPronunciation(),word.getExample()});
        db.close();
    }
    /**
     * 删除一条数据
     * @param id
     */
    public void delete(Integer id){
        SQLiteDatabase db= dbOpenHelperWords.getWritableDatabase();
        db.execSQL("delete from words where id=?", new Object[]{id});
        db.close();
    }
    /**
     * 更新一条数据
     * @param word
     */
    public void update(Word word){
        SQLiteDatabase db= dbOpenHelperWords.getWritableDatabase();
        db.execSQL("update words set word=?,pronunciation=?,example=? where id=?", new Object[]{word.getWord(),word.getPronunciation(),word.getExample()});
        db.close();
    }
    /**
     * 查找一条数据
     * @param id
     */
    public Word find(Integer id){
        SQLiteDatabase db= dbOpenHelperWords.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from words where id=?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            int id2=cursor.getInt(cursor.getColumnIndex("id"));
            String word2=cursor.getString(cursor.getColumnIndex("word"));
            String pronunciation2=cursor.getString(cursor.getColumnIndex("pronunciation"));
            String example2=cursor.getString(cursor.getColumnIndex("example"));
            Word word_get=new Word();
            word_get.setId(id2);
            word_get.setWord(word2);
            word_get.setPronunciation(pronunciation2);
            word_get.setExample(example2);
            return word_get;
        }
        cursor.close();
        return null;
    }
    /**
     * 根据words查找一条数据,返回ID
     * @param str
     */
    public int findByWord(String str){
        SQLiteDatabase db= dbOpenHelperWords.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from words where word like ?", new String[]{str});
        //Log.e("test_str", str );
        if(cursor.moveToFirst()){
            int id2=cursor.getInt(cursor.getColumnIndex("id"));
            //Log.e("test_id", ""+id2);
            return id2;
        }
        cursor.close();
        return -1;
    }
    /**
     * 分页查找数据
     * @param offset 跳过多少条数据
     * @param maxResult 每页多少条数据
     * @return
     */
    public List<Word> getScrollData(int offset, int maxResult){
        List<Word>words=new ArrayList<Word>();
        SQLiteDatabase db= dbOpenHelperWords.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from user order by id asc limit ?,?", new String[]{String.valueOf(offset), String.valueOf(maxResult)});
        while(cursor.moveToNext()){
            int id2=cursor.getInt(cursor.getColumnIndex("id"));
            String word2=cursor.getString(cursor.getColumnIndex("word"));
            String pronunciation2=cursor.getString(cursor.getColumnIndex("expalin"));
            String example2=cursor.getString(cursor.getColumnIndex("example"));
            Word word=new Word();
            word.setId(id2);
            word.setWord(word2);
            word.setPronunciation(pronunciation2);
            word.setExample(example2);
            words.add(word);
        }
        return words;
    }
    /**
     * 获取数据总数
     * @return
     */
    public long getCount(){
        SQLiteDatabase db= dbOpenHelperWords.getReadableDatabase();
        Cursor cursor =db.rawQuery("select count(*) from words", null);
        cursor.moveToFirst();
        long reslut=cursor.getLong(0);
        return reslut;
    }
}
