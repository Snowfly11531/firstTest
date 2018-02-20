package com.example.hasee.firsttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by hasee on 2018/1/13.
 */

public class friendsAdapter extends RecyclerView.Adapter<friendsAdapter.ViewHolder>{
    private List<friends> friendsList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageViewPlus friendsListPic;
        TextView friendsListName;
        TextView friendsListAdd;
        public ViewHolder(View view){
            super(view);
            friendsListPic=(ImageViewPlus)view.findViewById(R.id.friendsListPic);
            friendsListName=(TextView)view.findViewById(R.id.friendsListName);
            friendsListAdd=(TextView)view.findViewById(R.id.friendsListAdd);
        }
    }
    public friendsAdapter(Context context,List<friends> friendsList){
        this.friendsList=friendsList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.friendslist,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final friends fri=friendsList.get(position);
        holder.friendsListName.setText(fri.getUsername());
        holder.friendsListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mPath = Environment.getExternalStorageDirectory().getPath()+ "/njfu/friends";
                    File file = new File(mPath);
                    if(!file.exists()){
                        file.mkdir();
                    }
                    file=new File(mPath+"/friends.txt");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file,true);
                    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                    bufferedWriter.write(fri.getUsername()+";");
                    
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    Toast.makeText(context,"读写成功",Toast.LENGTH_LONG).show();

                    friendsList.remove(position);
                    notifyItemRemoved(position);

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context,"读写失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("url",fri.getUrl());
        Toast.makeText(context,fri.getUrl(),Toast.LENGTH_LONG).show();
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
        client.post("http://192.168.1.110/webgis/friendsAvatar.php?", params, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.friendsListPic.setBorder(0);
                holder.friendsListPic.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                //Toast.makeText(context,"get"+Integer.toString(position)+" fail",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
