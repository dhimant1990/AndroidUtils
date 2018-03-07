package com.dhims.sampleutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dhims.androidutils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.e("Testing Android Utils");
    }
}
