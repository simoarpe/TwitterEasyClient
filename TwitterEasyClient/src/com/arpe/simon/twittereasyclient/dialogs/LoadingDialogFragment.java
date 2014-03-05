package com.arpe.simon.twittereasyclient.dialogs;

import com.simon.arpe.twittereasyclient.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class LoadingDialogFragment extends DialogFragment implements OnKeyListener {

	public static final String DIALOG_TAG_LOADING = "dialog_loading";
	private static final String ARG_MESSAGE = "arg_message";

	public static LoadingDialogFragment getInstance(String message) {
		LoadingDialogFragment frag = new LoadingDialogFragment();

		Bundle args = new Bundle();
		args.putString(ARG_MESSAGE, message);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String message = args.getString(ARG_MESSAGE);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(createView(message));
		builder.setOnKeyListener(this);

		Dialog dialog = builder.create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	private View createView(String message) {
		View layout = View.inflate(getActivity(), R.layout.loading_dialog, null);

		TextView text = (TextView) layout.findViewById(R.id.message);

		if (message != null) {
			text.setText(message);
		}
		return layout;
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getActivity().finish();
			return true;
		}

		return false;
	}
}
