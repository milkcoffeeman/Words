package com.kid.words.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kid.words.user_defined_class.Article_word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guotao on 15/7/5.
 */
public class DBDao_Articles {
    DBOpenHelper_Articles dbOpenHelperArticles;

    public DBDao_Articles(Context context){
        this.dbOpenHelperArticles =new DBOpenHelper_Articles(context);
    }
    /**
     * 添加一条数据
     * @param article_word
     */
    public void save(Article_word article_word){
        SQLiteDatabase db= dbOpenHelperArticles.getWritableDatabase();
        db.execSQL("insert into articles(word_id,word) values(?,?)", new Object[]{article_word.getWord_id(),article_word.getWord()});
        db.close();
    }
    /**
     * 删除一条数据
     * @param id
     */
    public void delete(Integer id){
        SQLiteDatabase db= dbOpenHelperArticles.getWritableDatabase();
        db.execSQL("delete from articles where id=?", new Object[]{id});
        db.close();
    }
    /**
     * 删除所有数据
     */
    public void deleteall(){
        SQLiteDatabase db= dbOpenHelperArticles.getWritableDatabase();
        db.execSQL("delete from articles");
        db.close();
    }
    /**
     * 更新一条数据
     * @param article_word
     */
    public void update(Article_word article_word){
        SQLiteDatabase db= dbOpenHelperArticles.getWritableDatabase();
        db.execSQL("update articles set word_id=?,word=? where id=?", new Object[]{article_word.getWord_id(),article_word.getWord()});
        db.close();
    }
    /**
     * 查找一条数据
     * @param id
     */
    public Article_word find(Integer id){
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from articles where id=?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            //Log.e("findid", id.toString());
            int id2=cursor.getInt(cursor.getColumnIndex("word_id"));
            String word2=cursor.getString(cursor.getColumnIndex("word"));
            Article_word word_get=new Article_word();
            word_get.setWord_id(id2);
            word_get.setWord(word2);
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
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from articles where word like ?", new String[]{str});
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
    public List<Article_word> getScrollData(int offset, int maxResult){
        List<Article_word>words=new ArrayList<Article_word>();
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from user order by id asc limit ?,?", new String[]{String.valueOf(offset), String.valueOf(maxResult)});
        while(cursor.moveToNext()){
            int id2=cursor.getInt(cursor.getColumnIndex("word_id"));
            String word2=cursor.getString(cursor.getColumnIndex("word"));

            Article_word word=new Article_word();
            word.setWord_id(id2);
            word.setWord(word2);
            words.add(word);
        }
        return words;
    }
    /**
     * 获取数据总数
     * @return
     */
    public long getCount(){
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("select count(*) from articles", null);
        cursor.moveToFirst();
        long reslut=cursor.getLong(0);
        return reslut;
    }
    /**
     * 自增id初始化
     *
     */
    public void initId(){
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("update sqlite_sequence set seq = 0", null);
        cursor.moveToFirst();
        //Cursor cursor =db.rawQuery("select max(seq) from sqlite_sequence", null);
    }
    /**
     * 获得最大id
     *
     */
    public int getMaxId(){
        SQLiteDatabase db= dbOpenHelperArticles.getReadableDatabase();
        Cursor cursor =db.rawQuery("select max(seq) from sqlite_sequence", null);
        while(cursor.moveToNext()){
            int id2=cursor.getInt(0);
            return id2;
        }
        return 0;
    }
}
