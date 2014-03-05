package com.arpe.simon.twittereasyclient.core;

import com.arpe.simon.twittereasyclient.dialogs.LoadingDialogFragment;

import android.app.Dialog;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class OAuthWebViewClient extends WebViewClient {

	private final LoadingDialogFragment loadingDialogFragment;
	private final WebView webViewOAuth;
	private final Dialog dialog;
	private final String URL_TWITTER_OAUTH_VERIFIER;
	private final String TWITTER_CALLBACK_URL;

	public OAuthWebViewClient(LoadingDialogFragment loadingDialogFragment, WebView webViewOAuth, Dialog dialog, String urlOAuthVerifier, String callbackUrl) {
		this.loadingDialogFragment = loadingDialogFragment;
		this.webViewOAuth = webViewOAuth;
		this.dialog = dialog;
		this.URL_TWITTER_OAUTH_VERIFIER = urlOAuthVerifier;
		this.TWITTER_CALLBACK_URL = callbackUrl;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		loadingDialogFragment.dismiss();
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		webViewOAuth.setVisibility(View.VISIBLE);
		loadingDialogFragment.dismiss();

		// check if the login was successful and the access token returned
		if (url.contains(TWITTER_CALLBACK_URL)) {
			Uri uri = Uri.parse(url);
			String oauthVerifier = uri.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

			if (oauthVerifier == null) {
				dialog.dismiss();
				return true;
			}
			runOAuthAccessTokenTask(oauthVerifier);
			return true;
		}
		return false;
	}

	protected abstract void runOAuthAccessTokenTask(String oauthVerifier);

}