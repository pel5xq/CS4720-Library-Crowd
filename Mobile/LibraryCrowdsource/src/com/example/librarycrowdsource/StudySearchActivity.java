package com.example.librarycrowdsource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class StudySearchActivity extends Activity {

	private final String TAG = "LIBRARYCROWD";

	private TimePicker timePicker;
	private int hour;
	private int minute;
	static final int TIME_DIALOG_ID = 999;
	public static ProgressDialog progress;
	private boolean picker = false;

	private TextView startTime;
	private TextView endTime;
	private Button startTimeButton;
	private Button endTimeButton;

	private Spinner spinnerCourse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		progress = new ProgressDialog(this);
		progress.setTitle("Please wait...");
		progress.setMessage("Searching for study groups");
		setContentView(R.layout.activity_study_search);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// come back to this to set spinner to data type
		addListenerOnSpinnerItemSelection();

		// timePicker
		// setCurrentTimeOnView();
		// addListenerOnButton();

		/*
		 * Time t = new Time(); t.setToNow(); String currentTime =
		 * t.format("%I:%M %p"); startTime = (TextView)
		 * findViewById(R.id.sTime); startTime.setText(currentTime); endTime =
		 * (TextView) findViewById(R.id.eTime); t.hour += 1; String incTime =
		 * t.format("%I:%M %p"); endTime.setText(incTime);
		 */

		/*((Button) findViewById(R.id.createGroupButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						Intent intent = new Intent(StudySearchActivity.this,
								StudyPostActivity.class);
						startActivity(intent);

					}

				});*/
		
		((Button) findViewById(R.id.homeButton))
		 		.setOnClickListener(new OnClickListener() {
		 
		 			@Override
		 			public void onClick(View theView) {
		 				Intent intent = new Intent(StudySearchActivity.this,
		 						MainActivity.class);
		 				startActivity(intent);
		 			}
		 
		 		});

		/*
		 * ((Button) findViewById(R.id.startTimeButton)) .setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @SuppressWarnings("deprecation")
		 * 
		 * @Override public void onClick(View theView) {
		 * showDialog(TIME_DIALOG_ID);
		 * 
		 * }
		 * 
		 * });
		 * 
		 * ((Button) findViewById(R.id.endTimeButton)) .setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @SuppressWarnings("deprecation")
		 * 
		 * @Override public void onClick(View theView) {
		 * showDialog(TIME_DIALOG_ID); picker = true;
		 * 
		 * }
		 * 
		 * });
		 */

		((Button) findViewById(R.id.submitSearchButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						progress.show();

						String department = ((Spinner) findViewById(R.id.spinnerCourse))
								.getSelectedItem().toString();
						String course = ((TextView) findViewById(R.id.editText1))
								.getText().toString();

						Calendar cal = new GregorianCalendar();
						
						Log.d(TAG, department + "/" + course);
						if (!department.equals("") && !course.equals("")) {
							new SearchAsyncTask()
									.execute("http://infinite-thicket-5143.herokuapp.com/department/"
											+ department
											+ "/"
											+ "courseNum/"
											+ course
											+"/time/"
											+(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")).format((new GregorianCalendar()).getTime()));
						}
					}

				});

	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);

	}

	// public void setCurrentTimeOnView() {
	// final Calendar c = Calendar.getInstance();
	// hour = c.get(Calendar.HOUR_OF_DAY);
	// minute = c.get(Calendar.MINUTE);
	// // set current time into textview
	// // startTime.setText(new StringBuilder().append(padding_str(hour))
	// // .append(":").append(padding_str(minute)));
	//
	// // set current time into timepicker
	// timePicker.setCurrentHour(hour);
	// timePicker.setCurrentMinute(minute);
	// }

	/*
	 * @Override protected Dialog onCreateDialog(int id) { switch (id) { case
	 * TIME_DIALOG_ID: // set timePicker as current time return new
	 * TimePickerDialog(this, timePickerListener, hour, minute, false); } return
	 * null; }
	 * 
	 * private TimePickerDialog.OnTimeSetListener timePickerListener = new
	 * TimePickerDialog.OnTimeSetListener() { public void onTimeSet(TimePicker
	 * view, int selectedHour, int selectedMinute) { hour = selectedHour; minute
	 * = selectedMinute;
	 * 
	 * // set current time into textview
	 * 
	 * if (picker == false) { startTime.setText(new
	 * StringBuilder().append(pad(hour)) .append(":").append(pad(minute)));
	 * 
	 * // set current time into timepicker view.setCurrentHour(hour);
	 * view.setCurrentMinute(minute); }
	 * 
	 * else { endTime.setText(new StringBuilder().append(pad(hour))
	 * .append(":").append(pad(minute)));
	 * 
	 * // set current time into timepicker view.setCurrentHour(hour);
	 * view.setCurrentMinute(minute); }
	 * 
	 * } };
	 */

	// public void addListenerOnButton() {
	//
	// btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
	//
	// btnChangeTime.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// showDialog(TIME_DIALOG_ID);
	//
	// }
	//
	// });
	//
	// }

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private class SearchAsyncTask extends AsyncTask<String, String, String> {

		private ArrayList<View> inflatedRows;

		protected String doInBackground(String... args) {
			inflatedRows = new ArrayList<View>();

			String result = getJSONfromURL(args[0]);
			Log.d(TAG, args[0] + " | " + result);
			try {
				if (result != null) {
					JSONArray jArray = new JSONArray(result);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject jObject = jArray.getJSONObject(i);
						String library = jObject.getString("Library");
						String section = jObject.getString("Section");
						library = library.replace("%20", " ");
						section = section.replace("%20", " ");
						String department = jObject.getString("Dept");
						String courseNum = jObject.getString("CourseNum");
						String name = jObject.getString("Name");
						String description = jObject.getString("Descrip");
						String timeString = decodeTime(jObject.getString("StartTime"), jObject.getString("EndTime"));
						name = name.replace("_", " ");
						description = description.replace("_", " ");
						View newRow = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
								.inflate(R.layout.search_row, null);
						((TextView) newRow.findViewById(R.id.libraryText))
								.setText(library);
						((TextView) newRow.findViewById(R.id.sectionText))
								.setText(section);
						((TextView) newRow.findViewById(R.id.departmentText))
								.setText(department+" "+courseNum);
						((TextView) newRow.findViewById(R.id.nameText))
								.setText(name);
						((TextView) newRow.findViewById(R.id.descriptionText))
								.setText(description);
						((TextView) newRow.findViewById(R.id.timeText))
								.setText(timeString);
						inflatedRows.add(newRow);
					}
				}
			} catch (JSONException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			} finally {
			}

			return null;
		}
		
		private String decodeTime(String startT, String endT) {
			String hour = endT.substring(endT.indexOf("T")+1, endT.indexOf(":"));
			String minute = endT.split(":")[1];
			if (hour.equals("0") || hour.equals("00")) {
				return "12:"+minute+" AM";
			}
			else if (hour.equals("12")) {
				return "12:"+minute+" PM";
			}
			else if (Integer.parseInt(hour) > 12) {
				return (Integer.parseInt(hour)-12)+":"+minute+" PM";
			}
			else {
				return hour+":"+minute+" AM";
			}
		}

		// Changes the values for a bunch of TextViews on the GUI
		protected void onPostExecute(String result) {

			progress.dismiss();
			InputMethodManager man = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getCurrentFocus() != null) {
				man.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						man.HIDE_NOT_ALWAYS);
			}
			Log.d(TAG, "About to inflate add rows");
			TableLayout searchTable = (TableLayout) findViewById(R.id.searchTable);
			searchTable.removeAllViews();
			searchTable
					.addView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
							.inflate(R.layout.search_row_title, null));
			if (inflatedRows.isEmpty()) {
				searchTable
						.addView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
								.inflate(R.layout.search_row_empty, null));
			}
			for (View v : inflatedRows) {
				searchTable.addView(v);
			}
		}

		public String getJSONfromURL(String url) {

			// initialize
			InputStream is = null;
			String result = "";

			Log.d(TAG, "url: " + url);
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
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
