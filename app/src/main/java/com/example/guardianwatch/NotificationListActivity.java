package com.example.guardianwatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //===== 테스트를 위한 더미 데이터 생성 ===================
        List<NotificationData> testDataSet = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            testDataSet.add(new NotificationData("Command for " + i,i+" commands",i+" 분 전"));
        }
        //========================================================

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotification);

        //--- LayoutManager는 아래 3가지중 하나를 선택하여 사용 ---
        // 1) LinearLayoutManager()
        // 2) GridLayoutManager()
        // 3) StaggeredGridLayoutManager()
        //---------------------------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        CustomAdapter_Notification customAdapter = new CustomAdapter_Notification(testDataSet);
        recyclerView.setAdapter(customAdapter); // 어댑터 설정

        //뒤로가기 버튼 누를 시에 메인 페이지로 이동
        View backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}