package com.example.borrar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.Classes.SessionClass;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.dbHelper_Session;
import com.example.borrar.db.dbHelper_serie;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressFragment extends Fragment {

    private LineChart chart;
    private List<Float> list1 = new ArrayList<>();
    private List<Float> list2 = new ArrayList<>();

    ArrayList<SessionClass> mySessions;
    SeriesClass myserie;
    HashMap<String, Integer> TotalWorkout = new HashMap<>();
    Integer accumulator; //accumulator for storing the workout of each session

    private List<String> names = new ArrayList<>(); //list to store the names of the legend

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
        ArrayList<String> fechas = new ArrayList<>(TotalWorkout.keySet());
        xAxis.setValueFormatter(new MyXAxisValueFormatter(fechas));

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

        //Personalizar el nombre de la leyenda
        ArrayList<ILineDataSet> dataSets = (ArrayList<ILineDataSet>) chart.getData().getDataSets();
        List<LegendEntry> legendEntries = new ArrayList<>();
        names.add("My workout");
        names.add("Recomendation");
        for (int i = 0; i < dataSets.size(); i++) {
            String label = names.get(i); // Aquí puedes poner el título que desees
            int color = dataSets.get(i).getColor();
            LegendEntry entry = new LegendEntry(label, Legend.LegendForm.LINE, 10f, 2f, null, color);
            legendEntries.add(entry);
        }
        legend.setCustom(legendEntries);


        return view;
    }

    // Clase para formatear los valores del eje X
    private class MyXAxisValueFormatter extends ValueFormatter {

        private final ArrayList<String> mDates;

        public MyXAxisValueFormatter(ArrayList<String> dates) {
            mDates = dates;
        }

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index >= 0 && index < mDates.size()) {
                return mDates.get(index);
            }
            return "";
        }
    }

    // Método para obtener los datos de las listas
    private void getData() {
        // Recuperar el nombre de usuario de SharedPreferences
        String userID=getUserId();

        String date;
        int day;
        int month=4;

        for(day=1; day<9; day++){
            date= String.valueOf(day)+String.valueOf(month)+String.valueOf(2023);
            mySessions=getSessionWork(date,userID);
            accumulator=0;
            for(SessionClass session : mySessions) {
                try {
                    myserie=GetSerie(session.getSerie());
                    accumulator=accumulator+myserie.getRepetitions();

                }catch (Exception e){ Toast.makeText(getActivity().getApplicationContext(),"Session not found", Toast.LENGTH_LONG).show();}
            }
            list1.add(Float.valueOf(accumulator));
            TotalWorkout.put(date, accumulator);
        }


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
        LineDataSet set1 = new LineDataSet(values1, "Your progress");
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
        LineDataSet set2 = new LineDataSet(values2, "Recommended progress");
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



        chart.moveViewToX(list1.size() - 1);

        // Establecer los datos en el gráfico
        chart.setData(data);
        chart.setVisibleXRangeMaximum(7);
        chart.setVisibleXRangeMinimum(7);
    }
    //Query to the database to get the workouts per day
    public ArrayList<SessionClass> getSessionWork(String date,String userID){
        dbHelper_Session dbHelper=new dbHelper_Session(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<SessionClass> listSession=new ArrayList<>();
        SessionClass session=null;
        Cursor cursor=null;
        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Session.TABLE_NAME+" WHERE date == "+date +" AND userID == " + userID, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    session = new SessionClass();
                    session.setId(cursor.getInt(0));
                    session.setSerie(cursor.getInt(1));
                    session.setDate(cursor.getString(2));
                    listSession.add(session);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return listSession;
    }
    //Query to the database to get the serie given the session
    public SeriesClass GetSerie(int serieId){
        dbHelper_serie dbHelper=new dbHelper_serie(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SeriesClass serie=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Serie.TABLE_NAME+" WHERE id == "+serieId, null);
        if(cursor.moveToFirst()){
            do{
                serie=new SeriesClass();
                serie.setId(cursor.getInt(0));
                serie.setExercise(cursor.getInt(1));
                serie.setRepetitions(cursor.getInt(2));
                serie.setWeight(cursor.getString(3));
                serie.setRest(cursor.getString(4));
                serie.setNotes(cursor.getString(5));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return serie;
    }
    public String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }
}

