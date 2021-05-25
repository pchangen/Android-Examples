package com.mason.realtimebarchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BarChart custom_barchart;
    TextView txt_data;
    ValueHandler handler = new ValueHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        custom_barchart = (BarChart) findViewById(R.id.custom_barchart);
        txt_data = (TextView) findViewById(R.id.txt_data);

        //추가
        chartInitialize();
        ////

        BackgroundThread thread = new BackgroundThread();
        thread.start();

    }

    //추가
    private void addData(int value){

        if (custom_barchart.getData() != null &&
                custom_barchart.getData().getDataSetCount() > 0) {

            BarDataSet dataSet = (BarDataSet) custom_barchart.getData().getDataSetByIndex(0);
            BarEntry values = new BarEntry(custom_barchart.getData().getEntryCount(), value);
            dataSet.addEntry(values);

            custom_barchart.getData().notifyDataChanged();
            custom_barchart.notifyDataSetChanged();
            custom_barchart.setVisibleXRangeMaximum(10);    //10개를 5개로 줄이면 화면에 5개 까지만 나오고
            custom_barchart.moveViewToX(custom_barchart.getData().getEntryCount());     //최신꺼가 오면 좌측으로 밀리는 형태
            custom_barchart.invalidate();

        }else{
            BarDataSet set1;
            ArrayList<BarEntry> values = new ArrayList<>();
            values.add(new BarEntry(0, value));
            set1 = new BarDataSet(values, "미세먼지");      //왼쪽 아래 레전드와 관련된 것 미세먼지 라는 부분 수정 하면 바뀜

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);     //바 위에 숫자 크기
            data.setBarWidth(0.9f);

            custom_barchart.setData(data);
            custom_barchart.invalidate();
        }
    }
    ////


    //추가
    private void chartInitialize() {

        //해당 부분은 원하는 부분만 바꿔가면서 골라서 쓰면 됩니다.

//        custom_barchart.setBackgroundColor(Color.WHITE);
        custom_barchart.getDescription().setEnabled(false);

        custom_barchart.setDragEnabled(true);
        custom_barchart.setDrawBarShadow(false);
        custom_barchart.setDrawValueAboveBar(true);
        custom_barchart.setPinchZoom(true);
        custom_barchart.setDrawGridBackground(false);
        custom_barchart.setScaleEnabled(true);
        custom_barchart.setTouchEnabled(true);


        Legend l = custom_barchart.getLegend();
        l.setEnabled(false);

        custom_barchart.getAxisLeft().setEnabled(false);
        custom_barchart.getAxisLeft().setSpaceTop(40);
        custom_barchart.getAxisLeft().setSpaceBottom(40);
        custom_barchart.getAxisRight().setEnabled(false);
        custom_barchart.getXAxis().setEnabled(false);
    }

   ////



    //아래는 계속 값 임의로 넣어주려고 만든 것. 추가할 필요 없음.
    //실제로 적용할때는 아래의 addData(value)를 블루투스 값 받는 부분에 맞춰서 해주면 되고, value는 수신한 값으로 변경
    //value는 int형인데 float으로 바꾸고 싶다면, 위에 private void addData(int value){ 여기서 int를 float으로 바꾸면 됨
    class BackgroundThread extends Thread {
        int value = 0;
        boolean running = false;
        public void run() {
            running = true;
            while(running) {
                value = (int)(Math.random() * 50);
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value",value);
                message.setData(bundle);
                handler.sendMessage(message);



                //추가
                addData(value);
                ////


                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }
        }
    }

    class ValueHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            txt_data.setText("현재 값 : " + value);
        }
    }
}