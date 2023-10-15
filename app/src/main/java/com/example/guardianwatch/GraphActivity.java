package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;

public class GraphActivity extends AppCompatActivity  {

    ImageView backArrow;
    TextView graphActivityDate;
    TextView graphActivityDay;
    ImageView ArrowLeft;
    ImageView ArrowRight;
    TextView graphSentence1;
    TextView graphSentence2;
    TextView graphSentence3;
    ImageView useKcal;
    ImageView moveDistance;
    ImageView calendar;
    ImageView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // 현재 날짜를 가져옴
        Calendar calendarTime = Calendar.getInstance();
        // 년도를 가져옴
        int year = calendarTime.get(Calendar.YEAR);
        // 월을 가져옴
        int month = calendarTime.get(Calendar.MONTH) + 1;
        // 일을 가져옴
        int day = calendarTime.get(Calendar.DAY_OF_MONTH);

        graphActivityDate=findViewById(R.id.graphActivityDate);
        graphActivityDay=findViewById(R.id.graphActivityDay);
        ArrowLeft=findViewById(R.id.ArrowLeft);
        ArrowRight=findViewById(R.id.ArrowRight);
        graphSentence1=findViewById(R.id.graphSentence1);
        graphSentence2=findViewById(R.id.graphSentence2);
        graphSentence3=findViewById(R.id.graphSentence3);
        useKcal=findViewById(R.id.useKcal);
        moveDistance=findViewById(R.id.moveDistance);
        calendar=findViewById(R.id.calendar);
        graphView=findViewById(R.id.graphView);

        graphActivityDate.setText(year+"년 "+month+"월");
        graphActivityDay.setText(day+"");

        //뒤로가기 버튼 누를 시에 로그인 페이지로 이동
        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 이전 버튼의 리스너를 추가
        ArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 하루 전날의 날짜를 가져옴
                calendarTime.add(Calendar.DATE, -1);
                int year = calendarTime.get(Calendar.YEAR);
                int month = calendarTime.get(Calendar.MONTH) + 1;
                int day = calendarTime.get(Calendar.DAY_OF_MONTH);

                graphActivityDate.setText(year+"년 "+month+"월");
                graphActivityDay.setText(day+"");
            }
        });

        // 다음 버튼의 리스너를 추가
        ArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 하루 다음날의 날짜를 가져옴
                calendarTime.add(Calendar.DATE, 1);
                int year = calendarTime.get(Calendar.YEAR);
                int month = calendarTime.get(Calendar.MONTH) + 1;
                int day = calendarTime.get(Calendar.DAY_OF_MONTH);

                graphActivityDate.setText(year+"년 "+month+"월");
                graphActivityDay.setText(day+"");
            }
        });

        useKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useKcal.setImageResource(R.drawable.rectangle11);
                moveDistance.setImageResource(R.drawable.rectangle7);
            }
        });

        moveDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useKcal.setImageResource(R.drawable.rectangle7);
                moveDistance.setImageResource(R.drawable.rectangle10);
            }
        });


    }
}
