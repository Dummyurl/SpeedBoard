package com.anglll.speedboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SpeedBoardView speedBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedBoard = (SpeedBoardView) findViewById(R.id.view);
        speedBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speedBoard.setCount((int) (Math.random()*800),800);
            }
        });


    }
}
