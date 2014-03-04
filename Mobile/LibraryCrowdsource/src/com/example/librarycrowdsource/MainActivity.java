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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final String TAG = "LIBRARYCROWD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "About to perform task");
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Clemons/timespan/60", 
				""+R.id.clemonsCrowd, ""+R.id.clemonsNoise);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Alderman/timespan/60", 
				""+R.id.aldermanCrowd, ""+R.id.aldermanNoise);
		new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/"
				+ "library/Thornton/timespan/60", 
				""+R.id.thorntonCrowd, ""+R.id.thorntonNoise);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class MyAsyncTask extends AsyncTask<String, String, String> {

		private String crowd;
		private String noise;
		private int crowdId;
		private int noiseId;

		protected String doInBackground(String... args) {
			
			crowdId = Integer.parseInt(args[1]);
			noiseId = Integer.parseInt(args[2]);
			
			String result = getJSONfromURL(args[0]);
			Log.d(TAG, args[0]+" | "+result);
			try {
				if (result != null) {
					JSONObject jObject = new JSONObject(result);
					crowd = jObject.getString("crowd");
					noise = jObject.getString("noise");
				}
			} catch (JSONException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			}
			finally {}

			return null;
		}

		// Changes the values for a bunch of TextViews on the GUI
		protected void onPostExecute(String result) {
			Log.d(TAG, "About to set data");
			((TextView) findViewById(crowdId)).setText(crowd);
			((TextView) findViewById(noiseId)).setText(noise);
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

}
