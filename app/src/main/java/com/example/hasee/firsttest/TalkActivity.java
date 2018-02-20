package com.example.hasee.firsttest;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class TalkActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.talkLayout);
        final Button add=(Button)findViewById(R.id.add);
        final String str="Hello World";
        addView(this,linearLayout,0,str);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=(EditText)findViewById(R.id.isay);
                String str1=editText.getText().toString();
                addView(TalkActivity.this,linearLayout,1,str1);
                editText.setText("");
                addView(TalkActivity.this,linearLayout,0,str);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ScrollView scrollView=(ScrollView)findViewById(R.id.talkScroll);
                        int offset=linearLayout.getMeasuredHeight()-scrollView.getHeight();
                        if(offset<0)offset=0;
                        scrollView.scrollTo(0,offset);
                    }
                });
            }
        });
    }
    public void addView(Context context,LinearLayout LL,int i,String str){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View textLayout;
        TextView textView;
        if(i==0){
            textLayout=layoutInflater.inflate(R.layout.text_layout,null);
            textView=(TextView)textLayout.findViewById(R.id.lefttext);
        }else{
            textLayout=layoutInflater.inflate(R.layout.text_right_layout,null);
            textView=(TextView)textLayout.findViewById(R.id.righttext);
        }
        textView.setText(str);
        LL.addView(textLayout);
    }


}
