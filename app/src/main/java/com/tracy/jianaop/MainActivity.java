package com.tracy.jianaop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tech.track.Track;
import com.tech.track.TrackView;

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
            public void onClick(View view) {
                Log.e("track_______", "anonymous innerClass Click is clicked");
                String path = TrackView.getPath(view);
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
            }

        });

        this.findViewById(R.id.tv_lambda).setOnClickListener(v -> {
            Log.e("track_______", "lambda  Click is clicked");
            String path = TrackView.getPath(v);
            Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
        });
    }

    View.OnClickListener mNormalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("track_______", "normal Click is clicked");
        }

    };
}