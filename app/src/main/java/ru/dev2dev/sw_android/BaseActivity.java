package ru.dev2dev.sw_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()" );
    }
}
