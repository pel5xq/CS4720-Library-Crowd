package com.example.librarycrowdsource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private final String TAG = "LIBRARYCROWD";

	// private TableLayout LibraryDatascrollView;
	Button submit_library_button;
	Button buttonAlderman;
	Button buttonClark;
	Button buttonClemons;
	Button buttonCommerce;
	Button buttonRice;
	Button buttonThornton;
	Button buttonWilsdorf;
	private TextView textView1;

	private final String clemonsArray[] = { "1", "2", "3", "4" };
	private final String aldermanArray[] = { "WestWing", "EastWing",
			"McGregor", "Cafe" };
	private final String thorntonArray[] = { "Stacks" };
	private final String clarkArray[] = { "WestWing", "EastWing", "Reading",
			"Stacks" };
	private final String commerceArray[] = { "CompLab", "4" };
	private final String riceArray[] = { "1", "2", "3", "4", "5" };
	private final String wilsdorfArray[] = { "Cafe" };

	// private final String

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView1 = (TextView) findViewById(R.id.libraryNameTextView);

		textView1.setOnClickListener(textView1Listener);

		buttonAlderman = (Button) findViewById(R.id.buttonAlderman);
		buttonClark = (Button) findViewById(R.id.buttonClark);
		buttonClemons = (Button) findViewById(R.id.buttonClemons);
		buttonCommerce = (Button) findViewById(R.id.buttonCommerce);
		buttonRice = (Button) findViewById(R.id.buttonRice);
		buttonThornton = (Button) findViewById(R.id.buttonThornton);
		buttonWilsdorf = (Button) findViewById(R.id.buttonWilsdorf);

		Log.d(TAG, "About to perform task");
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Alderman/timespan/60", "" + R.id.aldermanCrowdImage,
				"" + R.id.aldermanNoise, "" + R.id.aldermanSmallCrowdImage, ""
						+ R.id.aldermanSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Clark/timespan/60", "" + R.id.clarkCrowd, ""
				+ R.id.clarkNoise, "" + R.id.clarkSmallCrowdImage, ""
				+ R.id.clarkSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Clemons/timespan/60", "" + R.id.clemonsCrowd, ""
				+ R.id.clemonsNoise, "" + R.id.clemonsSmallCrowdImage, ""
				+ R.id.clemonsSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Commerce%20School/timespan/60", ""
				+ R.id.commerceCrowd, "" + R.id.commerceNoise, ""
				+ R.id.commerceSmallCrowdImage, ""
				+ R.id.commerceSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Rice/timespan/60", "" + R.id.riceCrowd, ""
				+ R.id.riceNoise, "" + R.id.riceSmallCrowdImage, ""
				+ R.id.riceSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Thornton/timespan/60", "" + R.id.thorntonCrowd, ""
				+ R.id.thorntonNoise, "" + R.id.thorntonSmallCrowdImage, ""
				+ R.id.thorntonSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Wilsdorf/timespan/60", "" + R.id.wilsdorfCrowd, ""
				+ R.id.wilsdorfNoise, "" + R.id.wilsdorfSmallCrowdImage, ""
				+ R.id.wilsdorfSmallNoiseImage);

		// Post Lib crowd and noise data
		Log.d(TAG, "About to set post button listener");
		((Button) findViewById(R.id.makePostButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						Intent intent = new Intent(MainActivity.this,
								PostActivity.class);
						startActivity(intent);
					}

				});

		// Search for study groups
		Log.d(TAG, "About to set search button listener");
		((Button) findViewById(R.id.studySearchButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						Intent intent = new Intent(MainActivity.this,
								StudySearchActivity.class);
						startActivity(intent);
					}

				});

		buttonAlderman.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonAlderman);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", aldermanArray);
				startActivity(intent);

			}

		});

		buttonClark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonClark);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", clarkArray);
				startActivity(intent);

			}

		});

		buttonClemons.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonClemons);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", clemonsArray);
				startActivity(intent);

			}

		});

		buttonCommerce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonCommerce);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", commerceArray);
				startActivity(intent);

			}

		});

		buttonRice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonRice);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", riceArray);
				startActivity(intent);

			}

		});

		buttonThornton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonThornton);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", thorntonArray);
				startActivity(intent);

			}

		});

		buttonWilsdorf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView lib = (TextView) findViewById(R.id.buttonWilsdorf);
				Intent intent = new Intent(MainActivity.this,
						LibraryInfoActivity.class);
				intent.putExtra("Library", lib.getText().toString());
				intent.putExtra("array", wilsdorfArray);
				startActivity(intent);

			}

		});
	}

	// for testing purposes
	public OnClickListener textView1Listener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(MainActivity.this,
					LibraryInfoActivity.class);
			intent.putExtra("Library", textView1.getText().toString());
			textView1.setText(textView1.getText().toString());
			startActivity(intent);
		}
	};

	// private void updateLibraryDatascrollView(String )

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onResume() {
		super.onResume();
		reloadData();
	}

	private void reloadData() {
		// Intent i = new Intent(getBaseContext(), MainActivity.class);
		// startActivity(i);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Alderman/timespan/60", "" + R.id.aldermanCrowdImage,
				"" + R.id.aldermanNoise, "" + R.id.aldermanSmallCrowdImage, ""
						+ R.id.aldermanSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Clark/timespan/60", "" + R.id.clarkCrowd, ""
				+ R.id.clarkNoise, "" + R.id.clarkSmallCrowdImage, ""
				+ R.id.clarkSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Clemons/timespan/60", "" + R.id.clemonsCrowd, ""
				+ R.id.clemonsNoise, "" + R.id.clemonsSmallCrowdImage, ""
				+ R.id.clemonsSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Commerce%20School/timespan/60", ""
				+ R.id.commerceCrowd, "" + R.id.commerceNoise, ""
				+ R.id.commerceSmallCrowdImage, ""
				+ R.id.commerceSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Rice/timespan/60", "" + R.id.riceCrowd, ""
				+ R.id.riceNoise, "" + R.id.riceSmallCrowdImage, ""
				+ R.id.riceSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Thornton/timespan/60", "" + R.id.thorntonCrowd, ""
				+ R.id.thorntonNoise, "" + R.id.thorntonSmallCrowdImage, ""
				+ R.id.thorntonSmallNoiseImage);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Wilsdorf/timespan/60", "" + R.id.wilsdorfCrowd, ""
				+ R.id.wilsdorfNoise, "" + R.id.wilsdorfSmallCrowdImage, ""
				+ R.id.wilsdorfSmallNoiseImage);
	}

	private class MyAsyncTask extends AsyncTask<String, String, String> {

		private String crowd;
		private String noise;
		private int crowdId;
		private int noiseId;
		private int crowdImageId;
		private int noiseImageId;
		private Double doubleCrowd;
		private Double doubleNoise;
		private boolean isPattern = false;

		protected String doInBackground(String... args) {

			crowdId = Integer.parseInt(args[1]);
			noiseId = Integer.parseInt(args[2]);
			crowdImageId = Integer.parseInt(args[3]);
			noiseImageId = Integer.parseInt(args[4]);

			String result = getJSONfromURL(args[0]);
			Log.d(TAG, args[0] + " | " + result);
			try {
				if (result != null) {
					JSONObject jObject = new JSONObject(result);
					crowd = jObject.getString("crowd");
					noise = jObject.getString("noise");
					doubleCrowd = Double.parseDouble(crowd);
					doubleNoise = Double.parseDouble(noise);

					if (crowd.equals("0") || noise.equals("0")) {
						String[] parts = args[0].split("/");
						String query = parts[0] + "/" + parts[1] + "/"
								+ parts[2] + "/" + parts[3] + "/" + parts[4]
								+ "/" + parts[5] + "/day";
						Log.d(TAG, "Temp: " + query);
						String result2 = getJSONfromURL(query);

						Log.d(TAG, "Exception: " + query + "|" + result2);

						JSONObject jObject2 = new JSONObject(result2);
						isPattern = true;
						crowd = jObject2.getString("crowd");
						noise = jObject2.getString("noise");
						doubleCrowd = Double.parseDouble(crowd);
						doubleNoise = Double.parseDouble(noise);

						// if (crowd.equals("0") || noise.equals("0*")){
						// crowd = "No data";
						// noise = "No data";
						// }

					}
				}
			} catch (JSONException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			} finally {
			}

			return null;
		}

		// Changes the values for a bunch of TextViews on the GUI
		protected void onPostExecute(String result) {
			Log.d(TAG, "About to set data");

			if (crowd.equals("0") || crowd.equals("0*")) {
				((TextView) findViewById(crowdId)).setText("No Data");
			}

			else if (isPattern == false) {
				// String checkAsterik = crowd.substring(0, crowd.length()-1);

				if (doubleCrowd <= 2) {
					((TextView) findViewById(crowdId)).setText("Sparse");
				} else if (doubleCrowd > 2 && doubleCrowd < 4) {
					((TextView) findViewById(crowdId)).setText("Normal");
					ImageView img = (ImageView) findViewById(crowdImageId);
					img.setImageResource(R.drawable.med_crowd);
				} else {
					((TextView) findViewById(crowdId)).setText("Busy");
					ImageView img = (ImageView) findViewById(crowdImageId);
					img.setImageResource(R.drawable.large_crowd);
				}
			} else {
				if (doubleCrowd <= 2) {
					((TextView) findViewById(crowdId)).setText("Sparse *");
				} else if (doubleCrowd > 2 && doubleCrowd < 4) {
					((TextView) findViewById(crowdId)).setText("Normal *");
					ImageView img = (ImageView) findViewById(crowdImageId);
					img.setImageResource(R.drawable.med_crowd);
				} else {
					((TextView) findViewById(crowdId)).setText("Busy *");
					ImageView img = (ImageView) findViewById(crowdImageId);
					img.setImageResource(R.drawable.large_crowd);
				}

			}

			if (noise.equals("0") || noise.equals("0*")) {
				((TextView) findViewById(noiseId)).setText("No Data");
			} else if (isPattern == false) {

				if (doubleNoise <= 2) {
					((TextView) findViewById(noiseId)).setText("Sparse");
				} else if (doubleNoise > 2 && doubleNoise < 4) {
					((TextView) findViewById(noiseId)).setText("Normal");
					ImageView img = (ImageView) findViewById(noiseImageId);
					img.setImageResource(R.drawable.medium_noise);
				} else {
					((TextView) findViewById(noiseId)).setText("Noisy");
					ImageView img = (ImageView) findViewById(noiseImageId);
					img.setImageResource(R.drawable.loud_noise);
				}

			} else {

				if (doubleNoise <= 2) {
					((TextView) findViewById(noiseId)).setText("Sparse *");
				} else if (doubleNoise > 2 && doubleNoise < 4) {
					((TextView) findViewById(noiseId)).setText("Normal *");
					ImageView img = (ImageView) findViewById(noiseImageId);
					img.setImageResource(R.drawable.medium_noise);
				} else {
					((TextView) findViewById(noiseId)).setText("Noisy *");
					ImageView img = (ImageView) findViewById(noiseImageId);
					img.setImageResource(R.drawable.loud_noise);
				}

			}

		}

	}

	public String getJSONfromURL(String url) {

		// initialize
		InputStream is = null;
		String result = "";

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e(TAG, "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e(TAG, "Error converting result " + e.toString());
		}

		return result;
	}

}
