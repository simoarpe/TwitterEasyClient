package com.arpe.simon.twittereasyclient.otto;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class ShowTwitterDialogEvent {
	RequestToken requestToken;
	Twitter twitter;

	public ShowTwitterDialogEvent(RequestToken requestToken, Twitter twitter) {
		this.requestToken = requestToken;
		this.twitter = twitter;
	}

	public RequestToken getRequestToken() {
		return requestToken;
	}

	public Twitter getTwitter() {
		return twitter;
	}

}
