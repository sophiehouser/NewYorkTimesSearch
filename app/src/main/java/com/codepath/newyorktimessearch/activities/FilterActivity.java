package com.codepath.newyorktimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.newyorktimessearch.DatePickerFragment;
import com.codepath.newyorktimessearch.R;
import com.codepath.newyorktimessearch.SearchFilters;

import java.util.Calendar;

/**
 * Created by sophiehouser on 6/22/16.
 */
public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatePicker dpDate;
    int myYear;
    int myDay;
    int myMonth;
    EditText etDate;
    SearchFilters filters;
    Spinner svOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("date happened one");
        setContentView(R.layout.activity_filter);
        etDate = (EditText) findViewById(R.id.etDateInput);
        etDate.setKeyListener(null);
        filters = new SearchFilters();
        //dpDate = (DatePicker) findViewById(R.id.dpDate);
        //System.out.println("date happened two");
    }


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

        // handle the date selected
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // store the values selected into a Calendar instance
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            myYear = year;
            myDay = dayOfMonth;
            myMonth = monthOfYear;
            etDate.setText(String.valueOf(myMonth) + "/" + String.valueOf(myDay) + "/" + String.valueOf(myYear));
        }

    public void onDateClicked(View view) {
        showDatePickerDialog();
    }

    public void onSubmitClicked(View view){
        Intent i = new Intent(this, SearchActivity.class);
        //i.putExtra("myDate", String.valueOf(myYear) + String.valueOf(myMonth) + String.valueOf(myDay));
        //i.putExtra("date", myYear + myMonth + myDay);
        Spinner svOrder = (Spinner) findViewById(R.id.svOrder);
        String value = svOrder.getSelectedItem().toString();
        char c[] = value.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        filters.order = new String(c);
        if (myMonth < 10) {
            filters.beginDate = String.valueOf(myYear) + "0" + String.valueOf(myMonth) + String.valueOf(myDay);
        } else {
            filters.beginDate = String.valueOf(myYear) + String.valueOf(myMonth) + String.valueOf(myDay);
        }
        i.putExtra("filter", filters);
        setResult(RESULT_OK, i);
        finish();
    }
}
