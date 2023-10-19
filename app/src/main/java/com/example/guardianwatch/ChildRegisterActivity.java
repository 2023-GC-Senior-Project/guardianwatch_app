package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildRegisterActivity extends AppCompatActivity {

    // 이미지 요청 코드 상수
    private static final int IMAGE_REQUEST_CODE = 100;

    ImageView backArrow;

    RadioGroup radioGroupGender;
    RadioButton girl;
    RadioButton boy;
    ImageView profileImage;
    private Uri selectedImageUri;

    int gender=-1;

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
        setContentView(R.layout.activity_child_register);

        girl=findViewById(R.id.radioButtonGirl);
        boy=findViewById(R.id.radioButtonBoy);
        radioGroupGender = findViewById(R.id.radioGroupGender);

//        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch(i) {
//                    case R.id.radioButtonBoy:
//                        gender = 0;
//                        Toast.makeText(ChildRegisterActivity.this, "남자 선택됨", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.radioButtonGirl:
//                        gender = 1;
//                        Toast.makeText(ChildRegisterActivity.this, "여자 선택됨", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });


        //뒤로가기 버튼 누를 시에 아이 리스트 페이지로 이동
        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChildListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileImage = findViewById(R.id.profile_image);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });



        Button registerButton = findViewById(R.id.registerBtn);
        EditText nameEditText=findViewById(R.id.nameEditText);
        EditText yearEditText=findViewById(R.id.yearEditText);
        EditText monthEditText=findViewById(R.id.monthEditText);
        EditText dayEditText=findViewById(R.id.dayEditText);
        EditText placeEditText=findViewById(R.id.placeEditText);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (girl.isChecked()==true) {
                    gender = 1; // 여자를 1로 표시
//                    Toast.makeText(ChildRegisterActivity.this, String.valueOf(gender), Toast.LENGTH_SHORT).show();
                } else if(boy.isChecked()==true){
                    gender = 0; // 남자를 0으로 표시
//                    Toast.makeText(ChildRegisterActivity.this, String.valueOf(gender), Toast.LENGTH_SHORT).show();

                }

                String name = nameEditText.getText().toString();
                String birthyear = yearEditText.getText().toString();
                String birthmonth = monthEditText.getText().toString();
                String birthday = dayEditText.getText().toString();
                String place = placeEditText.getText().toString();
                int represent=0;

//                ChildData childData = new ChildData(name, birthyear, birthmonth, birthday, place, selectedImageUri.toString(), gender,represent);

                // Retrofit API 호출
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://inclab3.gachon.ac.kr:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                Service service = retrofit.create(Service.class);
//                Call<ResponseBody> call = service.addChild(childData);
                Call<ResponseBody> call = service.addKid("test", name, Integer.toString(gender), birthyear, birthmonth, birthday, place, Integer.toString(represent));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChildRegisterActivity.this, "아이 정보 등록 성공!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ChildRegisterActivity.this,Integer.toString(gender) ,Toast.LENGTH_SHORT).show();
                            // 결과를 MainActivity로 전달하는 방법 중 하나: 인텐트 사용
//                            Intent resultIntent = new Intent();
//                            resultIntent.putExtra("newChild", childData);
//                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(ChildRegisterActivity.this, "아이 정보 등록 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ChildRegisterActivity.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("AddChild", "Error: " + t.getMessage());

                    }
                });
                // 결과를 MainActivity로 전달하는 방법 중 하나: 인텐트 사용
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("newChild", childData);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
        }
    }

}