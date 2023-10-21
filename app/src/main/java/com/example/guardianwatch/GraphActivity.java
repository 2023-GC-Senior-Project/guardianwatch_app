package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.json.JSONArray;
import org.json.JSONObject;

public class GraphActivity extends AppCompatActivity {

    ImageView backArrow;
    TextView graphActivityDate;
    TextView graphActivityDay;
    ImageView ArrowLeft;
    ImageView ArrowRight;
    TextView graphSentence1;
    TextView graphSentence2;
    TextView graphSentence3;
    ImageView heatmap;
    ImageView useKcal;
    ImageView moveDistance;
    ImageView calendar;
    ImageView graphView;
    TextView noGraphText;
    private String userId;
    private String kidName;

    private int year;
    private int month;
    private int day;
    int flag = 1;
    String usekcal,usekcalPercent;
    String distance, distancePercent;
    Bitmap bitmap = null;
    //getAnalysis 함수가 처음 불렸을때
    private boolean isFirstCall = true;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Intent kidInformation = getIntent();



        userId = kidInformation.getStringExtra("userId");
        kidName = kidInformation.getStringExtra("kidName");
//        Log.d("Heatmap", userId);
//        Log.d("Heatmap", kidName);

        // 현재 날짜를 가져옴
        Calendar calendarTime = Calendar.getInstance();
        // 년도를 가져옴
        year = calendarTime.get(Calendar.YEAR);
        // 월을 가져옴
        month = calendarTime.get(Calendar.MONTH) + 1;
        // 일을 가져옴
        day = calendarTime.get(Calendar.DAY_OF_MONTH);
        // 초기 데이터값 가져오기 위해 getAnaylsis 호출
//        getAnalysis(userId, kidName, year, month, day, "ch02");
        graphActivityDate = findViewById(R.id.graphActivityDate);
        graphActivityDay = findViewById(R.id.graphActivityDay);
        ArrowLeft = findViewById(R.id.ArrowLeft);
        ArrowRight = findViewById(R.id.ArrowRight);
        graphSentence1 = findViewById(R.id.graphSentence1);
        graphSentence2 = findViewById(R.id.graphSentence2);
        graphSentence3 = findViewById(R.id.graphSentence3);
        heatmap = findViewById(R.id.heatmap);
        useKcal = findViewById(R.id.useKcal);
        moveDistance = findViewById(R.id.moveDistance);
        calendar = findViewById(R.id.calendar);
        graphView = findViewById(R.id.graphView);

        noGraphText = findViewById(R.id.noGraphText);
        noGraphText.setVisibility(View.GONE);
//        // 이미지 뷰를 흰색으로 설정
//        graphView.setBackgroundColor(Color.WHITE);
        graphActivityDate.setText(year + "년 " + month + "월");
        graphActivityDay.setText(day + "");

//        graphSentence1.setText("특정 장소에 오래 머물러 있었습니다.");

        //뒤로가기 버튼 누를 시에 로그인 페이지로 이동
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 이전 버튼의 리스너를 추가
        ArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 하루 전날의 날짜를 가져옴
                calendarTime.add(Calendar.DATE, -1);
                year = calendarTime.get(Calendar.YEAR);
                month = calendarTime.get(Calendar.MONTH) + 1;
                day = calendarTime.get(Calendar.DAY_OF_MONTH);

                graphActivityDate.setText(year + "년 " + month + "월");
                graphActivityDay.setText(day + "");
                if (flag == 1) { // heatmap
                    getMap(userId, kidName, year, month, day, "heatmap");
                } else if (flag == 3) {
                    getMap(userId, kidName, year, month, day, "pathmap");
                } else if (flag == 2) {
                    getAnalysis(userId, kidName, year, month, day, "ch02");
                } else {
                    graphView.setImageResource(R.drawable.rectangle0);
                    noGraphText.setVisibility(View.VISIBLE);
                }
            }
        });

        // 다음 버튼의 리스너를 추가
        ArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 하루 다음날의 날짜를 가져옴
                calendarTime.add(Calendar.DATE, 1);
                year = calendarTime.get(Calendar.YEAR);
                month = calendarTime.get(Calendar.MONTH) + 1;
                day = calendarTime.get(Calendar.DAY_OF_MONTH);

                graphActivityDate.setText(year + "년 " + month + "월");
                graphActivityDay.setText(day + "");
                Log.d("map", String.valueOf(flag));
                if (flag == 1) { // heatmap
                    getMap(userId, kidName, year, month, day, "heatmap");
                } else if (flag == 3) {
                    getMap(userId, kidName, year, month, day, "pathmap");
                } else if (flag == 2) {
                    getAnalysis(userId, kidName, year, month, day, "ch02");
                } else {
                    graphView.setImageResource(R.drawable.rectangle0);
                    noGraphText.setVisibility(View.VISIBLE);
                }
            }
        });

        heatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heatmap.setImageResource(R.drawable.rectangle11);
                useKcal.setImageResource(R.drawable.rectangle7);
                moveDistance.setImageResource(R.drawable.rectangle7);

//                graphSentence1.setText("가장 어두운 곳에 가장 오래 머물렀어요.");
//                graphSentence2.setText("");
//                graphSentence3.setText("");
                flag = 1;
                getMap(userId, kidName, year, month, day, "heatmap");
            }

        });

        useKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heatmap.setImageResource(R.drawable.rectangle7);
                useKcal.setImageResource(R.drawable.rectangle12);
                moveDistance.setImageResource(R.drawable.rectangle7);
             flag = 2;
                getAnalysis(userId, kidName, year, month, day, "ch02");

            }
        });

        moveDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heatmap.setImageResource(R.drawable.rectangle7);
                useKcal.setImageResource(R.drawable.rectangle7);
                moveDistance.setImageResource(R.drawable.rectangle10);


                flag = 3;
                getMap(userId, kidName, year, month, day, "pathmap");
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 날짜 선택 다이얼로그를 띄운다.
                DatePickerDialog datePickerDialog = new DatePickerDialog(GraphActivity.this, R.style.DatePickerDialogTheme,
                        (view, year, month, dayOfMonth) -> {
                            int monthT = month + 1;

                            graphActivityDate.setText(year + "년 " + monthT + "월");
                            graphActivityDay.setText(dayOfMonth + "");

                            if (flag == 1) { // heatmap
                                getMap(userId, kidName, year, monthT, dayOfMonth, "heatmap");
                            } else if (flag == 2) { //analysis(kcal)
                                getAnalysis(userId, kidName, year, monthT, dayOfMonth, "ch02");
                            } else if (flag == 3) { //pathmap
                                getMap(userId, kidName, year, monthT, dayOfMonth, "pathmap");
                            }
                            else {
                                graphView.setImageResource(R.drawable.rectangle0);
                            }

                        },
                        calendarTime.get(Calendar.YEAR),
                        calendarTime.get(Calendar.MONTH),
                        calendarTime.get(Calendar.DAY_OF_MONTH)

                );
//                datePickerDialog.getDatePicker().setBackgroundColor(Color.parseColor("#D7E7F1"));
                datePickerDialog.show();
            }
        });

    }

    public void getAnalysis(String id, String name, int year, int month, int day, String ch) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://210.102.178.157:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = null;
        call = service.getAnalysis(
                id, name,
                Integer.toString(year),
                Integer.toString(month),
                Integer.toString(day),
                "ch02"
        );
        // ProgressBar
        ProgressBar loadingIndicator = findViewById(R.id.loadingIndicator);
        // API 호출 시작 전에 로딩 인디케이터 표시
        loadingIndicator.setVisibility(View.VISIBLE);
        noGraphText.setVisibility(View.GONE);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 응답 받은 후 로딩 인디케이터 숨기기
                loadingIndicator.setVisibility(View.GONE);
                noGraphText.setVisibility(View.VISIBLE); // "아직 없음" 텍스트 보이기

                if (response.isSuccessful()) {
                    try {

//                        // 이미지가 준비되면 ImageView 다시 보여주기
//                        graphView.setVisibility(View.VISIBLE);

                        String responseData = response.body().string();
//                        Log.d("HTTP_RESPONSE", responseData);

                        JSONArray jsonArray = new JSONArray(responseData);
                        byte[] decodedImage = new byte[0];
//                        if (jsonArray.length() == 0) {
//                            graphView.setImageResource(R.drawable.rectangle0);
//                        }
//                        if(isFirstCall==true){
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                // JSON 객체 내의 특정 키에 대한 값을 가져와서 사용
////                            String ch = jsonObject.getString("ch");
//                                Log.d("Heatmap Json", jsonObject.toString());
//
////                                String map = jsonObject.getString("chart");
//                                distance = jsonObject.getString("distance");
//                                usekcal = jsonObject.getString("usekcal");
//                                usekcalPercent = jsonObject.getString("usekcal_percent");
//                                distancePercent = jsonObject.getString("distance_percent");
////                                Log.d("Heatmap Json m", map);
//                                Log.d("Heatmap Json d", distance);
//                                isFirstCall = false;
////                                graphSentence1.setText("오늘 하루동안 "+usekcal+"kcal 소비하였습니다.");
////                                graphSentence2.setText("또래 평균의 "+usekcalPercent+"% 소비하였습니다.");
//
////                                String base64Image = map.replaceFirst("data:image\\/jpg;base64,", "");
////                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                                    decodedImage = Base64.getDecoder().decode(base64Image);
////                                }
//
////                                bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
////                                graphView.setImageBitmap(bitmap);
////                                graphView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                            }
//                        }else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                // JSON 객체 내의 특정 키에 대한 값을 가져와서 사용
//                            String ch = jsonObject.getString("ch");
                                Log.d("Heatmap Json", jsonObject.toString());

                                String map = jsonObject.getString("chart");
                                distance = jsonObject.getString("distance");
                                usekcal = jsonObject.getString("usekcal");
                                usekcalPercent = jsonObject.getString("usekcal_percent");
                                distancePercent = jsonObject.getString("distance_percent");
                                Log.d("Heatmap Json m", map);
                                Log.d("Heatmap Json d", distance);

                                graphSentence1.setText("오늘 하루동안 " + usekcal + "kcal 소비하였습니다.");
                                graphSentence2.setText("또래 평균의 " + usekcalPercent + "% 소비하였습니다.");

                                String base64Image = map.replaceFirst("data:image\\/jpg;base64,", "");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    decodedImage = Base64.getDecoder().decode(base64Image);
                                }

                                bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                                graphView.setImageBitmap(bitmap);
                                graphView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }

                            if (jsonArray.length() == 0) {
                                graphView.setVisibility(View.GONE); // 이미지 뷰 숨기기
                                noGraphText.setVisibility(View.VISIBLE); // "아직 없음" 텍스트 보이기
                            } else {
                                graphView.setVisibility(View.VISIBLE); // 이미지 뷰 보이기
                                noGraphText.setVisibility(View.GONE); // "아직 없음" 텍스트 숨기기

                            }
//                        }
                    } catch (Exception e) {
                        Log.e("GraphActivity", "Response not successful: " + response.code() + " - " + response.message());
                        // 오류 메시지 처리
                        Log.d("Heatmap", e.toString());
//                        Toast.makeText(GraphActivity.this, "분석 map을 불러오는 데 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 실패한 경우에도 로딩 인디케이터 숨기기
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(GraphActivity.this, "네트워크 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMap(String id, String name, int year, int month, int day, String mode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://210.102.178.157:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<ResponseBody> call = null;

        if (mode == "pathmap") {
            call = service.getPathMap(
                    id, name,
                    Integer.toString(year),
                    Integer.toString(month),
                    Integer.toString(day)
            );
        } else if (mode == "heatmap") {
            call = service.getHeatMap(
                    id, name,
                    Integer.toString(year),
                    Integer.toString(month),
                    Integer.toString(day)
            );
        }
        // ProgressBar
        ProgressBar loadingIndicator = findViewById(R.id.loadingIndicator);
        // API 호출 시작 전에 로딩 인디케이터 표시
        loadingIndicator.setVisibility(View.VISIBLE);
        noGraphText.setVisibility(View.GONE);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 응답 받은 후 로딩 인디케이터 숨기기
                loadingIndicator.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
//                        // 이미지가 준비되면 ImageView 다시 보여주기
//                        graphView.setVisibility(View.VISIBLE);

                        String responseData = response.body().string();

                        JSONArray jsonArray = new JSONArray(responseData);
                        Log.d("Heatmap", String.valueOf(jsonArray.length()));
                        byte[] decodedImage = new byte[0];
                        if (jsonArray.length() == 0) {
                            graphView.setImageResource(R.drawable.rectangle0);
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            // JSON 객체 내의 특정 키에 대한 값을 가져와서 사용
//                            String ch = jsonObject.getString("ch");
                            String map = jsonObject.getString(mode.toString());
                            String hour = jsonObject.getString("hour");
                            String minute = jsonObject.getString("minute");
                            Log.d("Heatmap", String.valueOf(heatmap));

                            String base64Image = map.replaceFirst("data:image/jpg;base64,", "");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                decodedImage = Base64.getDecoder().decode(base64Image);
                            }

                            bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                            graphView.setImageBitmap(bitmap);
                            graphView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }

                        if (jsonArray.length() == 0) {
                            graphView.setVisibility(View.GONE); // 이미지 뷰 숨기기
                            noGraphText.setVisibility(View.VISIBLE); // "아직 없음" 텍스트 보이기
                        } else {
                            graphView.setVisibility(View.VISIBLE); // 이미지 뷰 보이기
                            noGraphText.setVisibility(View.GONE); // "아직 없음" 텍스트 숨기기
                            Float distanceMeter= Float.parseFloat(distance)/100;
                            if(mode=="pathmap"){
                                graphSentence1.setText("오늘 하루동안 "+distanceMeter+"m 이동하였습니다.");
                                graphSentence2.setText("또래 평균의 "+distancePercent+"% 이동하였습니다.");

                            }
                            else if(mode=="heatmap"){
                                graphSentence1.setText("가장 어두운 곳에 가장 오래 머물렀어요.");
                                graphSentence2.setText("");
                                graphSentence3.setText("");
                            }
                        }

                    } catch (Exception e) {
                        Log.e("GraphActivity", "Response not successful: " + response.code() + " - " + response.message());

                        // 오류 메시지 처리
                        Log.d("Heatmap", e.toString());
//                        Toast.makeText(GraphActivity.this, "분석 map을 불러오는 데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 실패한 경우에도 로딩 인디케이터 숨기기
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(GraphActivity.this, "네트워크 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
