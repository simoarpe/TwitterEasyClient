package com.arpe.simon.twittereasyclient.otto;

public class RequestTokenRetrievedEvent {
	twitter4j.auth.AccessToken accessToken;

	public RequestTokenRetrievedEvent(twitter4j.auth.AccessToken accessToken) {
		this.accessToken = accessToken;

	}

	public twitter4j.auth.AccessToken getAccessToken() {
		return accessToken;
	}

}
