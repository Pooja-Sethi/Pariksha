package com.minorproject.pariksha;

import android.widget.RadioButton;

//modal class for storing information registered by the user
public class addInformation {
    String name ;
    String email;
    String password;
    String id;
    String radiobtn;

    public addInformation(){

    }

    public addInformation(String id, String name, String email, String password, String radiobtn) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.radiobtn = radiobtn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRadiobtn(String radiobtn) {
        this.radiobtn = radiobtn;
    }

    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

     public String getRadiobtn(){
        return radiobtn;
}
}
