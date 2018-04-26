package com.kmitl.tulaya.foodchecking;

/**
 * Created by phatipan on 19/4/2018 AD.
 */

public class CheckModel {
    String ckName;

    //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.

    private static CheckModel instance;

    public static CheckModel getInstance() {
        if (instance == null)
            instance = new CheckModel();
        return instance;
    }

    public CheckModel(){

    }

    public CheckModel(String ckName){
        this.ckName = ckName;
    }

    public String getCkName() {
        return ckName;
    }

    public void setCkName(String ckName) {
        this.ckName = ckName;
    }
}
