package com.example.britto.mygiftapplication.sessions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sambritto on 3/26/2018.
 */

public class LoginSession {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Emailk = "nameKey";
    public static final String UserName = "usenameKey";
    public static final String isLoggedIn = "isLoggedIn";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public LoginSession(Context context){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void createLoginSession( String email , String userName){
        editor.putString(Emailk,email);
        editor.putString(UserName,userName);
        editor.putBoolean(isLoggedIn , true);
        editor.commit();
    }

    public void sessionClear(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedInUser(){
        return sharedpreferences.getBoolean(isLoggedIn , false);
    }

    public String getUserName(){
        return sharedpreferences.getString(UserName,null);
    }

    public String getEmailk(){
        return sharedpreferences.getString(Emailk,null);
    }
}
