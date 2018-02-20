package com.example.hasee.firsttest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import static com.example.hasee.firsttest.R.id.dialog;

/**
 * Created by hasee on 2018/1/10.
 */

public class loginDialog extends Dialog {
    public loginDialog(Context context,int theme){
        super(context,theme);
    }
    public static class Builder{
        private Context context;
        private Button takephotoButton;
        private Button getpictureButton;
        private String takephotoButtonText;
        private DialogInterface.OnClickListener takephotoButtonListener;
        private String getpictureButtonText;
        private DialogInterface.OnClickListener getpictureButtonListener;
        public Builder(Context context){
            this.context=context;
        }
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.takephotoButtonText=positiveButtonText;
            this.takephotoButtonListener=listener;
            return this;
        }
        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener){
            this.getpictureButtonText=negativeButtonText;
            this.getpictureButtonListener=listener;
            return this;
        }
        public loginDialog create(){
            LayoutInflater inflater=LayoutInflater.from(context);
            final loginDialog dialog=new loginDialog(context,R.style.Dialog);
            View view=inflater.inflate(R.layout.logindialog,null);
            dialog.addContentView(view, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
            lp.y=20;
            dialog.getWindow().setAttributes(lp);
            takephotoButton=(Button)view.findViewById(R.id.takephoto);
            getpictureButton=(Button)view.findViewById(R.id.getpicture);
            takephotoButton.setText(takephotoButtonText);
            getpictureButton.setText(getpictureButtonText);
            takephotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takephotoButtonListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                }
            });
            getpictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getpictureButtonListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                }
            });
            dialog.setContentView(view);
            return dialog;
        }
    }
}
