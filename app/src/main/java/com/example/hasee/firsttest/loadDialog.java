package com.example.hasee.firsttest;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.liao.GifView;

/**
 * Created by hasee on 2018/1/13.
 */

public class loadDialog extends Dialog {
    public loadDialog(Context context,int theme){super(context,theme);}
    public static class Builder{
        private String loadTitleText;
        private String loadMessageText;
        private GifView loadGif;
        private Context context;
        public Builder(Context context){this.context=context;}
        public void setLoadTitle(String loadTitleText) {
            this.loadTitleText=loadTitleText;
        }
        public void setLoadMessage(String loadMessageText){
            this.loadMessageText=loadMessageText;
        }
        public loadDialog create(){
            loadDialog dialog=new loadDialog(context,R.style.unclickDialog);
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.loaddialog,null);
            ((GifView)view.findViewById(R.id.loadGif)).setGifImage(R.drawable.load);
            ((TextView)view.findViewById(R.id.loadTitle)).setText(loadTitleText);
            ((TextView)view.findViewById(R.id.loadMessage)).setText(loadMessageText);
            dialog.addContentView(view,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(view);
            return dialog;
        }
    }
}
