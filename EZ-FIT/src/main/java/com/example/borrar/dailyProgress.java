package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.borrar.Adapter.SeriesListAdapterProgress;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.Classes.SessionClass;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.dbHelper_Session;
import com.example.borrar.db.dbHelper_serie;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class dailyProgress extends AppCompatActivity {

    private LineChart chart;
    private List<Float> list1 = new ArrayList<>();
    private List<Float> list2 = new ArrayList<>();
    private List<String> listDates = new ArrayList<>();

    ArrayList<SessionClass> mySessions;
    SeriesClass myserie;
    Integer accumulator; //accumulator for storing the workout of each session

    RecyclerView listExercises;
    ArrayList<ExerciseClass> listArrayExercises;

    String Selected_date;

    private List<String> names = new ArrayList<>(); //list to store the names of the legend
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_progress);
        // Obtener los datos de las listas
        getData();

        // Configurar el gráfico
        chart = findViewById(R.id.line_chart);
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
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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

        xAxis.setValueFormatter(new MyXAxisValueFormatter((ArrayList<String>) listDates));
        xAxis.setLabelRotationAngle(50);

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
        Selected_date = String.valueOf(listDates.get(listDates.size()-1));//Take the last day by default
        //In order to get the date that the user schoosing
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Obtener la posición seleccionada en el eje X
                int index = (int) (e.getX());
                Selected_date = String.valueOf(listDates.get(index));

            }

            @Override
            public void onNothingSelected() {
                Selected_date = String.valueOf(listDates.get(listDates.size()-1));//Take the last day by default
            }
        });


        //RecyclerView
        listExercises=findViewById(R.id.listSeries);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();

        //Pass the query below to the adapter in order to place the items
        SeriesListAdapterProgress adapter= new SeriesListAdapterProgress(showSeries(Selected_date));
        listExercises.setAdapter(adapter);

        //Refresh
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SeriesListAdapterProgress adapter= new SeriesListAdapterProgress(showSeries(Selected_date));
                listExercises.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    // Clase para formatear los valores del eje X
    public class MyXAxisValueFormatter extends ValueFormatter {

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
        Calendar calendario = Calendar.getInstance();
        int maxday = calendario.get(Calendar.DAY_OF_MONTH);
        int month = calendario.get(Calendar.MONTH)+1;
        for(day=0; day<maxday+1; day++){
            date= String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(2023);
            mySessions=getSessionWork(date,userID);
            accumulator=0;
            for(SessionClass session : mySessions) {
                try {
                    myserie=GetSerie(session.getSerie());
                    accumulator=accumulator+myserie.getRepetitions();

                }catch (Exception e){ Toast.makeText(getApplicationContext(),"Session not found", Toast.LENGTH_LONG).show();}
            }
            list1.add(Float.valueOf(accumulator));
            list2.add(30f);
            listDates.add(date);
        }
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
        dbHelper_Session dbHelper=new dbHelper_Session(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<SessionClass> listSession=new ArrayList<>();
        SessionClass session=null;
        Cursor cursor=null;
        date=date.replace("/", "");
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
        dbHelper_serie dbHelper=new dbHelper_serie(this);
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
        SharedPreferences sharedPreferences =getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }

    //Query to the database
    public ArrayList<SeriesClass> showSeries(String date){
        dbHelper_serie dbHelper=new dbHelper_serie(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper_Session dbHelper2=new dbHelper_Session(this);
        SQLiteDatabase db2 = dbHelper2.getWritableDatabase();

        ArrayList<SeriesClass> listSeries=new ArrayList<>(); //To store all series
        SeriesClass series=null;
        String userID=getUserId();
        Cursor cursor=null;

        ArrayList<String> seriesIDs2=new ArrayList<>(); //To store the ids of the series given the exercise and the session
        String seiresIDsStr2;

        date=date.replace("/", "");
        //Select all exercises from this date
        cursor=db2.rawQuery("SELECT * FROM "+ BBDD_Session.TABLE_NAME+" WHERE date == "+date+" AND userID == "+userID, null);
        if(cursor.moveToFirst()){
            do{
                seriesIDs2.add(String.valueOf(cursor.getInt(1)));
            } while(cursor.moveToNext());
        }
        cursor.close();

        if (seriesIDs2.isEmpty()) {
            seiresIDsStr2 = "()"; // Si la lista está vacía, se utiliza una cadena vacía como valor por defecto
        } else {
            seiresIDsStr2 = seriesIDs2.toString().replace("[", "(").replace("]", ")");// convertir la lista a una cadena de la forma "(1, 2, 3, ...)"
        }

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Serie.TABLE_NAME+" WHERE id IN "+seiresIDsStr2, null);
        if(cursor.moveToFirst()){
            do{
                series=new SeriesClass();
                series.setId(cursor.getInt(0));
                series.setExercise(cursor.getInt(1));
                series.setRepetitions(cursor.getInt(2));
                series.setWeight(cursor.getString(3));
                series.setRest(cursor.getString(4));
                series.setNotes(cursor.getString(5));
                if(cursor.getInt(6)==1){
                    listSeries.add(series);
                }
            } while(cursor.moveToNext());
        }
        cursor.close();


        return listSeries;
    }
}