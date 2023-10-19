package com.example.guardianwatch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        doubleCheck = findViewById(R.id.doublecheck);
        idAvailableText = findViewById(R.id.idAvailableText);
        doubleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAvailableText.setText("사용 가능한 아이디입니다.");
            }
        });

        //뒤로가기 버튼 누를 시에 로그인 페이지로 이동
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //---------비밀번호 입력--------
        pwEditText = findViewById(R.id.pwEditText);
        pwAvailableText = findViewById(R.id.pwAvailableText);
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
                    password = pw;

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        //---------------비밀번호 확인---------------
        //비밀번호 다시 입력하는 공간
        pwCheckEditText = findViewById(R.id.pwCheckEditText);

        //비밀번호 일치여부 결과 text
        pwCheckAvailableText = findViewById(R.id.pwCheckAvailableText);

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

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 로직

                //아이디, 패스워드
                EditText idEditText = findViewById(R.id.idEditText);
                String userId = idEditText.getText().toString().trim();
                String password = pwEditText.getText().toString().trim();

//                Service service = RetrofitServiceImplFactory.createService();
//                Call<SignUpResult> call = service.signUp(userId, password);
//
//                call.enqueue(new Callback<SignUpResult>() {
//                    @Override
//                    public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
//                        if(response.isSuccessful()) {
//                            // 회원가입 성공 처리
//                            Log.d("SignUp", "Success!");
//                        } else {
//                            // 실패 처리
//                            Log.d("SignUp", "Failed: " + response.message());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SignUpResult> call, Throwable t) {
//                        // 네트워크 에러 등 처리
//                        Log.d("SignUp", "Error: " + t.getMessage());
//                    }
//                });

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://inclab3.gachon.ac.kr:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Service service = retrofit.create(Service.class);

                UserData newUser = new UserData(userId, password);
                Call<ResponseBody> call = service.createUser(newUser);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            // 회원가입 성공 처리
                            Log.d("SignUp", "Success!");

                            // 회원가입 성공 토스트 메시지 표시
                            Toast.makeText(SignUpActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();

                            // 로그인 화면으로 돌아가기
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();  // 현재 액티비티 종료 (회원가입 화면)

                        } else {
                            // 실패 처리
                            Log.d("SignUp", "Failed: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 요청에 실패했을 때의 코드
                        Log.d("SignUp", "Error: " + t.getMessage());
                    }
                });

            }
        });


    }
}