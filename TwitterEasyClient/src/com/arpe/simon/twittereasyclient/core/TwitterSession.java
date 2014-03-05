package com.arpe.simon.twittereasyclient.core;

public class TwitterSession {
	private final String oauthToken;
	private final String oauthSecret;
	private final boolean isUserLoggedIn;

	public TwitterSession(String oauthToken, String oauthSecret, boolean isUserLoggedIn) {
		this.oauthToken = oauthToken;
		this.oauthSecret = oauthSecret;
		this.isUserLoggedIn = isUserLoggedIn;
	}

	public String getOAuthToken() {
		return oauthToken;
	}

	public String getOAuthSecret() {
		return oauthSecret;
	}

	public boolean isUserLoggedIn() {
		return isUserLoggedIn;
	}
}
