package com.example.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import java.util.ArrayList;

public class RowActivity extends MainActivity2 {
    Button deleteBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent().removeExtra("key");
        setContentView(R.layout.activity_main);


        deleteBtn = (Button) findViewById(R.id.delBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(v);
            }
        });
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        Log.e("String", (String) taskTextView.getText());
        String task = String.valueOf(taskTextView.getText());
        final int position = tasklist.getPositionForView((View) view.getParent());
        tasks.remove(position);
        mAdapter.notifyDataSetChanged();
        dbHelper.deleteTask(task);
        loadTaskList();
    }
}
