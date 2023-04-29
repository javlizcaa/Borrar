package com.example.borrar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private LineChart chart;
    private List<Float> list1 = new ArrayList<>();
    private List<Float> list2 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        // Obtener los datos de las listas
        getData();

        // Configurar el gráfico
        chart = view.findViewById(R.id.line_chart);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);
        chart.setScaleMinima(7f, 1f);

        chart.setExtraOffsets(40f, 0f, 40f, 0f); // Agregar margen a los laterales

        // Obtener la altura de la pantalla y configurar la altura del gráfico
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height / 2);
        layoutParams.setMargins(0, 0, 0, 0); // Configurar los margenes
        chart.setLayoutParams(layoutParams);

        // Configurar el eje X
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());

        // Configurar el eje Y izquierdo
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);

        // Configurar el eje Y derecho
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // Agregar los datos al gráfico
        setData();

        // Configurar la leyenda
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // Colocar la leyenda en la parte superior del gráfico
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        return view;
    }

    // Clase para formatear los valores del eje X
    private class MyXAxisValueFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return String.valueOf(value);
        }
    }

    // Método para obtener los datos de las listas
    private void getData() {
        list1.add(10f);
        list1.add(20f);
        list1.add(30f);
        list1.add(40f);
        list1.add(50f);
        list1.add(60f);
        list1.add(70f);
        list1.add(80f);
        list1.add(90f);


        list2.add(20f);
        list2.add(25f);
        list2.add(35f);
        list2.add(45f);
        list2.add(55f);
        list2.add(65f);
        list2.add(75f);
        list2.add(85f);
        list2.add(95f);
    }

    // Método para agregar los datos al gráfico
    private void setData() {

        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {
            values1.add(new Entry(i, list1.get(i)));
            values2.add(new Entry(i, list2.get(i)));
        }

        // Configurar los datos y las propiedades de la línea 1
        LineDataSet set1 = new LineDataSet(values1, "DataSet 1");
        set1.setColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleColor(Color.RED);
        set1.setCircleRadius(5f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(10f);
        set1.setDrawFilled(true);
        set1.setFillAlpha(50);
        set1.setFillColor(Color.RED);

        // Configurar los datos y las propiedades de la línea 2
        LineDataSet set2 = new LineDataSet(values2, "DataSet 2");
        set2.setColor(Color.BLUE);
        set2.setLineWidth(2f);
        set2.setCircleColor(Color.BLUE);
        set2.setCircleRadius(5f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(10f);
        set2.setDrawFilled(true);
        set2.setFillAlpha(50);
        set2.setFillColor(Color.BLUE);

        // Agregar las líneas al gráfico
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        // Crear y configurar los datos del gráfico
        LineData data = new LineData(dataSets);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        chart.setVisibleXRangeMaximum(7);
        chart.setVisibleXRangeMinimum(7);
        chart.moveViewToX(list1.size() - 1);

        // Establecer los datos en el gráfico
        chart.setData(data);
    }
}

