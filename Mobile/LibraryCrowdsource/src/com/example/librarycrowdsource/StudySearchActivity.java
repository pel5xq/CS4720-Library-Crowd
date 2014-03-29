package com.example.librarycrowdsource;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class StudySearchActivity extends Activity {

	private TimePicker timePicker;
	private int hour;
	private int minute;
	static final int TIME_DIALOG_ID = 999;
	private boolean picker = false;

	private TextView startTime;
	private TextView endTime;
	private Button startTimeButton;
	private Button endTimeButton;

	private Spinner spinnerCourse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_search);

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

		((Button) findViewById(R.id.submitPostButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View theView) {
						Intent intent = new Intent(StudySearchActivity.this,
								StudyPostActivity.class);
						startActivity(intent);

					}

				});

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

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set timePicker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview

			if (picker == false) {
				startTime.setText(new StringBuilder().append(pad(hour))
						.append(":").append(pad(minute)));

				// set current time into timepicker
				view.setCurrentHour(hour);
				view.setCurrentMinute(minute);
			}

			else {
				endTime.setText(new StringBuilder().append(pad(hour))
						.append(":").append(pad(minute)));

				// set current time into timepicker
				view.setCurrentHour(hour);
				view.setCurrentMinute(minute);
			}

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

}
