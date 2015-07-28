package com.kid.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kid.words.R;

/**
 * Created by guotao on 15/7/25.
 */
public class website_open_activity extends ActionBarActivity implements View.OnClickListener {

    Button btn_input_website;
    EditText et_input_website;
    WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_website_open);

        btn_input_website = (Button) findViewById(R.id.btn_input_website);
        btn_input_website.setOnClickListener(this);
        et_input_website = (EditText) findViewById(R.id.et_input_website);
        web_view = (WebView) findViewById(R.id.web_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input_website:
                String website = et_input_website.getText().toString();

                if(website.equals("")){
                    Toast.makeText(website_open_activity.this, R.string.PLEASE_INPUTE_WEBSITE,
                            Toast.LENGTH_LONG).show();
                }else {
                    Intent it1 = new Intent(this, webview_activity.class);
                    it1.putExtra("WEBSITE", website);
                    startActivity(it1);
                    this.finish();
                }
                break;
            default:
                break;
        }
    }
}
