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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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
