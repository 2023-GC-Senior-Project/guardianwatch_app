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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        signUpText = findViewById(R.id.text2);
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
                parentView = dialog.findViewById(R.id.parentView);
                teacherView = dialog.findViewById(R.id.teacherView);
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
                nextText = dialog.findViewById(R.id.nextText);
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
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText idEditText = findViewById(R.id.idEditText); // 아이디 입력 필드 ID를 수정해야 합니다.
                EditText passwordEditText = findViewById(R.id.pwEditText); // 비밀번호 입력 필드 ID를 수정해야 합니다.

                String userId = idEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://210.102.178.157:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Service service = retrofit.create(Service.class);
                UserData user = new UserData(userId, password);

                Call<ResponseBody> call = service.loginUser(user);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            UserData.getInstance().setUserId(userId);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // 요청에 실패했을 때의 코드
                        Log.d("SignUp", "Error: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "로그인 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();

            }
        });

    }
}