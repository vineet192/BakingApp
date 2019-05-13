package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoFragment extends Fragment {
    SimpleExoPlayerView mExoplayerView;
    SimpleExoPlayer mExoplayer;
    private String mUrl;
    private String mDescription;
    OnNextClickListener mNextListener;
    OnPrevclickListener mPrevListener;
    Button mPrevButton;
    Button mNextButton;
    boolean mIsTablet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recipe_step_video, container, false);
        TextView recipeDescritionTextView = (TextView) rootView.findViewById(R.id.recipe_desc_text_view);
        mPrevButton = (Button) rootView.findViewById(R.id.prev_button);
        mNextButton = (Button) rootView.findViewById(R.id.next_button);
        mExoplayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video_exo_player);
        checkFullScreen();

        //Setting description
        recipeDescritionTextView.setText(mDescription);

        //Initialising exoplayer
        if (mUrl != null && !mUrl.equals(""))
            initialisePlayer();
        else
            mExoplayerView.setVisibility(View.GONE);

        //Setting up button controls.
        if(mIsTablet)
        {
            mNextButton.setVisibility(View.GONE);
            mPrevButton.setVisibility(View.GONE);
        }
        else {
            mPrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPrevListener.OnClickPrev();
                }
            });

            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNextListener.onClickNext();
                }
            });
        }


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoplayer != null)
            releasePlayer();

    }

    public interface OnNextClickListener {
        void onClickNext();
    }

    public interface OnPrevclickListener {
        void OnClickPrev();
    }

    private void initialisePlayer() {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mExoplayerView.setPlayer(mExoplayer);
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mUrl), new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoplayer.prepare(mediaSource);
        mExoplayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        mExoplayer.stop();
        mExoplayer.release();
        mExoplayer = null;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setTabletMode(boolean isTablet)
    {
        mIsTablet = isTablet;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mNextListener = (OnNextClickListener) context;
            mPrevListener = (OnPrevclickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement both OnNextClickListener and OnPrevClickListener");
        }
    }

    private void checkFullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
            mExoplayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mExoplayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }
    }

    private void hideSystemUI() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        //Use Google's "LeanBack" mode to get fullscreen in landscape
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
