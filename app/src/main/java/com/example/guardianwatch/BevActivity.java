package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class BevActivity extends AppCompatActivity  {

    ImageView backArrow;
    TextView bevActivityDate;
    TextView bevActivityDay;
    ImageView ArrowLeft;
    ImageView ArrowRight;
    ImageView calendar;
    TextView longPlaceData;
    TextView longPersonData;
    TextView longActionData;
    ImageView bevView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bev);

        // 현재 날짜를 가져옴
        Calendar calendarTime = Calendar.getInstance();
        // 년도를 가져옴
        int year = calendarTime.get(Calendar.YEAR);
        // 월을 가져옴
        int month = calendarTime.get(Calendar.MONTH) + 1;
        // 일을 가져옴
        int day = calendarTime.get(Calendar.DAY_OF_MONTH);

        bevActivityDate=findViewById(R.id.bevActivityDate);
        bevActivityDay=findViewById(R.id.bevActivityDay);
        ArrowLeft=findViewById(R.id.ArrowLeft);
        ArrowRight=findViewById(R.id.ArrowRight);
        calendar=findViewById(R.id.calendar);
        longPlaceData=findViewById(R.id.longPlaceData);
        longPersonData=findViewById(R.id.longPersonData);
        longActionData=findViewById(R.id.longActionData);
        bevView=findViewById(R.id.bevView);

        bevActivityDate.setText(year+"년 "+month+"월");
        bevActivityDay.setText(day+"");

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

                bevActivityDate.setText(year+"년 "+month+"월");
                bevActivityDay.setText(day+"");
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

                bevActivityDate.setText(year+"년 "+month+"월");
                bevActivityDay.setText(day+"");
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 날짜 선택 다이얼로그를 띄운다.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BevActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            int monthT = month + 1;
                            bevActivityDate.setText(year+"년 "+month+"월");
                            bevActivityDay.setText(day+"");
                        },
                        calendarTime.get(Calendar.YEAR),
                        calendarTime.get(Calendar.MONTH),
                        calendarTime.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.getDatePicker().setBackgroundColor(Color.parseColor("#D7E7F1"));
                datePickerDialog.show();


            }
        });
    }
}
