package com.example.testproject;

import com.arpe.simon.twittereasyclient.dialogs.TwitterDialogFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends FragmentActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		TwitterDialogFragment twitterDialog = new TwitterDialogFragment.Builder("message","url.com") //
		.callbackUrl("http://www.website.com") //
		.consumerKey("XXXXXXXXXXXXXXXXXXXXXX") //
		.consumerSecret("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX") //
		.urlOAuth("oauth_verifier") //
		.build();
		
		twitterDialog.show(getSupportFragmentManager(), TwitterDialogFragment.class.getSimpleName());
		
	}

}
