package com.arpe.simon.twittereasyclient.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

	protected static final String PREFERENCE_KEY = Prefs.class.getCanonicalName();

	protected static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
	}
}
