package com.example.hasee.firsttest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.liao.GifView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addFriendActivity extends ActionBarActivity {
    private List<friends> friendsList=new ArrayList<>();
    private EditText searchFriendsEditText;
    private Button searchFriendsButton;
    private RecyclerView recyclerView;
    private GifView jiazaiFriendsGifView;
    private TextView jiazaiFriendsTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        jiazaiFriendsGifView=(GifView)findViewById(R.id.jiazaiFriendsGifView);
        jiazaiFriendsTextview=(TextView)findViewById(R.id.jiazaiFriendsTextview);
        searchFriendsButton=(Button)findViewById(R.id.searchFriendsButtton);
        searchFriendsEditText=(EditText)findViewById(R.id.searchFriendsEditText);
        recyclerView=(RecyclerView)findViewById(R.id.searchFriendsList);
        LinearLayoutManager manager=new LinearLayoutManager(addFriendActivity.this);
        recyclerView.setLayoutManager(manager);
        searchFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=searchFriendsEditText.getText().toString();
                initFriendsList(name);
            }
        });
    }
    private void initFriendsList(final String name){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("name",name);
        client.post("http://192.168.1.110/webgis/getFriendsList.php?", params,new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                jiazaiFriendsGifView.setVisibility(View.VISIBLE);
                jiazaiFriendsGifView.setGifImage(R.drawable.load1);
                jiazaiFriendsTextview.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                friendsList.clear();
                jiazaiFriendsGifView.setVisibility(View.GONE);
                JSONArray nameArray;
                JSONArray urlArray;
                int length;
                try{
                    nameArray=response.getJSONArray(0);
                    urlArray=response.getJSONArray(1);
                }catch (Exception e){
                    e.printStackTrace();
                    nameArray=null;
                    urlArray=null;
                }
                if(nameArray!=null&&urlArray!=null){
                    length=nameArray.length();
                    friends[] fris=new friends[length];
                    for(int i=0;i<length;i++){
                        try {
                            String name = nameArray.getString(i);
                            //Toast.makeText(addFriendActivity.this,name,Toast.LENGTH_LONG).show();
                            String url = urlArray.getString(i);
                           // Toast.makeText(addFriendActivity.this,url,Toast.LENGTH_LONG).show();
                            fris[i] = new friends(url, name);
                            friendsList.add(fris[i]);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                if(friendsList.size()==0){
                    jiazaiFriendsTextview.setText("未找到用户");
                    jiazaiFriendsGifView.setVisibility(View.GONE);
                    jiazaiFriendsTextview.setVisibility(View.VISIBLE);
                }else {
                    friendsAdapter adapter = new friendsAdapter(addFriendActivity.this, friendsList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(addFriendActivity.this,"加载失败",Toast.LENGTH_LONG).show();
                jiazaiFriendsGifView.setVisibility(View.GONE);
                jiazaiFriendsTextview.setVisibility(View.VISIBLE);
            }
        });
    }
}
