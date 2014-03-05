package com.arpe.simon.twittereasyclient.prefs;

import com.arpe.simon.twittereasyclient.core.TwitterSession;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SocialMediaPrefs extends Prefs {
	private static final String TWITTER_OAUTH_TOKEN = "oauth_token";
	private static final String TWITTER_OAUTH_SECRET = "oauth_token_secret";
	private static final String TWITTER_IS_LOGGED_IN = "isTwitterLoggedIn";

	public static void saveTwitterSession(Context context, TwitterSession session) {
		Editor editor = getPrefs(context).edit();

		if (editor != null && session != null) {
			editor.putString(TWITTER_OAUTH_TOKEN, session.getOAuthToken());
			editor.putString(TWITTER_OAUTH_SECRET, session.getOAuthSecret());
			editor.putBoolean(TWITTER_IS_LOGGED_IN, session.isUserLoggedIn());
			editor.apply();
		}
	}

	public static boolean isTwitterUserLoggedIn(Context context) {
		return getPrefs(context).getBoolean(TWITTER_IS_LOGGED_IN, false);
	}

	public static TwitterSession getTwitterSession(Context context) {
		String oAuthToken = getPrefs(context).getString(TWITTER_OAUTH_TOKEN, "");
		String oAuthSecret = getPrefs(context).getString(TWITTER_OAUTH_SECRET, "");
		boolean isLoggedIn = getPrefs(context).getBoolean(TWITTER_IS_LOGGED_IN, false);

		return new TwitterSession(oAuthToken, oAuthSecret, isLoggedIn);
	}
}
