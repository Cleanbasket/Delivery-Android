package kr.co.cleanbasket.cleanbasketdelivererandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by gingeraebi on 2016. 6. 7..
 */
public class SharedPreferenceBase {
    private static Context context;

    public static SharedPreferenceBase init(Context context) {
        setContext(context);
        return new SharedPreferenceBase();
    }

    public static void setContext(Context context) {
        SharedPreferenceBase.context = context;
    }

    public static Set<String> getSharedPreference(String prefName, Set<String> defValues) {
        return context.getSharedPreferences(prefName,Context.MODE_PRIVATE).getStringSet(prefName, defValues);
    }

    public static void putSharedPreference(String prefName, Set<String> valeus) {
        context.getSharedPreferences(prefName,Context.MODE_PRIVATE).edit().putStringSet(prefName, valeus).commit();
    }

}
