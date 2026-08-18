package com.example.myfirsttimer.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "church_session";
    private static final String KEY_USHER_ID = "logged_in_usher_id";
    private static final String KEY_SERVICE_TYPE = "service_type";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void setLoggedInUsherId(long usherId) {
        prefs.edit().putLong(KEY_USHER_ID, usherId).apply();
    }

    public long getLoggedInUsherId() {
        return prefs.getLong(KEY_USHER_ID, -1L);
    }

    public boolean isLoggedIn() {
        return getLoggedInUsherId() != -1L;
    }

    public void setServiceType(String serviceType) {
        prefs.edit().putString(KEY_SERVICE_TYPE, serviceType).apply();
    }

    public String getServiceType() {
        return prefs.getString(KEY_SERVICE_TYPE, null);
    }

    public boolean hasServiceType() {
        return getServiceType() != null;
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_USHER_ID)
                .remove(KEY_SERVICE_TYPE)
                .apply();
    }
}
