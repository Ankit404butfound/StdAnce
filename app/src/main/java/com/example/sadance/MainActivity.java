package com.example.sadance;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected Context context;
    TextView total_days;
    TextView present_days;
    TextView percent;
    TextView left_over_days;

    DecimalFormat newFormat = new DecimalFormat("#.##");
    DBHandler mydb = new DBHandler(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        total_days = (TextView) findViewById(R.id.total_days);
        present_days = (TextView) findViewById(R.id.present_days);
        percent = (TextView) findViewById(R.id.percent);
        left_over_days = (TextView) findViewById(R.id.leftOverDays);

        Button present = (Button) findViewById(R.id.present);
        Button absent = (Button) findViewById(R.id.absent);

        present.setOnClickListener(this);
        absent.setOnClickListener(this);

        try {
            float[] data = mydb.current_data();
            double tot_days = (double) data[0];
            double pre_days = (double) data[1];
            double percentage = pre_days / tot_days * 100;


            Log.d("hmmmmmmm", String.valueOf(data));

            total_days.setText("Total days: " + String.valueOf(Math.round(tot_days)));
            present_days.setText("Present days: " + String.valueOf(Math.round(pre_days)));
            percent.setText("Percentage: " + Double.valueOf(newFormat.format(percentage)));

            double para_tot_days = (double) tot_days;
            double para_pre_days = (double) pre_days;

            Log.d("Starting app", String.valueOf(para_pre_days));
            Log.d("Starting app", String.valueOf(para_tot_days));

            calculate_safety(percentage, para_tot_days, para_pre_days);

        }
        catch (ArrayIndexOutOfBoundsException e){
            total_days.setText("Total days: N/A");
            present_days.setText("Present days: N/A");
            percent.setText("Percentage: N/A");


            float[] data = mydb.current_data();
            Log.d("hmmmmmmm", String.valueOf(data));
//            db.insert();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.absent){
            float[] data;
            data = mydb.absent();
            Log.d("arrrrrrray", String.valueOf(data));
            double percentage = 0;
            percentage = Double.valueOf(newFormat.format(data[2]));

            total_days.setText("Total days: " + String.valueOf(Math.round(data[0])));
            present_days.setText("Present days: " + String.valueOf(Math.round(data[1])));
            percent.setText("Percentage: " + percentage);

            double para_tot_days = data[0];
            int para_pre_days = Math.round(data[1]);

            calculate_safety(percentage, para_tot_days, para_pre_days);
        }

        else{
            float[] data;
            data = mydb.present();
            double percentage = 0;
            percentage = Double.valueOf(newFormat.format(data[2]));
            total_days.setText("Total days: " + String.valueOf(Math.round(data[0])));
            present_days.setText("Present days: " + String.valueOf(Math.round(data[1])));
            percent.setText("Percentage: " + percentage);

            double para_tot_days = data[0];
            double para_pre_days = (data[1]);

            calculate_safety(percentage, para_tot_days, para_pre_days);
        }
    }

    public void calculate_safety(double percentage, double tot_days, double pre_days){
        Log.d("Below 75", String.valueOf(percentage));
        Log.d("Below 75", String.valueOf(pre_days));
        Log.d("Below 75", String.valueOf(tot_days));
        int ctr = 0;
        if (percentage < 75) {
            while (percentage < 75) {
                tot_days += 1;
                pre_days += 1;
                ctr += 1;
                percentage = pre_days/tot_days*100;
                Log.d("Below 75", String.valueOf(percentage));
                Log.d("Below 75", String.valueOf(pre_days));
                Log.d("Below 75", String.valueOf(tot_days));
            }
            left_over_days.setText("You need to attend " + ctr + " periods for 75%+ attendance");
            left_over_days.setTextColor(Color.RED);
        }
        else {
            ctr = -1;
            while (percentage > 75) {
                tot_days -= 1;
                pre_days -= 1;
                ctr += 1;
                percentage = pre_days / tot_days * 100;
                Log.d("Above 75", String.valueOf(percentage));
                Log.d("Above 75", String.valueOf(pre_days));
                left_over_days.setText("You can safely bunk " + ctr + " classes");
                left_over_days.setTextColor(Color.GREEN);
            }
        }
    }
}