package com.arpe.simon.twittereasyclient.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkRequest {

	public static boolean hasNetworkConnection(Context context) {
		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

			if (activeNetwork != null) {
				return activeNetwork.isConnectedOrConnecting();
			}
		}
		return false;
	}

}
