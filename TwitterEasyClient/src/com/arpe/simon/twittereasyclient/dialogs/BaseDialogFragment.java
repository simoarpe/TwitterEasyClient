package com.arpe.simon.twittereasyclient.dialogs;

import com.arpe.simon.twittereasyclient.otto.BusProvider;
import com.squareup.otto.Bus;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class BaseDialogFragment extends DialogFragment {
	
	private final Bus bus = BusProvider.getInstance();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		bus.unregister(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		bus.register(this);
	}
}
