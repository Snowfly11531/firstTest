package com.example.hasee.firsttest;

/**
 * Created by hasee on 2018/1/13.
 */

public class friends {
    private String url;
    private String username;
    public friends(String url,String username){
        this.url=url;
        this.username=username;
    }
    public String getUrl(){
        return this.url;
    }
    public String getUsername(){
        return this.username;
    }
}
