package com.arpe.simon.twittereasyclient.dialogs;

import com.simon.arpe.twittereasyclient.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;


public class BasicDialogFragment extends DialogFragment implements OnClickListener {

	private static final String ARG_TITLE = "arg_title";
	private static final String ARG_MESSAGE = "arg_message";
	private static final String ARG_POSITIVE_BUTTON_TEXT = "positive_button_text";

	public static BasicDialogFragment getInstance(String title, String message) {
		BasicDialogFragment frag = new BasicDialogFragment();

		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		args.putString(ARG_MESSAGE, message);
		args.putInt(ARG_POSITIVE_BUTTON_TEXT, android.R.string.ok);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String title = args.getString(ARG_TITLE);
		String message = args.getString(ARG_MESSAGE);
		int positiveButtonText = args.getInt(ARG_POSITIVE_BUTTON_TEXT);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = View.inflate(getActivity(), R.layout.button_dialog, null);

		TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
		titleView.setText(title);

		TextView messageView = (TextView) view.findViewById(R.id.dialog_message);
		messageView.setText(message);

		TextView okButton = (TextView) view.findViewById(R.id.dialog_button_positive);
		okButton.setText(getString(positiveButtonText));

			okButton.setOnClickListener(this);	

		builder.setView(view);

		Dialog dialog = builder.create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		return dialog;
	}


	@Override
	public void onClick(View v) {
		dismiss();
	}
}
