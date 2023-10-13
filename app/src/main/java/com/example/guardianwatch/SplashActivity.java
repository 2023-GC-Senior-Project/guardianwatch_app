package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView textView;
    private String fullText = "GuardianWatch";
    private int index = 0;
    private long delay = 130;  // 각 글자가 나타나는 지연 시간 (130ms)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.logo);
        showTextByChar();
    }

    private void showTextByChar() {
        if (index <= fullText.length()) {
            textView.setText(fullText.substring(0, index));
            index++;

            // 지연 시간 후에 다음 글자를 표시
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showTextByChar();
                }
            }, delay);
        }else {
            // 모든 글자가 표시된 후 1초 대기
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 대기 시간 후 메인 액티비티로 전환
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);  // 1초 대기
        }
    }
}
