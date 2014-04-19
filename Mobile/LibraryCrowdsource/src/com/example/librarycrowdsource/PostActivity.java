package com.example.librarycrowdsource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;

public class PostActivity extends Activity {
	
	private final String TAG = "LIBRARYCROWD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		((Button) findViewById(R.id.homeButton))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View theView) {
				Intent intent = new Intent(PostActivity.this,
						MainActivity.class);
				startActivity(intent);
			}

		});
		
		Log.d(TAG, "About to set spinner listener");
		((Spinner) findViewById(R.id.spinnerLibrary)).setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "About to check "+id+" ("+position+")");
				int spinID = 0;
				String selectedText = ((Spinner)findViewById(R.id.spinnerLibrary)).getSelectedItem().toString();
				
				if (selectedText.equals(getResources().getString(R.string.alderman))) {
					Log.d(TAG, "Alderman Selected");
					spinID = R.layout.spinner_alderman;
				}
				else if (selectedText.equals(getResources().getString(R.string.clark))) {
					Log.d(TAG, "Clark Selected");
					spinID = R.layout.spinner_clark;
				}
				else if (selectedText.equals(getResources().getString(R.string.clemons))) {
					Log.d(TAG, "Clemons Selected");
					spinID = R.layout.spinner_clemons;
				}
				else if (selectedText.equals(getResources().getString(R.string.commerce))) {
					Log.d(TAG, "Commerce School Selected");
					spinID = R.layout.spinner_commerce;
				}
				else if (selectedText.equals(getResources().getString(R.string.rice))) {
					Log.d(TAG, "Rice Selected");
					spinID = R.layout.spinner_rice;
				}
				else if (selectedText.equals(getResources().getString(R.string.thornton))) {
					Log.d(TAG, "Thornton Selected");
					spinID = R.layout.spinner_thornton;
				}
				else if (selectedText.equals(getResources().getString(R.string.wilsdorf))) {
					Log.d(TAG, "Wilsdorf Selected");
					spinID = R.layout.spinner_wilsdorf;
				}
				if (spinID != 0) {
					View rightSpinner = ((LayoutInflater) getSystemService
							(Context.LAYOUT_INFLATER_SERVICE)).inflate(spinID, null);
					((TableLayout)findViewById(R.id.dynamicSectionView)).removeAllViews();
					((TableLayout)findViewById(R.id.dynamicSectionView)).addView(rightSpinner, 0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.d(TAG, "Library Spinner Nothing Selected");
				((TableLayout)findViewById(R.id.dynamicSectionView)).removeAllViews();
			}
			
		});
		
		
		((Button) findViewById(R.id.submitPostButton)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View theView) {
				//Submit Data
				String selectedLibrary = "";
				String selectedSection = "";
				String selectedNoise = "";
				String selectedCrowd = "";
				
				selectedLibrary = ((Spinner)findViewById(R.id.spinnerLibrary)).getSelectedItem().toString();
				if (selectedLibrary.equals(getResources().getString(R.string.rice))) {
					selectedLibrary = getResources().getString(R.string.url_rice);
				}
				
				selectedSection = ((Spinner)findViewById(R.id.spinnerSection)).getSelectedItem().toString();
				if (selectedSection.equals(getResources().getString(R.string.floor_one))) {
					selectedSection = getResources().getString(R.string.one);
				}
				else if (selectedSection.equals(getResources().getString(R.string.floor_two))) {
					selectedSection = getResources().getString(R.string.two);
				}
				else if (selectedSection.equals(getResources().getString(R.string.floor_three))) {
					selectedSection = getResources().getString(R.string.three);
				}
				else if (selectedSection.equals(getResources().getString(R.string.floor_four))) {
					selectedSection = getResources().getString(R.string.four);
				}
				else if (selectedSection.equals(getResources().getString(R.string.floor_five))) {
					selectedSection = getResources().getString(R.string.five);
				}
				else if (selectedSection.equals(getResources().getString(R.string.west_wing))) {
					selectedSection = getResources().getString(R.string.url_west_wing);
				}
				else if (selectedSection.equals(getResources().getString(R.string.east_wing))) {
					selectedSection = getResources().getString(R.string.url_east_wing);
				}
				else if (selectedSection.equals(getResources().getString(R.string.mcgregor))) {
					selectedSection = getResources().getString(R.string.aldermanMcGregor);
				}
				else if (selectedSection.equals(getResources().getString(R.string.reading_room))) {
					selectedSection = getResources().getString(R.string.clarkReading);
				}
				else if (selectedSection.equals(getResources().getString(R.string.computer_lab))) {
					selectedSection = getResources().getString(R.string.url_computer_lab);
				}
				
				selectedNoise = (((SeekBar)findViewById(R.id.noiseSeekBar)).getProgress()+1)+"";
				selectedCrowd = (((SeekBar)findViewById(R.id.crowdSeekBar)).getProgress()+1)+"";
	
				selectedLibrary = selectedLibrary.replace(" ", "%20");
				selectedSection = selectedSection.replace(" ", "%20");
				Log.d(TAG, selectedLibrary+" "+selectedSection+" "+selectedCrowd+" "+selectedNoise);
				if (!selectedLibrary.equals("") &&
						!selectedSection.equals("") &&
						!selectedCrowd.equals("") &&
						!selectedNoise.equals("")) {
						new PostAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/insert/library/"
								+selectedLibrary+"/section/"+selectedSection+"/crowd/"
								+selectedCrowd+"/noise/"+selectedNoise);
				}
				
				//Back to main activity: is this the right way?
				//Intent intent = new Intent(PostActivity.this, MainActivity.class);
	            //startActivity(intent);
				finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class PostAsyncTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			String result = postJSONtoURL(args[0]);
			Log.d(TAG, args[0]+" | "+result);
			return null;
		}

		// Changes the values for a bunch of TextViews on the GUI
		protected void onPostExecute(String result) {
			
		}
		
		public String postJSONtoURL(String url) {
			// http post
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				HttpResponse response = httpclient.execute(httppost);
				return response.getStatusLine().toString();

			} catch (Exception e) {
				Log.e(TAG, "Error in http connection " + e.toString());
			}
			return null;
		}

	}
	
}
