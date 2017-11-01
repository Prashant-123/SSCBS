package com.cbs.sscbs.auth;

/**
 * Created by gauti on 04/10/17.
 */

public class Users {
    private  String email;
    private String name;
    private  String userid;


    public Users(String email, String name, String userid) {
        this.email = email;
        this.name = name;
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
