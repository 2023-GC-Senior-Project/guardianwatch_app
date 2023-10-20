package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildListActivity extends AppCompatActivity {

    private static final int CHILD_REGISTER_REQUEST_CODE = 101;

    TextView childRegisterText;
    ImageView backArrow;
    TextView noChildText;
    Button editBtn;
    private boolean shouldRefresh = true;

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;

            // 첫 번째 아이템 위에 간격을 추가하려면
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalSpaceHeight;
            }
        }
    }

    static ArrayList<ChildData> childDataList = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);
        String userId = UserData.getInstance().getUserId();
        if (childDataList.isEmpty()) {
            fetchKidsData(UserData.getInstance().getUserId());
        }
        noChildText = findViewById(R.id.noChildText);
        editBtn = findViewById(R.id.editBtn);

        //대표아이 변경 버튼 누를시 대표 변경
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customAdapter.getSelectedPosition() != -1) {
                    ChildData selectedChild = childDataList.get(customAdapter.getSelectedPosition());

                    // Retrofit을 사용하여 API 호출
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://210.102.178.157:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Service service = retrofit.create(Service.class);
//                    Log.d("ChangeRepresent", selectedChild.getName());

                    Call<ResponseBody> call = service.changeRepresent(
                            userId,
                            selectedChild.getName()  // 대표 아이로 설정
                    );

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    // ResponseBody로부터 String 데이터를 한 번만 가져옵니다.
                                    String responseData = response.body().string();

                                    Log.d("ChangeRepresent", responseData);

                                    for (ChildData child : childDataList) {
                                        child.setRepresent(0);  // 모든 아이들의 represent를 0으로 설정
                                    }
                                    selectedChild.setRepresent(1);  // 선택된 아이의 represent를 1로 설정

                                    customAdapter.notifyDataSetChanged();
                                } catch (IOException e) {
                                    // 오류 메시지 처리
                                    Toast.makeText(ChildListActivity.this, "대표 아이를 설정하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // 네트워크 오류 처리
                            Toast.makeText(ChildListActivity.this, "네트워크 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ChildListActivity.this, "아이를 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //아이등록 누를시에 아이등록 페이지로 이동
        childRegisterText = findViewById(R.id.childRegister);
        childRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChildRegisterActivity.class);
                startActivityForResult(intent, CHILD_REGISTER_REQUEST_CODE);
            }
        });


        //뒤로가기 버튼 누를 시에 아이 리스트 페이지로 이동
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //아이 목록이 비었을경우
//        if(childDataList.isEmpty()) {
////            String uriString1 = "android.resource://" + getPackageName() + "/" + R.drawable.lee_image;
////
////            String uriString2 = "android.resource://" + getPackageName() + "/" + R.drawable.kim_image;
////            childDataList.add(new ChildData("김서준", "2018","7","28", "가천 어린이집",uriString2,0,0));
//            noChildText=findViewById(R.id.noChildText);
//            noChildText.setText("아직 등록된 아이가 없어요");
//        }
//        else{
//            noChildText.setText("");
//        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter = new CustomAdapter(childDataList);
        recyclerView.setAdapter(customAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        Intent intent = getIntent();
        if (intent != null) {
            ChildData editData = (ChildData) intent.getSerializableExtra("editData");
            int position = intent.getIntExtra("position", -1);
            if (position != -1) {
                // 편집된 내용을 ChildListActivity의 childDataList에 반영합니다.
                childDataList.set(position, editData);

                // RecyclerView를 새로고침합니다.
                customAdapter.notifyDataSetChanged();
            }
        }
    }
//
//    public class SharedViewModel extends ViewModel {
//        private final MutableLiveData<Boolean> isRepresentativeChanged = new MutableLiveData<>();
//
//        public MutableLiveData<Boolean> getIsRepresentativeChanged() {
//            return isRepresentativeChanged;
//        }
//
//        public void representativeChanged() {
//            isRepresentativeChanged.setValue(true);
//        }
//
//        public void resetRepresentativeChanged() {
//            isRepresentativeChanged.setValue(false);
//        }
//    }

    //아이 목록 불러오기
    public void fetchKidsData(String userId) {

        // Retrofit API 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://210.102.178.157:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = service.getKids(userId);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // ResponseBody로부터 String 데이터를 한 번만 가져옵니다.
                        String responseData = response.body().string();

                        // 로그에 responseData를 출력합니다.
                        Log.d("list_kid", responseData);

                        // String 데이터를 원하는 데이터 형식 (ChildData의 리스트)으로 변환합니다.
                        // 여기서는 Gson을 사용하여 JSON 문자열을 ChildData 리스트로 변환하는 예를 제공합니다.
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ChildData>>() {
                        }.getType();
                        List<ChildData> kidsList = gson.fromJson(responseData, listType);

                        childDataList.clear();
                        childDataList.addAll(kidsList);
//                        Log.d("list_kid", kidsList.toString());


                        customAdapter.notifyDataSetChanged();

//// ViewModel 선언
//                        SharedViewModel sharedViewModel = new ViewModelProvider(ChildListActivity.this).get(SharedViewModel.class);
//
//// 대표 아이 변경 성공 시
//                        // ViewModel의 변경 사항 관찰
//                        sharedViewModel.getIsRepresentativeChanged().observe(ChildListActivity.this, isChanged -> {
//                            if (isChanged) {
//                                // ViewPager2의 아이 데이터를 새로고침
//                                fetchKidsData(UserData.getInstance().getUserId());
//                                sharedViewModel.resetRepresentativeChanged();
//                            }
//                        });
//
//// 대표 아이 변경 성공 시
//                        SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean("represent_changed", true);
//                        editor.apply();

                        // 여기서 noChildText를 업데이트합니다.
//                        if (childDataList.isEmpty()) {
//                            noChildText.setText("아직 등록된 아이가 없어요");
//                            noChildText.setVisibility(View.VISIBLE);
//                        } else {
//                            noChildText.setText("");
//                            noChildText.setVisibility(View.GONE);
//                        }

                    } catch (Exception e) {
                        Toast.makeText(ChildListActivity.this, "데이터 파싱에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChildListActivity.this, "데이터를 가져오는데 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChildListActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldRefresh) {
            fetchKidsData(UserData.getInstance().getUserId());
        }
        shouldRefresh = true;  // Reset the flag for next time
//        fetchKidsData(UserData.getInstance().getUserId()); // 화면에 다시 돌아올 때마다 아이 목록을 다시 가져오기

        SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        boolean isRepresentChanged = sharedPreferences.getBoolean("represent_changed", false);
        if (isRepresentChanged) {
            // ViewPager2의 아이 데이터를 새로고침
            fetchKidsData(UserData.getInstance().getUserId());
            // reset the flag
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("represent_changed", false);
            editor.apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHILD_REGISTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ChildData newChild = (ChildData) data.getSerializableExtra("newChild");
            childDataList.add(newChild);
            customAdapter.notifyDataSetChanged();
            shouldRefresh = false;  // refresh하지 않음

        }

    }


}