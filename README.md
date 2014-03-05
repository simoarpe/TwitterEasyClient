TwitterEasyClient
=================

An Easy Twitter Client for Android 


## How-to in 4 simple steps

1.
Put these permissions in your manifest:
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

2.
Make sure to use TwitterEasyClient as library project

3.
Set in project build target Android 4.3 (API 18)

4.
 Use TwitterEasyClient from your activity:

 			//setup
 			TwitterDialogFragment twitterDialog = new TwitterDialogFragment.Builder("message","url.com") //
			.callbackUrl("http://www.website.com") //
			.consumerKey("XXXXXXXXXXXXXXXXXXXXXX") //
			.consumerSecret("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX") //
			.urlOAuth("oauth_verifier") //
			.build();

			//show the dialog
			twitterDialog.show(getSupportFragmentManager(), TwitterDialogFragment.class.getSimpleName());


Enjoy !


Check the example in testproject.

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


Copyright 2014 Simone Arpe


Copyright 2014 GitHub Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


## Acknowledgements

TwitterEasyClient uses these great open-source libraries from the Android dev community:

* [Twitter4j](https://github.com/yusuke/twitter4j) Twitter4J is an open-sourced, mavenized and Google App Engine safe Java library for the Twitter API which is released under the Apache License 2.0. 
http://twitter4j.org/,
* [Otto](https://github.com/square/otto) An enhanced Guava-based event bus with emphasis on Android support.


## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/simoarpe/TwitterEasyClient/pulls).

Any contributions, large or small, major features, bug fixes, additional
language translations, unit/integration tests are welcomed and appreciated
but will be thoroughly reviewed and discussed.

I hope this helps you in building your next android app.
