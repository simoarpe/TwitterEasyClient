package com.arpe.simon.twittereasyclient.dialogs;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arpe.simon.twittereasyclient.asyncTask.MultiThreadAsyncTask;
import com.arpe.simon.twittereasyclient.constants.Constants;
import com.arpe.simon.twittereasyclient.core.OAuthWebViewClient;
import com.arpe.simon.twittereasyclient.core.TwitterSession;
import com.arpe.simon.twittereasyclient.network.NetworkRequest;
import com.arpe.simon.twittereasyclient.otto.BusProvider;
import com.arpe.simon.twittereasyclient.otto.DismissTwitterDialogEvent;
import com.arpe.simon.twittereasyclient.otto.RequestTokenRetrievedEvent;
import com.arpe.simon.twittereasyclient.otto.ShowTwitterDialogEvent;
import com.arpe.simon.twittereasyclient.prefs.SocialMediaPrefs;
import com.simon.arpe.twittereasyclient.R;
import com.squareup.otto.Subscribe;

public class TwitterDialogFragment extends BaseDialogFragment {
	private static final String TAG = TwitterDialogFragment.class.getSimpleName();

	public static final String ITEM_URL = "item_url";
	public static final String TWITTER_MESSAGE = "twitter_message";

	private Button updateStatusButton;
	private EditText updateMessageEditText;
	private TextView updateLabelTextView;
	private TwitterSession session;

	private String twitterMessage = "";
	private String shareItemUrl = "";

	private LoadingDialogFragment loadingDialogFragment;
	private WebView webViewOAuth;
	private View mainView;
	private boolean isTwitterUserLoggedIn;
	private String status;
	
	
	private static String TWITTER_CONSUMER_KEY;
	private static String TWITTER_CONSUMER_SECRET;
	private static String TWITTER_CALLBACK_URL;
	private static String URL_TWITTER_OAUTH_VERIFIER;
	
	public static class Builder{
		private final String twitterMessage;
		private final String shareItemUrl;
		
		private String TWITTER_CONSUMER_KEY;
		private String TWITTER_CONSUMER_SECRET;
		private String TWITTER_CALLBACK_URL;
		private String URL_TWITTER_OAUTH_VERIFIER;
		
		public Builder(String twitterMessage, String shareItemUrl){
			this.twitterMessage = twitterMessage;
			this.shareItemUrl = shareItemUrl;
		}
		
		public Builder consumerKey(String consumerKey){
			this.TWITTER_CONSUMER_KEY = consumerKey;
			
			return this;
		}
		
		public Builder consumerSecret(String consumerSecret){
			this.TWITTER_CONSUMER_SECRET = consumerSecret;
			
			return this;
		}
		
		public Builder callbackUrl(String callbackUrl){
			this.TWITTER_CALLBACK_URL = callbackUrl;
			
			return this;
		}
		
		public Builder urlOAuth(String urlOAuth){
			this.URL_TWITTER_OAUTH_VERIFIER = urlOAuth;
			
			return this;
		}
		
		public TwitterDialogFragment build(){
			return new TwitterDialogFragment(this);
		}
		
	}
	
	
	public TwitterDialogFragment(Builder builder){	
		
		
		Bundle args = new Bundle();
		args.putString(TwitterDialogFragment.TWITTER_MESSAGE,
				builder.twitterMessage);
		args.putString(TwitterDialogFragment.ITEM_URL,
				builder.shareItemUrl);

		setArguments(args);
		
		TWITTER_CONSUMER_KEY = builder.TWITTER_CONSUMER_KEY;
		TWITTER_CONSUMER_SECRET = builder.TWITTER_CONSUMER_SECRET;
		TWITTER_CALLBACK_URL = builder.TWITTER_CALLBACK_URL;
		URL_TWITTER_OAUTH_VERIFIER = builder.URL_TWITTER_OAUTH_VERIFIER;
		
	}
	
	
//	public TwitterDialogFragment(String message, String url, String consKey, String consSec, String callUrl, String urlTwit){		
//		Bundle args = new Bundle();
//		args.putString(TwitterDialogFragment.TWITTER_MESSAGE,
//				message);
//		args.putString(TwitterDialogFragment.ITEM_URL,
//				url);
//
//		setArguments(args);
//		
//		Constants.TWITTER_CONSUMER_KEY = consKey;
//		Constants.TWITTER_CONSUMER_SECRET = consSec;
//		Constants.TWITTER_CALLBACK_URL = callUrl;
//		Constants.URL_TWITTER_OAUTH_VERIFIER = urlTwit;
//		
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(createDialogView());
		return builder.create();
	}

	private View createDialogView() {

		View v = View.inflate(getActivity(), R.layout.twitter_update_view, null);
		webViewOAuth = (WebView) v.findViewById(R.id.oauthView);
		mainView = v.findViewById(R.id.mainView);
		updateStatusButton = (Button) v.findViewById(R.id.btnUpdateStatus);

		Bundle args = getArguments();
		shareItemUrl = args.getString(ITEM_URL);
		twitterMessage = args.getString(TWITTER_MESSAGE);

		status = twitterMessage + " " + shareItemUrl;

		if (SocialMediaPrefs.isTwitterUserLoggedIn(getActivity().getBaseContext())) {
			isTwitterUserLoggedIn = true;
			updateStatusButton.setText(getString(R.string.tweet));
		} else {
			isTwitterUserLoggedIn = false;
			updateStatusButton.setText(getString(R.string.login));
		}

		updateStatusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (NetworkRequest.hasNetworkConnection(getActivity())) {
					showLoadingDialog();
					if (!isTwitterUserLoggedIn) {
						new TwitterLoginTask().executeTask();
					} else {
						new UpdateTwitterStatusTask(session).executeTask(status);
					}
				} else {
					showErrorDialog();
				}
			}
		});

		updateMessageEditText = (EditText) v.findViewById(R.id.txtUpdateStatus);
		updateMessageEditText.setText(status);
		updateLabelTextView = (TextView) v.findViewById(R.id.lblUpdate);
		session = SocialMediaPrefs.getTwitterSession(getActivity());
		return v;
	}

	private void showErrorDialog() {
		BasicDialogFragment errorDialog = BasicDialogFragment.getInstance(
				getString(R.string.error),
				getString(R.string.checkconnection));
		errorDialog.show(getFragmentManager(), BasicDialogFragment.class.getSimpleName());

	}

	private void showLoadingDialog() {
		loadingDialogFragment = LoadingDialogFragment.getInstance(getActivity().getString(
				R.string.wait));
		loadingDialogFragment.show(getFragmentManager(), LoadingDialogFragment.class.getSimpleName());
	}

	private void dismissLoadingDialog() {
		loadingDialogFragment.dismiss();
	}

	private static class UpdateTwitterStatusTask extends MultiThreadAsyncTask<String, Void, Object> {
		private final TwitterSession session;

		public UpdateTwitterStatusTask(TwitterSession session) {
			this.session = session;
		}

		@Override
		protected String doInBackground(String... args) {
			String status = args[0];

			if (Constants.LOGGING_ENABLED) {
				Log.d(TAG, "status: " + status);
			}
			twitter4j.Status response = null;
			try {
				twitter4j.conf.ConfigurationBuilder twitterBuilder = new ConfigurationBuilder();
				twitterBuilder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				twitterBuilder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

				AccessToken accessToken = new AccessToken(session.getOAuthToken(), session.getOAuthSecret());
				Twitter twitter = new TwitterFactory(twitterBuilder.build()).getInstance(accessToken);

				StatusUpdate statusUpdate = new StatusUpdate(status);

				response = twitter.updateStatus(statusUpdate);
				String responseText = response.getText();

				if (Constants.LOGGING_ENABLED) {
					Log.d(TAG, "response text :" + responseText);
				}
				BusProvider.getInstance().post(new DismissTwitterDialogEvent(responseText));

			} catch (TwitterException e) {
				if (Constants.LOGGING_ENABLED) {
					Log.e(TAG, e.getMessage());
				}
				BusProvider.getInstance().post(new DismissTwitterDialogEvent(null));
			}
			return null;
		}

	}

	@Subscribe
	public void dismissTwitterDialog(final DismissTwitterDialogEvent event) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dismissLoadingDialog();
				String file_url = event.getUrl();

				if (file_url != null) {
					Toast.makeText(getActivity(),
							getActivity().getResources().getString(R.string.status_update),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(),
							getActivity().getResources().getString(R.string.update_failure),
							Toast.LENGTH_SHORT).show();
				}
				TwitterDialogFragment.this.dismiss();
			}
		});

	}

	private static class TwitterLoginTask extends MultiThreadAsyncTask<Void, Void, Void> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... arg0) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			RequestToken requestToken = null;
			try {
				requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
			} catch (TwitterException e) {
				if (Constants.LOGGING_ENABLED) {
					Log.e(TAG, e.getMessage());
				}
			}

			BusProvider.getInstance().post(new ShowTwitterDialogEvent(requestToken, twitter));
			return null;
		}

	}

	@Subscribe
	public void showTwitterDialog(final ShowTwitterDialogEvent event) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final RequestToken requestToken = event.getRequestToken();
				final Twitter twitter = event.getTwitter();
				mainView.setVisibility(View.GONE);
				webViewOAuth.loadUrl(requestToken.getAuthenticationURL());

				OAuthWebViewClient oAuthWebViewClient = new OAuthWebViewClient(loadingDialogFragment, webViewOAuth,getDialog(), URL_TWITTER_OAUTH_VERIFIER,TWITTER_CALLBACK_URL) {

					@Override
					protected void runOAuthAccessTokenTask(String oauthVerifier) {
						new OAuthAccessTokenTask(twitter, requestToken).executeTask(oauthVerifier);

					}
				};

				webViewOAuth.setWebViewClient(oAuthWebViewClient);
				WebSettings webSettings = webViewOAuth.getSettings();
				webSettings.setJavaScriptEnabled(true);
			}
		});
	}

	private static class OAuthAccessTokenTask extends MultiThreadAsyncTask<String, Void, Object> {
		private final Twitter twitter;
		private final RequestToken requestToken;

		public OAuthAccessTokenTask(Twitter twitter, RequestToken requestToken) {
			this.twitter = twitter;
			this.requestToken = requestToken;
		}

		@Override
		protected AccessToken doInBackground(String... params) {
			String oauthVerifier = params[0];
			AccessToken accessToken = null;

			try {
				accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			} catch (TwitterException e) {
				if (Constants.LOGGING_ENABLED) {
					Log.e(TAG, "TwitterError: " + e.getErrorMessage());
				}
			}
			BusProvider.getInstance().post(new RequestTokenRetrievedEvent(accessToken));
			return null;

		}
	}

	@Subscribe
	public void onRequestTokenRetrieved(final RequestTokenRetrievedEvent event) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AccessToken accessToken = event.getAccessToken();
				if (accessToken != null) {

					TwitterSession newSession = new TwitterSession(accessToken.getToken(),
							accessToken.getTokenSecret(), true);
					SocialMediaPrefs.saveTwitterSession(getActivity(), newSession);

					if (Constants.LOGGING_ENABLED) {
						Log.d(TAG, accessToken.getToken());
					}

					webViewOAuth.setVisibility(View.GONE);
					mainView.setVisibility(View.VISIBLE);
					updateLabelTextView.setVisibility(View.VISIBLE);
					updateMessageEditText.setVisibility(View.VISIBLE);
					updateStatusButton.setVisibility(View.VISIBLE);
					showLoadingDialog();
					new UpdateTwitterStatusTask(newSession).executeTask(status);
				}
			}
		});
	}

}
