package com.kid.words.tool;

import android.content.Context;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by guotao on 15/7/9.
 */
public class FileHelper {

    private Context context;
    //判断sd卡是否存在
    private boolean hasSD = false;
    //sd卡的位置
    private String SDPATH;
    //应用的地址
    private String FILESPATH;

    public FileHelper(Context context){
        this.context = context;
        hasSD = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILESPATH = this.context.getFilesDir().getPath();
    }

    //创建SD卡文件
    public File createSDFile(String fileName)throws IOException{
        File file = new File(SDPATH + "//" + fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    //删除SD卡文件
    public boolean deleteSDFile(String fileName){
        File file = new File(SDPATH + "//" +fileName);
        if(file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        return file.delete();
    }

    //写入内容到SD卡中的TXT文件
    public void writeSDFile(String str, String fileName){
        try {
            FileWriter fw = new FileWriter(SDPATH + "//" + fileName);
            File f = new File(SDPATH + "//" + fileName);
            fw.write(str);
            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取SD卡中txt文档
    public String readSDFile(String fileName){
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1){
                sb.append((char)c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean isHasSD() {
        return hasSD;
    }

    public String getSDPATH() {
        return SDPATH;
    }

    public String getFILESPATH() {
        return FILESPATH;
    }
}
