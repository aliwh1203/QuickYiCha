package com.aliwh.android.quickyicha.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class TvLiveActivity extends BaseActivity {

    private static final String TAG = TvLiveActivity.class.getSimpleName();
    private static final int RETRY_YIMES = 5;
    private static final int AUTO_HIDE_TIME = 5000;
    private int mCount = 0;

    private String mUrl;
    private String mTitle;
    private ImageView mBackButton;
    private TextView mTitleText;
    private TextView mSysTime;
    private VideoView mVideoView;
    private RelativeLayout mLoadingLayout;

    private RelativeLayout mRootView;
    private LinearLayout mTopLayout;
    private LinearLayout mBottomLayout;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ImageView mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_live);
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        Log.d(TAG,">> onCreate mTitle=" + mTitle + ", mUrl=" + mUrl);
        initView();
        initPlayer();
    }

    private void initPlayer() {
        Vitamio.isInitialized(getApplicationContext());
        mVideoView = (VideoView) findViewById(R.id.surface_view);

        mPlayButton = (ImageView) findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVideoView.isPlaying()) {
                    mVideoView.stopPlayback();
                    mPlayButton.setImageResource(R.mipmap.play);
                } else {
                    mVideoView.setVideoURI(Uri.parse(mUrl));
                    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mVideoView.start();
                        }
                    });
                    mPlayButton.setImageResource(R.mipmap.piaying);
                }

            }
        });

        mVideoView.setVideoURI(Uri.parse(mUrl));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();

            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mCount > RETRY_YIMES) {
                    new AlertDialog.Builder(TvLiveActivity.this)
                            .setMessage(R.string.error_massage)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TvLiveActivity.this.finish();
                                }
                            }).setCancelable(false).show();
                } else {
                    mVideoView.stopPlayback();
                    mVideoView .setVideoURI(Uri.parse(mUrl));
                }
                mCount++;
                return false;
            }
        });

        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLoadingLayout.setVisibility(View.VISIBLE);
                    break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        mLoadingLayout.setVisibility(View.GONE);
                    break;
                }
                return false;
            }
        });


    }

    private void initView() {
        mBackButton = (ImageView) findViewById(R.id.iv_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitleText = (TextView) findViewById(R.id.tv_title);
        mTitleText.setText(mTitle);

        mSysTime = (TextView) findViewById(R.id.tv_systime);
        mSysTime.setText(getCurrentTime());

        mLoadingLayout = (RelativeLayout) findViewById(R.id.rl_loading_layout);

        mRootView = (RelativeLayout) findViewById(R.id.activivity_live);
        mTopLayout = (LinearLayout) findViewById(R.id.ll_top_layout);
        mBottomLayout = (LinearLayout) findViewById(R.id.ll_bottom_layout);

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomLayout.getVisibility() == View.VISIBLE
                         || mTopLayout.getVisibility() == View.VISIBLE) {
                    mBottomLayout.setVisibility(View.GONE);
                    mTopLayout.setVisibility(View.GONE);
                    return;
                }

                if (mVideoView.isPlaying()) {
                    mPlayButton.setImageResource(R.mipmap.piaying);
                } else {
                    mPlayButton.setImageResource(R.mipmap.play);
                }
                mTopLayout.setVisibility(View.VISIBLE);
                mBottomLayout.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBottomLayout.setVisibility(View.GONE);
                        mTopLayout.setVisibility(View.GONE);
                    }
                }, AUTO_HIDE_TIME);

            }
        });

    }
    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String time = sdf.format(c.getTime());
        return time;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCount = 0;
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }
}
