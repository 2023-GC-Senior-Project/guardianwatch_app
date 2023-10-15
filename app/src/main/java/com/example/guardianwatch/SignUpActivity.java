package com.example.guardianwatch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    ImageView backArrow;
    TextView doubleCheck;
    TextView idAvailableText;
    TextView pwAvailableText;
    EditText pwEditText;
    EditText pwCheckEditText;
    TextView pwCheckAvailableText;
    private String password;
    Button loginBtn;


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
        setContentView(R.layout.activity_signup);

        //중복확인 누를 시에 텍스트 추가
        doubleCheck=findViewById(R.id.doublecheck);
        idAvailableText=findViewById(R.id.idAvailableText);
        doubleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAvailableText.setText("사용 가능한 아이디입니다.");
            }
        });

        //뒤로가기 버튼 누를 시에 로그인 페이지로 이동
        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //---------비밀번호 입력--------
        pwEditText=findViewById(R.id.pwEditText);
        pwAvailableText=findViewById(R.id.pwAvailableText);
        pwEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 이 메소드는 텍스트 입력 변경 전에 호출됩니다.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //입력한 비밀번호 임시 저장
                String pw = pwEditText.getText().toString().trim();

                if (pw.length() < 8) {
                    pwAvailableText.setText("8자 이상을 입력해주세요.");
                    //빨간색으로 바꾸기
                    pwAvailableText.setTextColor(Color.parseColor("#EA4185"));

                } else {
                    pwAvailableText.setText("사용가능한 비밀번호입니다");
                    //파란색으로 바꾸기
                    pwAvailableText.setTextColor(Color.parseColor("#207C98"));
                    password=pw;

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        //---------------비밀번호 확인---------------
        //비밀번호 다시 입력하는 공간
        pwCheckEditText=findViewById(R.id.pwCheckEditText);

        //비밀번호 일치여부 결과 text
        pwCheckAvailableText=findViewById(R.id.pwCheckAvailableText);

        //비밀번호 일치하는지 실시간으로 검사
        pwCheckEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 이 메소드는 텍스트 입력 변경 전에 호출됩니다.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력한 비밀번호 저장
                String pwConfirm = pwCheckEditText.getText().toString().trim();
                if (!pwConfirm.equals(password)) {
                    pwCheckAvailableText.setText("비밀번호가 일치하지 않습니다.");
//                    //빨간색으로 바꾸기
                    pwCheckAvailableText.setTextColor(Color.parseColor("#EA4185"));
                } else {
                    pwCheckAvailableText.setText("비밀번호가 일치합니다.");
//                    //파란색으로 바꾸기
                    pwCheckAvailableText.setTextColor(Color.parseColor("#207C98"));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                // 이 메소드는 텍스트 입력 변경 후에 호출됩니다.
            }
        });

        loginBtn=findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}