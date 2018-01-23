package com.tracy.jianaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_normal).setOnClickListener(mNormalListener);

        findViewById(R.id.tv_anonymous_innerClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("track_______", "anonymous innerClass Click is clicked");
            }
        });

        findViewById(R.id.tv_lambda).setOnClickListener(v -> Log.e("track_______", "lambda  Click is clicked"));
    }

    View.OnClickListener mNormalListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("track_______", "normal Click is clicked");
        }

        InnerClass innerClass = new InnerClass();
    };

    class InnerClass {
        InnerClass() {
            MainActivity.this.findViewById(R.id.tv_innerclass).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("track_______", "inner class Click is clicked");
                }
            });
        }
    }


}
