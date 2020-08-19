package com.aya.sakan.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelperImp implements PreferencesHelper {

    SharedPreferences preferences;
    Context context;
    private static PreferencesHelperImp instance;

    public static PreferencesHelperImp getInstance() {
        if (instance == null) {
            instance = new PreferencesHelperImp();
        }
        return instance;
    }

    private PreferencesHelperImp() {
        this.context = ContextApplication.getInstance();
    }


    @Override
    public String getAccountType() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getString(Constant.ACCOUNT_TYPE, null);
    }

    @Override
    public void setAccountType(String accountType) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constant.ACCOUNT_TYPE, accountType);
        editor.apply();
    }

    @Override
    public void removeAllValues() {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getLanguagePref() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getString(Constant.LANGUAGE_KEY, "en");
    }


    public void setLanguagePref(String localeKey) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constant.LANGUAGE_KEY, localeKey);
        editor.apply();
    }
}
