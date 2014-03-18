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

public class MainActivity extends Activity {
	
	private final String TAG = "LIBRARYCROWD";
	
	private EditText editText1;
	//private TableLayout LibraryDatascrollView;
	Button submit_library_button;
	private TextView textView1;
	private TableRow tableRow1;
	private TableRow tableRow2;
	private TableRow tableRow3;
	private TextView TextViewCrowd;
	
	private final String clemonsArray[] = {"1", "2", "3", "4"};
	private final String aldermanArray[] = {"WestWing", "EastWing", "McGregor", "Cafe"};
	private final String thorntonArray[] = {"Stacks"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		textView1 = (TextView) findViewById(R.id.libraryNameTextView);
		
		textView1.setOnClickListener(textView1Listener);	
		
		tableRow1 = (TableRow) findViewById(R.id.tableRow1);
		tableRow2 = (TableRow) findViewById(R.id.tableRow2);
		tableRow3 = (TableRow) findViewById(R.id.tableRow3);
		TextViewCrowd = (TextView) findViewById(R.id.TextViewCrowd);
		
		tableRow1.setOnClickListener(tableRow1Listener);
		tableRow2.setOnClickListener(tableRow2Listener);
		tableRow3.setOnClickListener(tableRow3Listener);
			
		
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
	
		
		Log.d(TAG, "About to set button listener");
		((Button) findViewById(R.id.makePostButton)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View theView) {
				Intent intent = new Intent(MainActivity.this, PostActivity.class);
	            startActivity(intent);
			}
			
		});
}
	
	 public OnClickListener tableRow2Listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//textView1.setText("HELLO YOU");
			
			//textView1.setText(TextViewCrowd.getText().toString());
			TextView TextViewNoise = (TextView) findViewById(R.id.TextViewNoise);
			Intent intent = new Intent(MainActivity.this, LibraryInfoActivity.class);
			intent.putExtra("Library",TextViewNoise.getText().toString());
			intent.putExtra("array", thorntonArray);
			startActivity(intent);
		}
	
	}; 
	
	public OnClickListener tableRow1Listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(MainActivity.this, LibraryInfoActivity.class);
			intent.putExtra("Library",TextViewCrowd.getText().toString());
			intent.putExtra("array", clemonsArray);
		
			startActivity(intent);
		}
	
	}; 
	
	public OnClickListener tableRow3Listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//textView1.setText("HELLO YOU");
			
			//textView2.setText(TextViewCrowd.getText().toString());
			TextView textView3 = (TextView) findViewById(R.id.text3);
			
			Intent intent = new Intent(MainActivity.this, LibraryInfoActivity.class);
			intent.putExtra("Library",textView3.getText().toString());
			intent.putExtra("array",  aldermanArray);
			startActivity(intent);
		}
	
	}; 
	
	public OnClickListener textView1Listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//textView1.setText("HELLO YOU");
			Intent intent = new Intent(MainActivity.this, LibraryInfoActivity.class);
			intent.putExtra("Library",textView1.getText().toString());
			textView1.setText(textView1.getText().toString());
			startActivity(intent);
		}
	}; 
	
	//private void updateLibraryDatascrollView(String )
	
	
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
