package com.herenit.armsapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.herenit.progressmanager.demo.ProgressManagerActivity;
import com.herenit.retrofiturlmanager.demo.RetrofitUrlManagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RetrofitUrlManager(View view) {
        startActivity(new Intent(this, RetrofitUrlManagerActivity.class));
    }

    public void ProgressManager(View view) {
        startActivity(new Intent(this, ProgressManagerActivity.class));
    }
}
