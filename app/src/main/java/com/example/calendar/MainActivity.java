package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Define the variable of CalendarView type and TextView type
    CalendarView calender;
    TextView date_view;
    RadioButton radioBtn1, radioBtn2;
    RadioGroup radioGroup;
    private DbHelper dbHandler;
    private SQLiteDatabase sqlDB;
    private String selectDate;

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getIntent().removeExtra("key");
        setContentView(R.layout.activity_main);
        RelativeLayout bkground = (RelativeLayout) findViewById(R.id.bkgroundLay);
        calender = (CalendarView) findViewById(R.id.calender);
        date_view = (TextView) findViewById(R.id.date_view);
        radioBtn1 = (RadioButton) findViewById(R.id.rdbtn1);
        radioBtn2 = (RadioButton) findViewById(R.id.rdbtn2);
        radioGroup = (RadioGroup) findViewById(R.id.rdbtngrp);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioBtn1.isChecked()) {
                    bkground.setBackgroundColor(getResources().getColor(R.color.Plum));
                } else if (radioBtn2.isChecked()) {
                    bkground.setBackgroundColor((getResources().getColor(R.color.PeachPuff)));
                }
            }
        });

        //RelativeLayout bkground = (RelativeLayout) findViewById(R.id.bkgroundLay);
        //bkground.setBackgroundColor(getResources().getColor(R.color.purple_200));


        // Add Listener in calendar
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // method to get the value of days, months and years when date is selected by user.
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                // Store the value of date with format in String type
                // Add 1 in month because month index starts with 0 (Jan should be = 1)
                selectDate = (month + 1) + "-" + dayOfMonth + "-" + year;

                // set this date in TextView for Display
                date_view.setText(selectDate);

                //    Toast.makeText(MainActivity.this, ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);    // intent to save first page to move to second page
                intent.putExtra("CalendarDateClick",selectDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        try {
            dbHandler = new DbHelper(this);
            sqlDB = dbHandler.getWritableDatabase();
            sqlDB.execSQL("CREATE TABLE EventCalendar(Title TEXT)");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

}