package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class ChildRegisterActivity extends AppCompatActivity {

    // 이미지 요청 코드 상수
    private static final int IMAGE_REQUEST_CODE = 100;

    ImageView backArrow;

    RadioGroup radioGroupGender;
    ImageView profileImage;
    private Uri selectedImageUri;



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

        radioGroupGender = findViewById(R.id.radioGroupGender);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                int gender;
                if (selectedGenderId == R.id.radioButtonBoy) {
                    gender = 0; // 남자를 0으로 표시
                } else {
                    gender = 1; // 여자를 1로 표시
                }

                String name = nameEditText.getText().toString();
                String birthyear = yearEditText.getText().toString();
                String birthmonth = monthEditText.getText().toString();
                String birthday = dayEditText.getText().toString();
                String place = placeEditText.getText().toString();

                ChildData childData = new ChildData(name, birthyear, birthmonth, birthday, place, selectedImageUri.toString(), gender);

                // 결과를 MainActivity로 전달하는 방법 중 하나: 인텐트 사용
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newChild", childData);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
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