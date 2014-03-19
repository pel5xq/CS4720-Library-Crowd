package com.example.librarycrowdsource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class LibraryInfoActivity extends Activity {
	
	private final String TAG = "LIB INFO";
	//private TextView text1;
	//private TextView libraryCrowdTextView;
	//private TextView libraryNoiseTextView;
	
	
	//private String lib = "";
	static String libraryInfoDos = "/section/";
	static String libraryInfoUno = "http://plato.cs.virginia.edu/~pel5xq/library/";
	static String libraryInfoTres = "/day";
			
			//http://plato.cs.virginia.edu/~pel5xq/library/Alderman/section/4/day
				
	//private String libraryName = "";
	//private String libraryCrowd = "";
	//private String libraryNoise = "";
	//String library;
	String section[] = new String[5];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		String arrays[] = getIntent().getExtras().getStringArray("array");
		String library = getIntent().getExtras().getString("Library");
		if (library.equals("Commerce School")){
			library = "Commerce%20School";
		}
		if (library.equals("Rice Hall")){
			library = ("Rice");
		}
		
		for(int i=0; i<5; i++){
			if(i<arrays.length){
				section[i]=arrays[i];
				Log.d(TAG, section[i]);
			}
			else{
				section[i]="";
			}
		}
		
		//new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + "4" + "/day",""+R.id.text1,""+R.id.text2,""+R.id.text3 );
		
	
		if(arrays.length>=1){
			new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + section[0] + "/timespan/60",""+R.id.sec0,""+R.id.sec0Crowd,""+R.id.sec0Noise, section[0] );
		}
		if(arrays.length>=2){
			new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + section[1] + "/timespan/60",""+R.id.sec1,""+R.id.sec1Crowd,""+R.id.sec1Noise, section[1] );
		}
		if(arrays.length>=3){
			new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + section[2] + "/timespan/60",""+R.id.sec2,""+R.id.sec2Crowd,""+R.id.sec2Noise, section[2] );
		}
		if(arrays.length>=4){
			new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + section[3] + "/timespan/60",""+R.id.sec3,""+R.id.sec3Crowd,""+R.id.sec3Noise, section[3] );
		}
		if(arrays.length>=5){
			new MyAsyncTask().execute("http://plato.cs.virginia.edu/~pel5xq/library/" + library + "/section/" + section[4] + "/timespan/60",""+R.id.sec4,""+R.id.sec4Crowd,""+R.id.sec4Noise, section[4] );
		} 
	
	}
	
	private class MyAsyncTask extends AsyncTask<String, String, String> {

		private String section;
		private String crowd;
		private String noise;
		private int sectionId;
		private int crowdId;
		private int noiseId;

		protected String doInBackground(String... args) {
			
			sectionId = Integer.parseInt(args[1]);
			crowdId = Integer.parseInt(args[2]);
			noiseId = Integer.parseInt(args[3]);
			section = args[4];
			
			String result = getJSONfromURL(args[0]);
			Log.d(TAG, args[0]+" | " + result);
			try {
				if (result != null) {
					JSONObject jObject = new JSONObject(result);
					
					crowd = jObject.getString("crowd");
					noise = jObject.getString("noise");
					Log.d("DATAAAAAAA", crowd + " " + noise);
					
					if (crowd.equals("0") || noise.equals("0")){
						String [] parts = args[0].split("/");
						String query = parts[0] + "/" + parts[1] + "/" + parts[2] + "/" + 
								parts[3] + "/" + parts[4] + "/" + parts[5] + "/" + 
								parts[6] + "/" + parts[7] + "/day";
						Log.d(TAG, "Temp: " + query);
						String result2 = getJSONfromURL(query);

						Log.d(TAG, "Exception: " + query + "|" + result2);

						JSONObject jObject2 = new JSONObject(result2);
						crowd = jObject2.getString("crowd") + "*";
						noise = jObject2.getString("noise") + "*";
						
						if (crowd.equals("0") || noise.equals("0*")){
							crowd = "No data";
							noise = "No data";
						}
						
					}
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
			//String sect = getIntent().getExtras().getString("Library");
			//((TextView) findViewById(sectionId)).setText(section);
			if(section.equals("CompLab")){
				((TextView) findViewById(sectionId)).setText("Computer Lab");
			}
			else if(section.equals("EastWing")){
				((TextView) findViewById(sectionId)).setText("East Wing");
			}
			else if(section.equals("WestWing")){
				((TextView) findViewById(sectionId)).setText("West Wing");
			}
			else if(section.matches(".*\\d.*")){
				((TextView) findViewById(sectionId)).setText("Floor " + section);
			}
			else{
				((TextView) findViewById(sectionId)).setText(section);
			}
			((TextView) findViewById(crowdId)).setText(" "+crowd+" " );
			((TextView) findViewById(noiseId)).setText(" "+noise+" ");
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
