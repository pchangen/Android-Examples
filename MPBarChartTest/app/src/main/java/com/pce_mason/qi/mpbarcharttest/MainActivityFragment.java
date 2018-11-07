package com.pce_mason.qi.mpbarcharttest;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    LineChart testLineChart;
    Timer updateTimer;
    TimerTask timerTask;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        testLineChart = view.findViewById(R.id.chart);
        chartInitialize();

        updateTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                addEntry();
            }
        };
        updateTimer.schedule(timerTask,0,10);

        return view;
    }

    private void chartInitialize(){
        testLineChart.setData(new LineData());
        testLineChart.getDescription().setEnabled(false);
        testLineChart.setDrawGridBackground(false);
        testLineChart.setBackgroundColor(Color.WHITE);
        testLineChart.setViewPortOffsets(10, 0, 10, 0);
        testLineChart.setDragEnabled(true);
        testLineChart.setScaleEnabled(true);
        testLineChart.setTouchEnabled(true);
        testLineChart.setPinchZoom(false);

        Legend l = testLineChart.getLegend();
        l.setEnabled(false);

        testLineChart.getAxisLeft().setEnabled(false);
        testLineChart.getAxisLeft().setSpaceTop(40);
        testLineChart.getAxisLeft().setSpaceBottom(40);
        testLineChart.getAxisRight().setEnabled(false);
        testLineChart.getXAxis().setEnabled(false);

        testLineChart.invalidate();
    }

    private void addEntry() {

        LineData data = testLineChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        float yValue = (float) (Math.random() * 10) + 50f;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        testLineChart.notifyDataSetChanged();

        testLineChart.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        testLineChart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }
    private void removeLastEntry() {

        LineData data = testLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set != null) {

                Entry e = set.getEntryForXValue(set.getEntryCount() - 1, Float.NaN);

                data.removeEntry(e, 0);
                // or remove by index
                // mData.removeEntryByXValue(xIndex, dataSetIndex);
                data.notifyDataChanged();
                testLineChart.notifyDataSetChanged();
                testLineChart.invalidate();
            }
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(3f);
        set.setCircleRadius(5.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
}
