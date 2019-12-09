package com.example.medical.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREF_NAME = "findpref";
    private static AppPreferences sInstance;
    private final SharedPreferences mPref;

    public static final String KEY_USERID = "user_id";
    public static final String KEY_FNAME = "firstname";
    public static final String KEY_LNAME = "user_name";
    public static final String KEY_MOB = "lastname";
    public static final String KEY_MAILID = "user_mail_id";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ANS = "user_ans";
    public static final String KEY_PASS = "user_pass";
    public static final String KEY_PLACE = "user_pass";


    private AppPreferences(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppPreferences(context);
        }
        return sInstance;
    }

    private enum Keys {
        IS_LOGGEDIN("loggedin"), IS_BACKGROUND("is_background"), USER_TYPE("user_type");
        private String label;

        private Keys(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public void setIsLoggedIn(boolean value) {
        setBooleanValue(Keys.IS_LOGGEDIN.getLabel(), value);
    }

    public boolean getIsLoggedIn(boolean defaultValue) {
        return getBooleanValue(Keys.IS_LOGGEDIN.getLabel(), defaultValue);
    }

    public void setUserType(String value) {
        setStringValue(Keys.USER_TYPE.getLabel(), value);
    }

    public String getUserType(String defaultValue) {
        return getStringValue(Keys.USER_TYPE.getLabel(), defaultValue);
    }

    public void setIsBackground(boolean value) {
        setBooleanValue(Keys.IS_BACKGROUND.getLabel(), value);
    }

    public boolean getIsBackground(boolean defaultValue) {
        return getBooleanValue(Keys.IS_BACKGROUND.getLabel(), defaultValue);
    }

    public void LoginSession(String user_name, String lastname, String mob, String user_mail_id, String address,
                             String user_ans, String user_pass, String usertype, String user_id,String place) {
        setStringValue(KEY_USERID, user_id);
        setStringValue(KEY_FNAME, user_name);
        setStringValue(KEY_LNAME, lastname);
        setStringValue(KEY_MAILID, user_mail_id);
        setStringValue(KEY_ADDRESS, address);
        setStringValue(KEY_MOB, mob);
        setStringValue(KEY_PASS, user_pass);
        setStringValue(KEY_PLACE, place);
        setStringValue(Keys.USER_TYPE.getLabel(), usertype);
    }

    public void setBooleanValue(String key, boolean value) {
        mPref.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanValue(String key, boolean _default) {
        return mPref.getBoolean(key, _default);
    }

    public void setStringValue(String key, String value) {
        mPref.edit().putString(key, value).commit();
    }

    public String getStringValue(String key, String _default) {
        return mPref.getString(key, _default);
    }

    public void setLongValue(String key, long value) {
        mPref.edit().putLong(key, value).commit();
    }

    public long getLongValue(String key, long _default) {
        return mPref.getLong(key, _default);
    }

    public void remove(String key) {
        mPref.edit().remove(key).commit();
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }

}
