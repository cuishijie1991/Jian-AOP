package com.tracy.jianaop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tech.track.Track;

public class MainActivity extends Activity {


    @Override
    protected void onResume() {
        super.onResume();
        Track.trackPageStart("MainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.tv_normal).setOnClickListener(mNormalListener);

        this.findViewById(R.id.tv_anonymous_innerClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("track_______", "anonymous innerClass Click is clicked");
            }
        });

        this.findViewById(R.id.tv_lambda).setOnClickListener(v -> Log.e("track_______", "lambda  Click is clicked"));
    }

    View.OnClickListener mNormalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("track_______", "normal Click is clicked");
        }

    };

}
