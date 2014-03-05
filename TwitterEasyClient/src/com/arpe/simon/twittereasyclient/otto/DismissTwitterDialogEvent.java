package com.arpe.simon.twittereasyclient.otto;

public class DismissTwitterDialogEvent {
	String url;

	public DismissTwitterDialogEvent(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
