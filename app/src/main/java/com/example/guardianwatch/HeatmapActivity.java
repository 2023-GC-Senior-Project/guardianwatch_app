package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HeatmapActivity extends AppCompatActivity  {

    ImageView backArrow;
    TextView heatmapActivityDate;
    TextView heatmapActivityDay;
    ImageView ArrowLeft;
    ImageView ArrowRight;
    ImageView calendar;
    TextView longPlaceData;
    TextView longPersonData;
    TextView longActionData;
    ImageView heatmapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);

        // 현재 날짜를 가져옴
        Calendar calendarTime = Calendar.getInstance();
        // 년도를 가져옴
        int year = calendarTime.get(Calendar.YEAR);
        // 월을 가져옴
        int month = calendarTime.get(Calendar.MONTH) + 1;
        // 일을 가져옴
        int day = calendarTime.get(Calendar.DAY_OF_MONTH);

        heatmapActivityDate=findViewById(R.id.heatmapActivityDate);
        heatmapActivityDay=findViewById(R.id.heatmapActivityDay);
        ArrowLeft=findViewById(R.id.ArrowLeft);
        ArrowRight=findViewById(R.id.ArrowRight);
        calendar=findViewById(R.id.calendar);
        longPlaceData=findViewById(R.id.longPlaceData);
        longPersonData=findViewById(R.id.longPersonData);
        longActionData=findViewById(R.id.longActionData);
        heatmapView=findViewById(R.id.heatmapView);

        heatmapActivityDate.setText(year+"년 "+month+"월");
        heatmapActivityDay.setText(day+"");

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

                heatmapActivityDate.setText(year+"년 "+month+"월");
                heatmapActivityDay.setText(day+"");
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

                heatmapActivityDate.setText(year+"년 "+month+"월");
                heatmapActivityDay.setText(day+"");
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 날짜 선택 다이얼로그를 띄운다.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        HeatmapActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            // 선택한 날짜를 Toast로 출력한다.
                            calendarTime.set(year,month,dayOfMonth);
                        },
                        calendarTime.get(Calendar.YEAR),
                        calendarTime.get(Calendar.MONTH) + 1,
                        calendarTime.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

                heatmapActivityDate.setText(year+"년 "+month+"월");
                heatmapActivityDay.setText(day+"");
            }
        });
    }
}
