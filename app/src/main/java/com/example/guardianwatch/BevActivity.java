package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.Calendar;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BevActivity extends AppCompatActivity {

    ImageView backArrow;
    TextView bevActivityDate;
    TextView bevActivityDay;
    ImageView ArrowLeft;
    ImageView ArrowRight;
    ImageView calendar;
    TextView longPlaceData;
    TextView longPersonData;
    TextView longActionData;
    PlayerView bevView;
    private SimpleExoPlayer player;
    private String userId;
    private String kidName;
    TextView fullScreenText;
    boolean isFullScreen = false;  // 전체 화면 여부를 추적하는 플래그
    String videoUrl;
    private static final int FULLSCREEN_REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bev);

        Intent kidInformation = getIntent();
        userId = kidInformation.getStringExtra("userId");
        kidName = kidInformation.getStringExtra("kidName");

        // 현재 날짜를 가져옴
        Calendar calendarTime = Calendar.getInstance();
        // 년도를 가져옴
        int year = calendarTime.get(Calendar.YEAR);
        // 월을 가져옴
        int month = calendarTime.get(Calendar.MONTH) + 1;
        // 일을 가져옴
        int day = calendarTime.get(Calendar.DAY_OF_MONTH);

        bevActivityDate = findViewById(R.id.bevActivityDate);
        bevActivityDay = findViewById(R.id.bevActivityDay);
        ArrowLeft = findViewById(R.id.ArrowLeft);
        ArrowRight = findViewById(R.id.ArrowRight);
        calendar = findViewById(R.id.calendar);
        longPlaceData = findViewById(R.id.longPlaceData);
        longPersonData = findViewById(R.id.longPersonData);
        longActionData = findViewById(R.id.longActionData);
        bevView = findViewById(R.id.bevView);

        player = new SimpleExoPlayer.Builder(this).build();
        bevView.setPlayer(player);
        fullScreenText = findViewById(R.id.fullScreenText);

        bevActivityDate.setText(year + "년 " + month + "월");
        bevActivityDay.setText(day + "");

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
                int year = calendarTime.get(Calendar.YEAR);
                int month = calendarTime.get(Calendar.MONTH) + 1;
                int day = calendarTime.get(Calendar.DAY_OF_MONTH);

                bevActivityDate.setText(year + "년 " + month + "월");
                bevActivityDay.setText(day + "");
                getBEV(userId, kidName, year, month, day, "ch02");
            }
        });

        // 다음 버튼의 리스너를 추가
        ArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 하루 다음날의 날짜를 가져옴
                calendarTime.add(Calendar.DATE, 1);
                int year = calendarTime.get(Calendar.YEAR);
                int month = calendarTime.get(Calendar.MONTH) + 1;
                int day = calendarTime.get(Calendar.DAY_OF_MONTH);

                bevActivityDate.setText(year + "년 " + month + "월");
                bevActivityDay.setText(day + "");
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 날짜 선택 다이얼로그를 띄운다.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BevActivity.this,
                        R.style.DatePickerDialogTheme,

                        (view, year, month, dayOfMonth) -> {
                            int monthT = month + 1;
                            bevActivityDate.setText(year + "년 " + monthT + "월");
                            bevActivityDay.setText(dayOfMonth + "");
                        },
                        calendarTime.get(Calendar.YEAR),
                        calendarTime.get(Calendar.MONTH),
                        calendarTime.get(Calendar.DAY_OF_MONTH)
                );
//                datePickerDialog.getDatePicker().setBackgroundColor(Color.parseColor("#D7E7F1"));
                datePickerDialog.show();
            }
        });
        //전체화면
        fullScreenText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreenIntent = new Intent(BevActivity.this, FullScreenVideoActivity.class);
                long playbackPosition = player.getCurrentPosition();
                fullScreenIntent.putExtra("VIDEO_URL", videoUrl);
                fullScreenIntent.putExtra("PLAYBACK_POSITION", playbackPosition);
                startActivityForResult(fullScreenIntent, FULLSCREEN_REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FULLSCREEN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            long playbackPosition = data.getLongExtra("PLAYBACK_POSITION", 0);
            player.seekTo(playbackPosition);
        }
    }
    private void getBEV(String id, String name, int year, int month, int day, String ch) {
        videoUrl = "http://210.102.178.157:8000/BEV/" + id + "/" + name + "/" + year + "/" + month + "/" + day + "/ch02";

        // 비디오 미디어 컨트롤러를 설정

        MediaSource mediaSource = buildMediaSource(videoUrl);
        player.setMediaSource(mediaSource);
        bevView.setUseController(true); // 기본 미디어 컨트롤러 사용

        player.prepare();
        player.setPlayWhenReady(true); // 재생 시작
    }

    private MediaSource buildMediaSource(String videoUrl) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "GuardianWatch"));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.release();
        }
    }

}
