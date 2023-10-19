package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class EditProfileActivity extends AppCompatActivity {

    ImageView backArrow;
    ImageView profile_image;
    EditText nameEditText;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGirl;
    RadioButton radioButtonBoy;
    EditText yearEditText;
    EditText monthEditText;
    EditText dayEditText;
    EditText placeEditText;
    Button editBtn;
    int gender;
    RadioButton girl,boy;
    String userId;
    String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backArrow=findViewById(R.id.backArrow);
        profile_image=findViewById(R.id.profile_image);
        nameEditText=findViewById(R.id.nameEditText);
        radioGroupGender=findViewById(R.id.radioGroupGender);
        radioButtonGirl=findViewById(R.id.radioButtonGirl);
        radioButtonBoy=findViewById(R.id.radioButtonBoy);
        yearEditText=findViewById(R.id.yearEditText);
        monthEditText=findViewById(R.id.monthEditText);
        dayEditText=findViewById(R.id.dayEditText);
        placeEditText=findViewById(R.id.placeEditText);
        editBtn=findViewById(R.id.editBtn);

        girl=findViewById(R.id.radioButtonGirl);
        boy=findViewById(R.id.radioButtonBoy);

        userId = UserData.getInstance().getUserId(); // 현재 사용자 ID 가져오기
        childName = getIntent().getStringExtra("childName");
        fetchKidData(userId, childName); // 아이의 정보 로드하기

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChildListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = UserData.getInstance().getUserId(); // 현재 사용자 ID 가져오기
                childName = getIntent().getStringExtra("childName");
                String year = yearEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String day = dayEditText.getText().toString();
                String imageUri = String.valueOf(profile_image.getDrawable());
                if(imageUri == null)
                    imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.default_profile;

                if (girl.isChecked()==true) {
                    gender = 1; // 여자를 1로 표시
//                    Toast.makeText(ChildRegisterActivity.this, String.valueOf(gender), Toast.LENGTH_SHORT).show();
                } else if(boy.isChecked()==true){
                    gender = 0; // 남자를 0으로 표시
//                    Toast.makeText(ChildRegisterActivity.this, String.valueOf(gender), Toast.LENGTH_SHORT).show();

                }
                String place = placeEditText.getText().toString();
                int represent=0;
                updateKidData(userId, childName,Integer.toString(gender), year, month, day, place, Integer.toString(represent));
                Intent intent = new Intent(getApplicationContext(), ChildListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void fetchKidData(String userId,String childName) {

        // Retrofit API 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inclab3.gachon.ac.kr:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.getKid(userId,childName);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // ResponseBody로부터 String 데이터를 한 번만 가져옵니다.
                        String responseData = response.body().string();

                        // 로그에 responseData를 출력합니다.
                        Log.d("edit_kid", responseData);

                        // String 데이터를 원하는 데이터 형식으로 변환합니다.
                        // 여기서는 Gson을 사용하여 JSON 문자열을 ChildData로 변환하는 예를 제공합니다.
                        Gson gson = new Gson();
                        Type type = new TypeToken<ChildData>() {}.getType();
                        ChildData childData = gson.fromJson(responseData, type);

                        // UI 업데이트
//                        if(imageUri == null)
//                            imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.default_profile;

//                        profile_image.setImageURI(Uri.parse(childData.getImageUri()));
                        profile_image.setImageResource(R.drawable.default_profile);

                        nameEditText.setText(childData.getName());
                        gender = childData.getGender();
                        if(gender == 0){
                            radioButtonBoy.setChecked(true);
                            radioButtonGirl.setChecked(false);
                        } else {
                            radioButtonBoy.setChecked(false);
                            radioButtonGirl.setChecked(true);
                        }
                        yearEditText.setText(childData.getYear());
                        monthEditText.setText(childData.getMonth());
                        dayEditText.setText(childData.getDay());
                        placeEditText.setText(childData.getPlace());

                    } catch (Exception e) {
                        Toast.makeText(EditProfileActivity.this, "데이터 파싱에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "데이터를 가져오는데 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateKidData(String userId, String childName, String gender, String year, String month, String day, String place, String represent){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inclab3.gachon.ac.kr:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.editKid(userId, childName, userId, childName, gender, year, month, day, place, null, represent);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        // ResponseBody로부터 String 데이터를 한 번만 가져옵니다.
                        String responseData = response.body().string();

                        Log.d("EditChild", responseData);
//                      로그에 responseData를 출력합니다.
                        Toast.makeText(EditProfileActivity.this, "아이 정보 수정 성공!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditProfileActivity.this,gender ,Toast.LENGTH_SHORT).show();
                        // 결과를 MainActivity로 전달하는 방법 중 하나: 인텐트 사용
//                            Intent resultIntent = new Intent();
//                            resultIntent.putExtra("newChild", childData);
//                            setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        responseData = response.body().string();
                    } catch (IOException e) {
                        Toast.makeText(EditProfileActivity.this, "데이터 파싱에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(EditProfileActivity.this, "아이 정보 수정 실패.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("EditChild", "Error: " + t.getMessage());

            }
        });
        // 결과를 MainActivity로 전달하는 방법 중 하나: 인텐트 사용
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("newChild", childData);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
    }
//});
//    }
}