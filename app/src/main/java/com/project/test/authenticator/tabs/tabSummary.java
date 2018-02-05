package com.project.test.authenticator.tabs;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.project.test.authenticator.R;
import com.project.test.authenticator.database.Data;
import com.project.test.authenticator.database.DataController;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabSummary extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DataController dataController = new DataController();
        View view =  inflater.inflate(R.layout.fragment_tab_summary, container, false);

        List<Data> data = dataController.getDataByLetterNumber(6);
        if (data.size()>0) {
            //Create graph diagram

            GraphView graph1 = (GraphView) view.findViewById(R.id.graph1);
            graph1.getViewport().setXAxisBoundsManual(true);
            graph1.getViewport().setMinX(-600);
            graph1.getViewport().setMaxX(600);
            graph1.getViewport().setScalable(true); // enables horizontal zooming and scrolling


            GraphView graph2 = (GraphView) view.findViewById(R.id.graph2);
            graph2.getViewport().setXAxisBoundsManual(true);
            graph2.getViewport().setMinX(-600);
            graph2.getViewport().setMaxX(600);
            graph2.getViewport().setScalable(true); // enables horizontal zooming and scrolling


            GraphView graph3 = (GraphView) view.findViewById(R.id.graph3);
            graph3.getViewport().setXAxisBoundsManual(true);
            graph3.getViewport().setMinX(-600);
            graph3.getViewport().setMaxX(600);
            graph3.getViewport().setScalable(true); // enables horizontal zooming and scrolling


            GraphView graph4 = (GraphView) view.findViewById(R.id.graph4);
            graph4.getViewport().setXAxisBoundsManual(true);
            graph4.getViewport().setMinX(-600);
            graph4.getViewport().setMaxX(600);
            graph4.getViewport().setScalable(true); // enables horizontal zooming and scrolling


            drawDataOnGraph(graph1, data);
            drawDataOnGraph2(graph2,data);
            drawDataOnGraph3(graph3,data);
            drawDataOnGraph4(graph4,data);

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            DataController dataController = new DataController();
            List<Data> data = dataController.getDataByLetterNumber(6);
            if (data.size()>0) {
                //Create graph diagram

                GraphView graph1 = (GraphView) getView().findViewById(R.id.graph1);
                graph1.removeAllSeries();
                graph1.getViewport().setXAxisBoundsManual(true);
                graph1.getViewport().setMinX(-600);
                graph1.getViewport().setMaxX(600);
                graph1.getViewport().setScalable(true); // enables horizontal zooming and scrolling


                GraphView graph2 = (GraphView) getView().findViewById(R.id.graph2);
                graph2.removeAllSeries();
                graph2.getViewport().setXAxisBoundsManual(true);
                graph2.getViewport().setMinX(-600);
                graph2.getViewport().setMaxX(600);
                graph2.getViewport().setScalable(true); // enables horizontal zooming and scrolling


                GraphView graph3 = (GraphView) getView().findViewById(R.id.graph3);
                graph3.removeAllSeries();
                graph3.getViewport().setXAxisBoundsManual(true);
                graph3.getViewport().setMinX(-600);
                graph3.getViewport().setMaxX(600);
                graph3.getViewport().setScalable(true); // enables horizontal zooming and scrolling


                GraphView graph4 = (GraphView) getView().findViewById(R.id.graph4);
                graph4.removeAllSeries();
                graph4.getViewport().setXAxisBoundsManual(true);
                graph4.getViewport().setMinX(-600);
                graph4.getViewport().setMaxX(600);
                graph4.getViewport().setScalable(true); // enables horizontal zooming and scrolling


                drawDataOnGraph(graph1, data);
                drawDataOnGraph2(graph2,data);
                drawDataOnGraph3(graph3,data);
                drawDataOnGraph4(graph4,data);

            }
    }
        else
            Log.d("MyFragment", "Fragment is not visible.");
    }

    private List<Long> tranformInputInLongList(String input){

        List<Long>tranformedString = new ArrayList<>();
        String arrayString[] = input.replace("[","").replace("]","").split(", ");
        for (String item :arrayString) {
            tranformedString.add(Long.parseLong(item));
        }
        return tranformedString;
    }

    private void drawDataOnGraph(GraphView graph, List<Data> data){
        List<DataPoint> dataPointList = new ArrayList<>();
        int sumDiffPr2Pr1 = 0;
        int medianDiffPr2Pr1 = 0;
        int standardDeviationDiffPr2Pr1=0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffPr2Pr1())) {
                dataPointList.add(new DataPoint(value,0));

                sumDiffPr2Pr1 = sumDiffPr2Pr1 +(int)value;
            }

        }
        medianDiffPr2Pr1 = sumDiffPr2Pr1 / dataPointList.size();
        //Calculate standard deviation
        int sumStandardDeviation = 0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffPr2Pr1())) {
                sumStandardDeviation = sumStandardDeviation + (int)Math.pow((double)value-(double)medianDiffPr2Pr1,2);
            }

        }
        standardDeviationDiffPr2Pr1 = (int)Math.sqrt((double)sumStandardDeviation/(double)data.size());
        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        dataPointList.toArray(dataPoints);

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(dataPoints);
        graph.addSeries(series4);
        series4.setColor(Color.CYAN);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-20, x, y+20, paint);
                canvas.drawLine(x, y-20, x, y+20, paint);
            }
        });
        PointsGraphSeries<DataPoint> series10 = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(standardDeviationDiffPr2Pr1 + medianDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1-standardDeviationDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1,0)
        });
        graph.addSeries(series10);
        series10.setColor(Color.CYAN);
        series10.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-200, x, y, paint);

            }
        });
    }
    private void drawDataOnGraph2(GraphView graph, List<Data> data){
        List<DataPoint> dataPointList = new ArrayList<>();
        int sumDiffPr2Pr1 = 0;
        int medianDiffPr2Pr1 = 0;
        int standardDeviationDiffPr2Pr1=0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffPr2Re1())) {
                dataPointList.add(new DataPoint(value,0));

                sumDiffPr2Pr1 = sumDiffPr2Pr1 +(int)value;
            }

        }
        medianDiffPr2Pr1 = sumDiffPr2Pr1 / dataPointList.size();
        //Calculate standard deviation
        int sumStandardDeviation = 0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffPr2Re1())) {
                sumStandardDeviation = sumStandardDeviation + (int)Math.pow((double)value-(double)medianDiffPr2Pr1,2);
            }

        }
        standardDeviationDiffPr2Pr1 = (int)Math.sqrt((double)sumStandardDeviation/(double)data.size());
        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        dataPointList.toArray(dataPoints);

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(dataPoints);
        graph.addSeries(series4);
        series4.setColor(Color.GREEN);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-20, x, y+20, paint);
                canvas.drawLine(x, y-20, x, y+20, paint);
            }
        });
        PointsGraphSeries<DataPoint> series10 = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(standardDeviationDiffPr2Pr1 + medianDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1-standardDeviationDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1,0)
        });
        graph.addSeries(series10);
        series10.setColor(Color.GREEN);
        series10.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-200, x, y, paint);

            }
        });
    }
    private void drawDataOnGraph3(GraphView graph, List<Data> data){
        List<DataPoint> dataPointList = new ArrayList<>();
        int sumDiffPr2Pr1 = 0;
        int medianDiffPr2Pr1 = 0;
        int standardDeviationDiffPr2Pr1=0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffRe2Re1())) {
                dataPointList.add(new DataPoint(value,0));

                sumDiffPr2Pr1 = sumDiffPr2Pr1 +(int)value;
            }

        }
        medianDiffPr2Pr1 = sumDiffPr2Pr1 / dataPointList.size();
        //Calculate standard deviation
        int sumStandardDeviation = 0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getDiffRe2Re1())) {
                sumStandardDeviation = sumStandardDeviation + (int)Math.pow((double)value-(double)medianDiffPr2Pr1,2);
            }

        }
        standardDeviationDiffPr2Pr1 = (int)Math.sqrt((double)sumStandardDeviation/(double)data.size());
        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        dataPointList.toArray(dataPoints);

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(dataPoints);
        graph.addSeries(series4);
        series4.setColor(Color.RED);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-20, x, y+20, paint);
                canvas.drawLine(x, y-20, x, y+20, paint);
            }
        });
        PointsGraphSeries<DataPoint> series10 = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(standardDeviationDiffPr2Pr1 + medianDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1-standardDeviationDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1,0)
        });
        graph.addSeries(series10);
        series10.setColor(Color.RED);
        series10.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-200, x, y, paint);

            }
        });
    }
    private void drawDataOnGraph4(GraphView graph, List<Data> data){

        List<DataPoint> dataPointList = new ArrayList<>();
        int sumDiffPr2Pr1 = 0;
        int medianDiffPr2Pr1 = 0;
        int standardDeviationDiffPr2Pr1=0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getPeriod())) {
                dataPointList.add(new DataPoint(value,0));

                sumDiffPr2Pr1 = sumDiffPr2Pr1 +(int)value;
            }

        }
        medianDiffPr2Pr1 = sumDiffPr2Pr1 / dataPointList.size();
        //Calculate standard deviation
        int sumStandardDeviation = 0;
        for (Data item :data) {
            for (long value:tranformInputInLongList(item.getPeriod())) {
                sumStandardDeviation = sumStandardDeviation + (int)Math.pow((double)value-(double)medianDiffPr2Pr1,2);
            }

        }
        standardDeviationDiffPr2Pr1 = (int)Math.sqrt((double)sumStandardDeviation/(double)data.size());
        DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
        dataPointList.toArray(dataPoints);

        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(dataPoints);
        graph.addSeries(series4);
        series4.setColor(Color.BLUE);
        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-20, x, y+20, paint);
                canvas.drawLine(x, y-20, x, y+20, paint);
            }
        });
        PointsGraphSeries<DataPoint> series10 = new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(standardDeviationDiffPr2Pr1 + medianDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1-standardDeviationDiffPr2Pr1,0),
                new DataPoint(medianDiffPr2Pr1,0)
        });
        graph.addSeries(series10);
        series10.setColor(Color.BLUE);
        series10.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, y-200, x, y, paint);

            }
        });
    }

}
