package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private PowierzchniaRysunku rysunek;
    Button buttonBlue,buttonGreen,buttonRed,buttonClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rysunek = (PowierzchniaRysunku) findViewById(R.id.powierzchnia_rysunku);
        buttonBlue = findViewById(R.id.buttonBlue);
        buttonGreen = findViewById(R.id.buttonGreen);
        buttonRed = findViewById(R.id.buttonRed);
        buttonClear = findViewById(R.id.buttonClear);

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rysunek.setColor(-16776961);
            }
        });
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rysunek.setColor(-65536);
            }
        });
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rysunek.setColor(-16711936);
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rysunek.czysc();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        rysunek.wznowRysowanie();
    }
    @Override
    protected void onPause(){
        rysunek.pauzujRysowanie();
        super.onPause();
    }
}

