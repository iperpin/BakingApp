package com.example.bakingapp.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.objects.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment implements Player.EventListener {

    private static final String TAG = "VideoFragment";

    @BindView(R.id.video_player)
    PlayerView playerView;
    @BindView(R.id.stepTextView)
    TextView stepTextView;

    private SimpleExoPlayer player;
    private Uri videoUrl;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    public VideoFragment() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_video, container, false);

        ButterKnife.bind(this, view);

        Step step = getArguments().getParcelable(getString(R.string.intent_step_object));
        stepTextView.setText(step.getDescription());
        Log.d(TAG, step.getVideoURL());
        videoUrl = Uri.parse(step.getVideoURL());

        if (step.getVideoURL().equalsIgnoreCase("")){
            playerView.setVisibility(View.GONE);
            playerView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_image_grey600_48dp));
        }else {
            playerView.setVisibility(View.VISIBLE);

        }

        return view;
    }


    private void initializePlayer() {
        initializeMediaSession();

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                trackSelector, loadControl);
        playerView.setPlayer(player);
        player.addListener(this);

        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new MySessionCallback());

        mediaSession.setActive(true);

    }

    private void releasePlayer() {

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }

        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }
}