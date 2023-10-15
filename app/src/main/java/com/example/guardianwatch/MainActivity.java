package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    View childListView;
    View alarmView;
    View activityAmountView;
    View activityRecordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아이 목록 누를시에 아이 목록 페이지로 이동
        childListView=findViewById(R.id.childListView);
        alarmView=findViewById(R.id.alarmView);
        activityAmountView=findViewById(R.id.activityAmountView);
        activityRecordView=findViewById(R.id.activityRecordView);

        childListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChildListActivity.class);
                startActivity(intent);
            }
        });
        alarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
                startActivity(intent);
            }
        });
        activityAmountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HeatmapActivity.class);
                startActivity(intent);
            }
        });
        activityRecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
    }
}