package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
        activityRecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BevActivity.class);
                startActivity(intent);
            }
        });
    }

    //메인 페이지에서 뒤로가기 누를 시에 앱 종료 팝업 화면 띄우기
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();  // 현재 액티비티와 같은 taskAffinity를 가진 모든 액티비티를 종료합니다.
                        System.exit(0);  // 시스템을 통해 앱의 프로세스를 종료합니다.
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

}