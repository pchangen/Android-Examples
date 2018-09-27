package com.pce_mason.qi.bottomseetbehaviortest;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button half_btn, ful_btn, collapse_btn;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottomSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collapse_btn = (Button) findViewById(R.id.collapsed);
        collapse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change current state to initialize state (like a to peek height)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        ful_btn = (Button) findViewById(R.id.expanded);
        ful_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change current state to full layout size state
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        half_btn = (Button) findViewById(R.id.half_expanded);
        half_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change current state to half layout size state
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        bottomSheet = (LinearLayout) findViewById(R.id.bottomSheetBehaviorLayout);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        //if you input like a bottom. the 100 is px not dp
        //       bottomSheetBehavior.setPeekHeight(100);
        float peekHeightDp = 72.0f;
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, peekHeightDp, getResources().getDisplayMetrics()));
    }
}
