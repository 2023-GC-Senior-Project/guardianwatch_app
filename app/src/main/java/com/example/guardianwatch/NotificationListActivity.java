package com.example.guardianwatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity {

    ImageView backArrow;
    View notificationAll;
    View notificationSafe;
    View notificationEat;
    View notificationAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationAll=findViewById(R.id.notificationAll);
        notificationSafe=findViewById(R.id.notificationSafe);
        notificationEat=findViewById(R.id.notificationEat);
        notificationAction=findViewById(R.id.notificationAction);

        //===== 테스트를 위한 더미 데이터 생성 ===================
        List<NotificationData> testDataSet = new ArrayList<>();

        //========================================================

        //뒤로가기 버튼 누를 시에 메인 페이지로 이동
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        notificationAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationAll.setBackgroundResource(R.drawable.rectangle5);
                notificationSafe.setBackgroundResource(R.drawable.rectangle6);
                notificationEat.setBackgroundResource(R.drawable.rectangle6);
                notificationAction.setBackgroundResource(R.drawable.rectangle6);

                testDataSet.clear();
                testDataSet.add(new NotificationData("지안이가 밥을 먹고 있어요.","2023/10/15 10:27","7시간 전"));
                testDataSet.add(new NotificationData("지안이가 친구와 말하고 있어요.","2023/10/15 12:33","5시간 전"));
                testDataSet.add(new NotificationData("지안이가 밥을 먹고 있어요.","2023/10/15 13:09","4시간 전"));
                testDataSet.add(new NotificationData("지안이가 뛰어 놀고 있어요.","2023/10/15 17:33","10분 전"));
                testDataSet.add(new NotificationData("지안이가 넘어졌어요.","2023/10/15 17:42","방금 전"));
            }
        });

        notificationSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationAll.setBackgroundResource(R.drawable.rectangle6);
                notificationSafe.setBackgroundResource(R.drawable.rectangle5);
                notificationEat.setBackgroundResource(R.drawable.rectangle6);
                notificationAction.setBackgroundResource(R.drawable.rectangle6);

                testDataSet.clear();
                testDataSet.add(new NotificationData("지안이가 넘어졌어요.","2023/10/15 17:42","방금 전"));

            }
        });

        notificationEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationAll.setBackgroundResource(R.drawable.rectangle6);
                notificationSafe.setBackgroundResource(R.drawable.rectangle6);
                notificationEat.setBackgroundResource(R.drawable.rectangle5);
                notificationAction.setBackgroundResource(R.drawable.rectangle6);

                testDataSet.clear();
                testDataSet.add(new NotificationData("지안이가 밥을 먹고 있어요.","2023/10/15 10:27","7시간 전"));
                testDataSet.add(new NotificationData("지안이가 밥을 먹고 있어요.","2023/10/15 13:09","4시간 전"));
            }
        });

        notificationAction.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                notificationAll.setBackgroundResource(R.drawable.rectangle6);
                notificationSafe.setBackgroundResource(R.drawable.rectangle6);
                notificationEat.setBackgroundResource(R.drawable.rectangle6);
                notificationAction.setBackgroundResource(R.drawable.rectangle5);

                testDataSet.clear();
                testDataSet.add(new NotificationData("지안이가 친구와 말하고 있어요.","2023/10/15 12:33","5시간 전"));
                testDataSet.add(new NotificationData("지안이가 뛰어 놀고 있어요.","2023/10/15 17:33","10분 전"));

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotification);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        CustomAdapter_Notification customAdapter = new CustomAdapter_Notification(testDataSet);
        recyclerView.setAdapter(customAdapter); // 어댑터 설정
    }

}