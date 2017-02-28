package near2u.com.near2u.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import near2u.com.near2u.R;

public class SessionManager {

    private static String SHARED_PREFERENCES_PLACE = "near2fv261";

    public static void addToSession(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_PLACE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void addIntToSession(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_PLACE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getFromSession(Context context, String sessionKey) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_PLACE, 0);
        return pref.getString(sessionKey, "");
    }

    public static int getIntFromSession(Context context, String sessionKey) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_PLACE, 0);
        return pref.getInt(sessionKey, 0);
    }
}
