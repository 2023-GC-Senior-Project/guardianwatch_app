package com.example.guardianwatch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class FullScreenVideoActivity extends AppCompatActivity{
    private PlayerView fullScreenPlayerView;
    private SimpleExoPlayer player;
    private String videoUrl;
    ImageButton closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_full_screen_video);

        fullScreenPlayerView = findViewById(R.id.fullScreenPlayerView);

        videoUrl = getIntent().getStringExtra("VIDEO_URL");

        player = new SimpleExoPlayer.Builder(this).build();
        fullScreenPlayerView.setPlayer(player);

        MediaSource mediaSource = buildMediaSource(videoUrl);
        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

        long playbackPosition = getIntent().getLongExtra("PLAYBACK_POSITION", 0);
        player.seekTo(playbackPosition);


        closeButton = findViewById(R.id.closeFullscreenButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("PLAYBACK_POSITION", player.getCurrentPosition());
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
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
        if (player != null) {
            player.release();
        }
    }

}

