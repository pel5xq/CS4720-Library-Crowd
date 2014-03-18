package com.example.librarycrowdsource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;

public class PostActivity extends Activity {
	
	private final String TAG = "LIBRARYCROWD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		Log.d(TAG, "About to set check listener");
		((RadioGroup) findViewById(R.id.libraryRadioGroup)).setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.d(TAG, "About to check "+checkedId);
				
				int radioID = 0;
				
				if (checkedId == R.id.libraryRadioAlderman) {
					Log.d(TAG, "Alderman Selected");
					radioID = R.layout.radio_section_alderman;
				}
				else if (checkedId == R.id.libraryRadioClark) {
					Log.d(TAG, "Clark Selected");
					radioID = R.layout.radio_section_clark;
				}
				else if (checkedId == R.id.libraryRadioClemons) {
					Log.d(TAG, "Clemons Selected");
					radioID = R.layout.radio_section_clemons;
				}
				else if (checkedId == R.id.libraryRadioCommerce) {
					Log.d(TAG, "Clemons Selected");
					radioID = R.layout.radio_section_commerce;
				}
				else if (checkedId == R.id.libraryRadioRice) {
					Log.d(TAG, "Rice Selected");
					radioID = R.layout.radio_section_rice;
				}
				else if (checkedId == R.id.libraryRadioThornton) {
					Log.d(TAG, "Thornton Selected");
					radioID = R.layout.radio_section_thornton;
				}
				else if (checkedId == R.id.libraryRadioWilsdorf) {
					Log.d(TAG, "Wilsdorf Selected");
					radioID = R.layout.radio_section_wilsdorf;
				}
				if (radioID != 0) {
					View radioButtons = ((LayoutInflater) getSystemService
							(Context.LAYOUT_INFLATER_SERVICE)).inflate(radioID, null);
					((TableLayout)findViewById(R.id.dynamicSectionView)).removeAllViews();
					((TableLayout)findViewById(R.id.dynamicSectionView)).addView(radioButtons, 0);
				}
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
				if (((RadioGroup)findViewById(R.id.libraryRadioGroup))
					.getCheckedRadioButtonId() != -1) {
					selectedLibrary = ((RadioButton)findViewById((
							(RadioGroup)findViewById(R.id.libraryRadioGroup))
							.getCheckedRadioButtonId())).getText().toString();
				}
				if (null != ((RadioGroup)findViewById(R.id.sectionRadioGroup)) &&
						((RadioGroup)findViewById(R.id.sectionRadioGroup))
						.getCheckedRadioButtonId() != -1) {
					selectedSection = ((RadioButton)findViewById((
							(RadioGroup)findViewById(R.id.sectionRadioGroup))
							.getCheckedRadioButtonId())).getText().toString();
				}
				if (((RadioGroup)findViewById(R.id.noiseRadioGroup))
						.getCheckedRadioButtonId() != -1) {
					selectedNoise = ((RadioButton)findViewById((
							(RadioGroup)findViewById(R.id.noiseRadioGroup))
							.getCheckedRadioButtonId())).getText().toString();
				}
				if (((RadioGroup)findViewById(R.id.crowdRadioGroup))
						.getCheckedRadioButtonId() != -1) {
					selectedCrowd = ((RadioButton)findViewById((
							(RadioGroup)findViewById(R.id.crowdRadioGroup))
							.getCheckedRadioButtonId())).getText().toString();
				}
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
