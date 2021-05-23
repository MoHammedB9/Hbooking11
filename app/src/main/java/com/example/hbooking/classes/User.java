package com.example.hbooking.classes;

import java.io.Serializable;

public class User implements Serializable {
   private String Email,pwd,username,phone,ville;
   public static User usr=null;
   public static String key;

    public User(){}

    public User(String email, String pwd, String phone, String username,String ville) {
        Email = email;
        this.pwd = pwd;
        this.phone = phone;
        this.username = username;
        this.ville = ville;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
