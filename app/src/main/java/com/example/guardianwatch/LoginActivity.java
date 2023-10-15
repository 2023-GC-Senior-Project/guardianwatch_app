package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView signUpText;
    Button loginBtn;
    View parentView;
    View teacherView;
    TextView nextText;


    // 화면 터치시 키보드 내리기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null) {

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpText=findViewById(R.id.text2);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_mode_alert);

                // 배경 흐리게 하기
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.dimAmount = 0.5f; // 0.0f ~ 1.0f (1.0f는 완전 흐림)
                dialog.getWindow().setAttributes(lp);

                // 학부모/선생님 선택할때 색상 변경
                parentView=dialog.findViewById(R.id.parentView);
                teacherView=dialog.findViewById(R.id.teacherView);
                parentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewCompat.setBackgroundTintList(parentView, ColorStateList.valueOf(Color.parseColor("#A0E2E2")));
////                        ViewCompat.setBackgroundTintList(teacherView, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
//                        teacherView.setBackgroundResource(R.drawable.rectangle_shape);
                    }
                });
//                teacherView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ViewCompat.setBackgroundTintList(teacherView, ColorStateList.valueOf(Color.parseColor("#A0E2E2")));
////                        ViewCompat.setBackgroundTintList(parentView, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
////                        parentView.setBackgroundResource(R.drawable.rectangle_shape);
//                    }
//                });
                //다음 텍스트 누를때 회원가입 페이지로 이동
                nextText=dialog.findViewById(R.id.nextText);
                nextText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                    }
                });

                // 팝업화면 띄우기
                dialog.show();
            }
        });

        //로그인 버튼 누를시에 메인 페이지로 이동
        loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}