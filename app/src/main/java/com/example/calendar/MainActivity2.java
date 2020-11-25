package com.example.calendar;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends MainActivity {
    public DbHelper dbHelper;
    public ArrayAdapter<String> mAdapter;
    ArrayList<String> tasks;
    public ListView tasklist;
    private String selectedDate;
    Button backBtn;
    private String task;
    private SQLiteDatabase sqlDB;
    private String selectDate;
    private Cursor cursor;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbHelper = new DbHelper(this);
        tasklist = (ListView)findViewById(R.id.tasks);
        loadTaskList();


        backBtn = (Button) findViewById(R.id.bkbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(MainActivity2.this, MainActivity.class);
                changeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                changeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(changeActivity);
            }
        });
    }

    void loadTaskList() {
        tasks = dbHelper.getTaskList();
        if(mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.activity_row, R.id.task_title, tasks);
            tasklist.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
//        Drawable icon = menu.getItem(0).getIcon();
//        icon.mutate();
//        icon.setColorFilter(getResources().getColor((android.R.color.white, PorterDuff.Mode.SRC_IN));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addTask:
                final EditText taskEditText = new EditText(this);
                final EditText timeEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setMessage("Name the task or cancel.")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task = (String.valueOf(taskEditText.getText()));
                                String value = getIntent().getStringExtra("CalendarDateClick");
                                dbHelper.insertNewTask(task + " Date: " + value);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void InsertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectDate);
        contentValues.put("Event", tasklist.getContext().toString());
        sqlDB.insert("EventCalendar", null, contentValues);

    }

    public void ReadDatabase(View view){
        String query = "Select Event from EventCalendar where Date = " + selectDate;
        try{
            cursor = sqlDB.rawQuery(query, null);
            cursor.moveToFirst();
            tasklist.setAdapter(mAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
           // editText.setText(" ");
        }
    }
}