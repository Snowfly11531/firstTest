package com.example.hasee.firsttest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Environment;
import android.os.Bundle;
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

import java.io.FileNotFoundException;

public class MapActivity extends Activity {
    MapView mMapView;
    ArcGISTiledMapServiceLayer tileLayer;
    FeatureLayer featureLayer;

    // 天地图
    /**
     * 矢量地图
     */
    public TianDiTuTiledMapServiceLayer t_vec;
    /**
     * 矢量标注
     */
    public TianDiTuTiledMapServiceLayer t_cva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        mMapView=(MapView)findViewById(R.id.map);
//        tileLayer = new ArcGISTiledMapServiceLayer(
//                "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
//
//       mMapView.addLayer(tileLayer);
// 天地图 矢量
        t_vec = new TianDiTuTiledMapServiceLayer(
                TianDiTuTiledMapServiceType.VEC_C);
        mMapView.addLayer(t_vec);
        t_cva = new TianDiTuTiledMapServiceLayer(
                TianDiTuTiledMapServiceType.CVA_C);
        mMapView.addLayer(t_cva);


        //  Toast.makeText(this,mMapView.getSpatialReference().getID(),Toast.LENGTH_LONG).show();

        String shpPath=getSDPath()+ "/datas/bou2_4p.shp";
        //String shpPath="/mnt/sdcard/download/bou2_4p.shp";
        Toast.makeText(this,shpPath,Toast.LENGTH_LONG).show();
        Symbol symbol=new SimpleFillSymbol(Color.BLUE);
        Renderer renderer=new SimpleRenderer(symbol);
        try {
            ShapefileFeatureTable shapefileFeatureTable=new ShapefileFeatureTable(shpPath);
            featureLayer=new FeatureLayer(shapefileFeatureTable);
            Toast.makeText(this,shpPath,Toast.LENGTH_LONG).show();
            featureLayer.setRenderer(renderer);
            mMapView.addLayer(featureLayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

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

}
