package com.kmitl.tulaya.foodchecking;

import android.content.Context;

/**
 * Created by phatipan on 19/4/2018 AD.
 */

public class UserModel {
    boolean isSelected;
    String userName;

    //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.

    private static UserModel instance;

    public static  UserModel getInstance() {
        if (instance == null)
            instance = new UserModel();
        return instance;
    }

    public UserModel(){

    }

    public UserModel(boolean isSelected, String userName) {
        this.isSelected = isSelected;
        this.userName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
