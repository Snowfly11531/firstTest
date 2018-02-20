package com.example.hasee.firsttest;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.Renderer;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.Symbol;
import com.example.hasee.firsttest.tdtutil.TianDiTuTiledMapServiceLayer;
import com.example.hasee.firsttest.tdtutil.TianDiTuTiledMapServiceType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class MainActivity extends Activity {
    public TianDiTuTiledMapServiceLayer t_vec;
    public TianDiTuTiledMapServiceLayer t_cva;
    MapView mMapView;
    ArcGISTiledMapServiceLayer tileLayer;
    FeatureLayer featureLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button news=(Button)findViewById(R.id.news);
        final Button dialogClick=(Button)findViewById(R.id.dialog);
        final WebView webView=(WebView)findViewById(R.id.web_view);
        final Button nextmap=(Button)findViewById(R.id.nextmap);
        final Button toAddFriends=(Button)findViewById(R.id.toAddFriends);
        toAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,addFriendActivity.class);
                startActivity(intent);
            }
        });
        nextmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com/");

        dialogClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDialog.Builder builder=new loadDialog.Builder(MainActivity.this);
                builder.setLoadTitle("Verification");
                builder.setLoadMessage("loading...");
                loadDialog dialog=builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TalkActivity.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openText();
                }else{
                    Toast.makeText(this,"未获取权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                break;
            default:
                break;
        }
    }*/
    public String getSDPath(){
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();//获取跟目录
        }
        return sdDir.toString();

    }

    private void openText(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String textPath=null;
                    Uri uri =data.getData();
                    String path=new ContentUriUtil().getRightPath(this,uri);
                    File file=new File(path);
                    try{
                        FileReader in=new FileReader(file);
                        Toast.makeText(this,"right",Toast.LENGTH_LONG).show();
                        char byt[]=new char[1024];
                        int len=in.read(byt);
                        String str=new String(byt,0,len);
                        FeatureLayer layer1=new FeatureLayer(new ShapefileFeatureTable(path));
                        //Toast.makeText(this,new String(byt,0,len),Toast.LENGTH_LONG).show();
                        TextView contentText=(TextView)findViewById(R.id.contentText);
                        contentText.setText(str);
                        contentText.setTextSize(20);
                    }catch (Exception e){
                       Toast.makeText(this,path,Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    //Toast.makeText(this,path,Toast.LENGTH_LONG).show();
                }
        }
    }
}

