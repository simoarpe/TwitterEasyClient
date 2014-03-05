package com.arpe.simon.twittereasyclient.asyncTask;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

public abstract class MultiThreadAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {

	@SuppressLint("NewApi")
	public AsyncTask<X, Y, Z> executeTask(X... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		}
		
		return execute(params);
	}
}

