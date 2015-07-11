package com.kid.words.tool;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guotao on 15/7/8.
 */
public class GetWebContent {
    public String GetWebContent(String url) throws Exception {
        try {
            URL aURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
            //错误
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            Log.e("GetWebContent",e.getMessage());
        }
        return null;
    }
}
