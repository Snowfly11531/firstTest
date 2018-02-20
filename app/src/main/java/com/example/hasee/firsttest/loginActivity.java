package com.example.hasee.firsttest;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class loginActivity extends ActionBarActivity {
    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private Button login;//初始界面login按钮
    private Button register;//初始界面register按钮
    private View popregister;//注册界面
    private View popLogin;//登录界面
    /*
    * 注册界面控件
    * */
    private TextView registerNameText;
    private EditText registerName;
    private TextView registerPassText;
    private EditText registerPass;
    private Button  registerButton;
    private ImageView registerPic;
    private LinearLayout registerNameLayout;
    private LinearLayout registerPassLayout;
    private TextView tologin;
    /*
    * 登录界面控件
    * */
    private EditText loginName;
    private EditText loginPass;
    private Button loginButton;
    private TextView toregister;
    private ImageViewPlus loginPic;

    private Uri imageUri;
    private File Image;

    private loadDialog dialog;

    private static final int REGISTER_SUCCESS=1;
    private static final int REGISTER_FAIL=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case REGISTER_SUCCESS:
                    Toast.makeText(loginActivity.this,"register successfully",Toast.LENGTH_LONG).show();
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(loginActivity.this,"register failure",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    private void setPopLogin(){
        loginName=(EditText)popLogin.findViewById(R.id.loginname);
        loginPass=(EditText)popLogin.findViewById(R.id.loginpass);
        loginButton=(Button)popLogin.findViewById(R.id.loginbutton);
        toregister=(TextView) popLogin.findViewById(R.id.toregister);
        loginPic=(ImageViewPlus) popLogin.findViewById(R.id.loginpic);
    }
    private void setPopregister(){
        registerNameLayout=(LinearLayout)popregister.findViewById(R.id.registerNameLayout);
        registerPassLayout=(LinearLayout)popregister.findViewById(R.id.registerPassLayout);
        registerNameText=(TextView)popregister.findViewById(R.id.registerNameText);
        registerName=(EditText)popregister.findViewById(R.id.registerName);
        registerPassText=(TextView)popregister.findViewById(R.id.registerPassText);
        registerPass=(EditText)popregister.findViewById(R.id.registerPass);
        registerButton=(Button)popregister.findViewById(R.id.registerButton);
        registerPic=(ImageView)popregister.findViewById(R.id.registerPic);
        tologin=(TextView)popregister.findViewById(R.id.tologin);
    }
    private void torightend(final View view,final int durTime){
        TranslateAnimation animation=new TranslateAnimation(0,view.getMeasuredWidth(),0,0);
        animation.setDuration(durTime);
        view.startAnimation(animation);
    }
    private void starttoright(final View view, final int durTime){
                TranslateAnimation animation=new TranslateAnimation(-view.getMeasuredWidth(),0,0,0);
                animation.setDuration(durTime);
                view.setAnimation(animation);
    }
    private void starttoleft(final View view, final int durTime){
                TranslateAnimation animation=new TranslateAnimation(view.getMeasuredWidth(),0,0,0);
                animation.setDuration(durTime);
                view.setAnimation(animation);
    }
    private void toleftend(final View view, final int durTime){
        TranslateAnimation animation=new TranslateAnimation(0,view.getMeasuredWidth(),0,0);
        animation.setDuration(durTime);
        view.setAnimation(animation);
    }
    private void starttotop(final View view,final int durTime){
                TranslateAnimation animation=new TranslateAnimation(0,0,1000,0);
                animation.setDuration(durTime);
                view.setAnimation(animation);
    }
    private void totopend(final View view,final int durTime){
                TranslateAnimation animation=new TranslateAnimation(0,0,0,view.getMeasuredHeight());
                animation.setDuration(durTime);
                view.setAnimation(animation);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout=(LinearLayout)findViewById(R.id.pop);
        layoutInflater=LayoutInflater.from(this);
        login =(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        popregister=layoutInflater.inflate(R.layout.pop_register,null);
        popLogin=layoutInflater.inflate(R.layout.pop_login,null);
        setPopregister();
        setPopLogin();
        loginName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginPic.setVisibility(View.VISIBLE);
                    }
                },0);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torightend(login,600);
                torightend(register,600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.removeAllViews();
                        linearLayout.addView(popregister);
                        starttotop(popregister,800);
                    }
                },600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerPassText.setVisibility(View.VISIBLE);
                        registerNameText.setVisibility(View.VISIBLE);
                        registerPic.setVisibility(View.VISIBLE);
                        starttoright(registerNameText,800);
                        starttoright(registerPassText,800);
                        starttoleft(registerPic,800);
                    }
                },1400);
            }
        });
        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torightend(popLogin,600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.removeAllViews();
                        linearLayout.addView(popregister);
                        starttotop(popregister,800);
                    }
                },600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerPassText.setVisibility(View.VISIBLE);
                        registerNameText.setVisibility(View.VISIBLE);
                        registerPic.setVisibility(View.VISIBLE);
                        starttoright(registerNameText,800);
                        starttoright(registerPassText,800);
                        starttoleft(registerPic,800);
                    }
                },1400);
            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torightend(popregister,600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerPassText.setVisibility(View.INVISIBLE);
                        registerNameText.setVisibility(View.INVISIBLE);
                        registerPic.setVisibility(View.INVISIBLE);
                        linearLayout.removeAllViews();
                        linearLayout.addView(popLogin);
                        starttotop(popLogin,800);
                    }
                },600);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torightend(login,600);
                torightend(register,600);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.removeAllViews();
                        linearLayout.addView(popLogin);
                        starttotop(popLogin,800);
                    }
                },600);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        registerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    registerNameLayout.setBackgroundResource(R.drawable.onfocus);
                }else{
                    registerNameLayout.setBackgroundResource(R.drawable.disfocus);
                }
            }
        });
        registerPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    registerPassLayout.setBackgroundResource(R.drawable.onfocus);
                }else{
                    registerPassLayout.setBackgroundResource(R.drawable.disfocus);
                }
            }
        });
        registerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDialog.Builder builder=new loginDialog.Builder(loginActivity.this);
                builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       takephoto();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("从相册中选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPic();
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        register();
            }
        });
        loginName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                getavatar();}
            }
        });
    }
    private void getPic(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT,null);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }
    private void getavatar(){
        String name=loginName.getText().toString();
        if(name.length()>0) {
            String url = "http://192.168.1.110/webgis/download.php?";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("name", name);
            String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
            /*client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Toast.makeText(loginActivity.this,"connect fail",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    Toast.makeText(loginActivity.this,s,Toast.LENGTH_LONG).show();
                }
            });*/
            client.post(url, params, new BinaryHttpResponseHandler(allowedContentTypes) {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    loginPic.setImageBitmap(bitmap);
                    loginPic.setBorder(6);
                    loginPic.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(loginActivity.this,"connect fail",Toast.LENGTH_LONG).show();
                }
            });
        }
    }//获得头像
    private void takephoto(){
        Image=new File(getExternalCacheDir(),"avatar.jpg");
        try{
            if(Image.exists()){
                Image.delete();
            }
            Image.createNewFile();
        }catch (Exception  e){
            e.printStackTrace();
        }
        imageUri=Uri.fromFile(Image);
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,0);
    }//拍照

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent intent;
        switch (requestCode){
            case 0:
                intent =new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri,"image/*");
                intent.putExtra("scale",true);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("outputX",300);
                intent.putExtra("outputY",300);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,2);
                break;
            case 1:
                Uri uri=data.getData();
                String str=ContentUriUtil.getRightPath(loginActivity.this,uri);
                Toast.makeText(loginActivity.this,str,Toast.LENGTH_LONG).show();
                intent =new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri,"image/*");
                intent.putExtra("scale",true);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("outputX",300);
                intent.putExtra("outputY",300);
                Image=new File(getExternalCacheDir(),"avatar.jpg");
                try{
                    if(Image.exists()){
                        Image.delete();
                    }
                    Image.createNewFile();
                }catch (Exception  e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(Image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,2);
                break;
            case 2:
                try {
                    Image=new File(getExternalCacheDir(),"avatar.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    registerPic.setImageBitmap(bitmap);
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private void register(){
        String name=registerName.getText().toString();
        String password=registerPass.getText().toString();
        if(name.length()<=0){
            Toast.makeText(loginActivity.this,"请填写用户名",Toast.LENGTH_LONG).show();
        }else if(name.length()<0){
            Toast.makeText(loginActivity.this,"请填写密码",Toast.LENGTH_LONG).show();
        }else if(Image==null){
            Toast.makeText(loginActivity.this,"拍张照片吧",Toast.LENGTH_LONG).show();
        }else {
            String url = "http://192.168.1.110/webgis/phoneregister.php?";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            try {
                params.put("attach", Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            params.put("name",name);
            params.put("password",password);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    loadDialog.Builder builder=new loadDialog.Builder(loginActivity.this);
                    builder.setLoadTitle("Verification");
                    builder.setLoadMessage("loading...");
                    dialog=builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    dialog.dismiss();
                    Toast.makeText(loginActivity.this, "adads", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    dialog.dismiss();
                    Toast.makeText(loginActivity.this, s, Toast.LENGTH_LONG).show();
                    if(s.equals("save successfully")){
                        Intent intent=new Intent(loginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
    private void login(){
        String username=loginName.getText().toString();
        String password=loginPass.getText().toString();
        if(username.length()<=0){
            Toast.makeText(loginActivity.this,"请填写用户名",Toast.LENGTH_LONG).show();
        }else if(password.length()<=0){
            Toast.makeText(loginActivity.this,"请填写密码",Toast.LENGTH_LONG).show();
        }else{
            String url="http://192.168.1.110/webgis/phonelogin.php?";
            AsyncHttpClient client=new AsyncHttpClient();
            RequestParams params=new RequestParams();
            params.put("name",username);
            params.put("password",password);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    loadDialog.Builder builder=new loadDialog.Builder(loginActivity.this);
                    builder.setLoadTitle("Registration");
                    builder.setLoadMessage("loading...");
                    dialog=builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Toast.makeText(loginActivity.this,s,Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    dialog.dismiss();
                     Toast.makeText(loginActivity.this,s,Toast.LENGTH_LONG).show();
                    if(s.equals("success")){
                        Intent intent=new Intent(loginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
}