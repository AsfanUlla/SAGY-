package ga.asfanulla.shadier.SubClass;

/**
 * Created by asfan on 9/19/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class SaveSharedPreference {
    static final String PREF_USER_NAME= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }


    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserPrefLoc(Context ctx, ArrayList<String> loc)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("loc", loc.toString());
        editor.commit();
    }


    public static String getUserPrefLoc(Context ctx)
    {
        return getSharedPreferences(ctx).getString("loc", "");
    }

    public static void setUserPrefLoc2(Context ctx, String loc)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("loc2", loc.toString());
        editor.commit();
    }


    public static String getUserPrefLoc2(Context ctx)
    {
        return getSharedPreferences(ctx).getString("loc2", "");
    }

    public static void setUserLang(Context ctx, String ln){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("ln", ln);
        editor.commit();
    }

    public static String getUserLang(Context ctx){
        return getSharedPreferences(ctx).getString("ln","");
    }

    public static void clearUserData(Context ctx)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();

    }

}
