package com.example.librarycrowdsource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class StudyPostActivity extends Activity {

	private final String TAG = "LIBRARYCROWD";

	private TimePicker timePicker;
	private int hour;
	private int minute;
	static final int TIME_DIALOG_ID = 999;
	private boolean picker = false;
	static final int DATE_DIALOG_ID = 998;
	private boolean isEndDate = false;
	private int dateYear, dateMonth, dateDay;

	private TextView startTime;
	private TextView endTime;
	private Button startTimeButton;
	private Button endTimeButton;

	private Spinner spinnerCourse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_study_post);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// come back to this to set spinner to data type
		addListenerOnSpinnerItemSelection();

		// timePicker
		// setCurrentTimeOnView();
		// addListenerOnButton();

		Time t = new Time();
		t.setToNow();
		String currentTime = t.format("%I:%M %p");
		startTime = (TextView) findViewById(R.id.sTime);
		startTime.setText(currentTime);
		endTime = (TextView) findViewById(R.id.eTime);
		t.hour += 1;
		String incTime = t.format("%I:%M %p");
		endTime.setText(incTime);

		((Button) findViewById(R.id.startTimeButton))
				.setOnClickListener(new OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View theView) {
						showDialog(TIME_DIALOG_ID);

					}

				});

		((Button) findViewById(R.id.endTimeButton))
				.setOnClickListener(new OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View theView) {
						showDialog(TIME_DIALOG_ID);
						picker = true;

					}

				});
		
		Calendar cal = Calendar.getInstance();
		dateYear = cal.get(Calendar.YEAR);
		dateMonth = cal.get(Calendar.MONTH);
		dateDay = cal.get(Calendar.DAY_OF_MONTH);
		((TextView) findViewById(R.id.startDate)).setText(dateYear+"-"+(dateMonth+1)+"-"+dateDay);
		((TextView) findViewById(R.id.endDate)).setText(dateYear+"-"+(dateMonth+1)+"-"+dateDay);
		
		((Button) findViewById(R.id.startDateButton))
		.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View theView) {
				isEndDate = false;
				showDialog(DATE_DIALOG_ID);

			}

		});

		((Button) findViewById(R.id.endDateButton))
		.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View theView) {
				isEndDate = true;
				showDialog(DATE_DIALOG_ID);
			}

		});

		Log.d(TAG, "About to set spinner listener");
		((Spinner) findViewById(R.id.spinnerLibrary))
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(TAG, "About to check " + id + " (" + position
								+ ")");
						int spinID = 0;
						String selectedText = ((Spinner) findViewById(R.id.spinnerLibrary))
								.getSelectedItem().toString();

						if (selectedText.equals(getResources().getString(
								R.string.alderman))) {
							Log.d(TAG, "Alderman Selected");
							spinID = R.layout.spinner_alderman;
						} else if (selectedText.equals(getResources()
								.getString(R.string.clark))) {
							Log.d(TAG, "Clark Selected");
							spinID = R.layout.spinner_clark;
						} else if (selectedText.equals(getResources()
								.getString(R.string.clemons))) {
							Log.d(TAG, "Clemons Selected");
							spinID = R.layout.spinner_clemons;
						} else if (selectedText.equals(getResources()
								.getString(R.string.commerce))) {
							Log.d(TAG, "Commerce School Selected");
							spinID = R.layout.spinner_commerce;
						} else if (selectedText.equals(getResources()
								.getString(R.string.rice))) {
							Log.d(TAG, "Rice Selected");
							spinID = R.layout.spinner_rice;
						} else if (selectedText.equals(getResources()
								.getString(R.string.thornton))) {
							Log.d(TAG, "Thornton Selected");
							spinID = R.layout.spinner_thornton;
						} else if (selectedText.equals(getResources()
								.getString(R.string.wilsdorf))) {
							Log.d(TAG, "Wilsdorf Selected");
							spinID = R.layout.spinner_wilsdorf;
						}
						if (spinID != 0) {
							View rightSpinner = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
									.inflate(spinID, null);
							((TableLayout) findViewById(R.id.dynamicSectionSpinnerView))
									.removeAllViews();
							((TableLayout) findViewById(R.id.dynamicSectionSpinnerView))
									.addView(rightSpinner, 0);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						Log.d(TAG, "Library Spinner Nothing Selected");
						((TableLayout) findViewById(R.id.dynamicSectionSpinnerView))
								.removeAllViews();
					}

				});

		((Button) findViewById(R.id.submitSearchButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						String library = "";
						String section = "";
						String department = "";
						String courseNum = "";
						String name = "";
						String description = "";
						String start = "";
						String end = "";

						department = ((Spinner) findViewById(R.id.spinnerCourse))
								.getSelectedItem().toString();
						courseNum = ((TextView) findViewById(R.id.editText1))
								.getText().toString();
						/*
						 * try { name =
						 * ((TextView)findViewById(R.id.nameText)).getText
						 * ().toString().replace(" ","%20"); name =
						 * URLEncoder.encode(name, "UTF-8"); } catch
						 * (UnsupportedEncodingException e) { Log.d(TAG,
						 * e.getMessage()); } try { description =
						 * ((TextView)findViewById
						 * (R.id.descriptionText)).getText
						 * ().toString().replace(" ", "%20"); description =
						 * URLEncoder.encode(description, "UTF-8"); } catch
						 * (UnsupportedEncodingException e) { Log.d(TAG,
						 * e.getMessage()); }
						 */

						name = stripFrom(((TextView) findViewById(R.id.nameText))
								.getText().toString());
						// description =
						// stripFrom(((TextView)findViewById(R.id.descriptionText)).getText().toString());

						if (null == name || name.equals("")) {
							name = "Anonymous";
						}

						if (null == description || description.equals("")) {
							description = "Studying";
						}

						start = encodeTime(((TextView) findViewById(R.id.startDate))
								.getText().toString(),
								((TextView) findViewById(R.id.sTime))
								.getText().toString());
						end = encodeTime(((TextView) findViewById(R.id.endDate))
								.getText().toString(),
								((TextView) findViewById(R.id.eTime))
								.getText().toString());

						String selectedLibrary = ((Spinner) findViewById(R.id.spinnerLibrary))
								.getSelectedItem().toString();
						if (null != selectedLibrary) {
							if (selectedLibrary.equals(getResources()
									.getString(R.string.rice))) {
								library = getResources().getString(
										R.string.url_rice);
							} else
								library = selectedLibrary;
						}

						String selectedSection = ((Spinner) findViewById(R.id.spinnerSection))
								.getSelectedItem().toString();
						if (null != selectedSection) {
							if (selectedSection.equals(getResources()
									.getString(R.string.floor_one))) {
								section = getResources()
										.getString(R.string.one);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.floor_two))) {
								section = getResources()
										.getString(R.string.two);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.floor_three))) {
								section = getResources().getString(
										R.string.three);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.floor_four))) {
								section = getResources().getString(
										R.string.four);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.floor_five))) {
								section = getResources().getString(
										R.string.five);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.west_wing))) {
								section = getResources().getString(
										R.string.url_west_wing);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.east_wing))) {
								section = getResources().getString(
										R.string.url_east_wing);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.mcgregor))) {
								section = getResources().getString(
										R.string.aldermanMcGregor);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.reading_room))) {
								section = getResources().getString(
										R.string.clarkReading);
							} else if (selectedSection.equals(getResources()
									.getString(R.string.computer_lab))) {
								section = getResources().getString(
										R.string.url_computer_lab);
							} else
								section = selectedSection;
						}

						library = library.replace(" ", "%20");
						section = section.replace(" ", "%20");

						Log.d(TAG, library + "/" + section + "/" + department
								+ "/" + courseNum + "/" + name + "/"
								+ description + "/" + start + "/" + end);
						if (!library.equals("") && !section.equals("")
								&& !department.equals("")
								&& !courseNum.equals("") &&
								// !name.equals("") && //optional
								// !description.equals("") && //optional
								!start.equals("") && !end.equals("")) {

							new PostAsyncTask()
									.execute("http://infinite-thicket-5143.herokuapp.com/insert/"
											+ "library/"
											+ library
											+ "/"
											+ "section/"
											+ section
											+ "/"
											+ "department/"
											+ department
											+ "/"
											+ "courseNum/"
											+ courseNum
											+ "/"
											+ "name/"
											+ name
											+ "/"
											+ "description/"
											+ description
											+ "/"
											+ "start/"
											+ start + "/" + "end/" + end);
						}

						finish();
					}

				});

		((Button) findViewById(R.id.submitPostButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						// Intent intent = new Intent(StudyPostActivity.this,
						// StudySearchActivity.class);
						// startActivity(intent);

						finish();

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

	private String encodeTime(String date, String time) {
		String result = "";
		if (null == date || date.equals(""))
			result += DateFormat.format("yyyy-MM-dd-",
				new Date(System.currentTimeMillis()));
		else 
			result+=date+"-";
		String[] hm = time.split(":");
		String hour = hm[0];
		String minute = hm[1].substring(0, 2);
		if (time.contains("PM") || time.contains("pm")) {
			int hourValue = Integer.parseInt(hour);
			if (hourValue != 12)
				hour = (hourValue + 12) + "";
		} else if (time.contains("AM") || time.contains("am")) {
			int hourValue = Integer.parseInt(hour);
			if (hourValue == 12)
				hour = "00";
		}
		result += hour + ":" + minute + ":00";
		return result;
	}

	private String stripFrom(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ')
				result += "_";
			else if (Character.isLetterOrDigit(input.charAt(i)))
				result += input.charAt(i);
		}
		return result;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set timePicker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);
		case DATE_DIALOG_ID:
			// set timePicker as current time
			return new DatePickerDialog(this, datePickerListener, dateYear, dateMonth,
					dateDay);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			Time t = new Time();
			t.minute = selectedMinute;
			t.hour = selectedHour;
			String time = t.format("%I:%M %p");

			// set current time into textview

			if (picker == false) {

				startTime.setText(time);

				// set current time into timepicker
				view.setCurrentHour(hour);
				view.setCurrentMinute(minute);
			}

			else {
				endTime.setText(time);

				// set current time into timepicker
				view.setCurrentHour(hour);
				view.setCurrentMinute(minute);
			}

		}
	};
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			dateYear = selectedYear;
			dateMonth = selectedMonth;
			dateDay = selectedDay;

			// set current time into textview

			if (isEndDate == false) {
				((TextView) findViewById(R.id.startDate)).setText(dateYear+"-"+(dateMonth+1)+"-"+dateDay);
			}

			else {
				((TextView) findViewById(R.id.endDate)).setText(dateYear+"-"+(dateMonth+1)+"-"+dateDay);
			}
			view.init(dateYear, dateMonth, dateDay, null);
		}
	};

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

	private class PostAsyncTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			String result = postJSONtoURL(args[0]);
			Log.d(TAG, args[0] + " | " + result);
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
