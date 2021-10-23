package com.example.sadance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "student_data";

    // below variable is for our id column.
    private static final String PRESENT_DAYS = "present_days";

    // below variable is for our course name column
    private static final String DAYS_COL = "total_days";



    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE student_data (total_days integer, present_days integer)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
        db.execSQL("INSERT INTO student_data (total_days, present_days) values(171, 111)");

    }

    // this method is use to add new course to our sqlite database.
    public float[] present() {

        SQLiteDatabase db = this.getWritableDatabase();


        Log.d("dataaaaaaaaaaaaaaa", "Inside current data");

        List<Integer> data = new ArrayList<>();
        float[] student_data = {0, 0, 0};

        Cursor cursor = db.rawQuery("SELECT * FROM student_data", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("cursorrr", String.valueOf(cursor.getInt(0)));
                Log.d("cursorrr", String.valueOf(cursor.getInt(1)));
                student_data[0] = (float)cursor.getInt(0) + 1;
                student_data[1] = (float)cursor.getInt(1) + 1;
            } while (cursor.moveToNext());
        }
        Log.d("dataaaaaaaaaaaaaaa", String.valueOf(data));

        float percent = student_data[1]/student_data[0]*100;
        student_data[2] = percent;
        db.execSQL("UPDATE student_data SET total_days="+(int)student_data[0]+", present_days="+(int)student_data[1]);
        return student_data;
    }

    public float[] absent() {

        SQLiteDatabase db = this.getWritableDatabase();


        Log.d("dataaaaaaaaaaaaaaa", "Inside current data");

        List<Integer> data = new ArrayList<>();
        float[] student_data = {0, 0, 0};

        Cursor cursor = db.rawQuery("SELECT * FROM student_data", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("cursorrr", String.valueOf(cursor.getInt(0)));
                Log.d("cursorrr", String.valueOf(cursor.getInt(1)));
                student_data[0] = (float)cursor.getInt(0) + 1;
                student_data[1] = (float)cursor.getInt(1);
            } while (cursor.moveToNext());
        }
        Log.d("dataaaaaaaaaaaaaaa", String.valueOf(data));

        float percent = student_data[1]/student_data[0]*100;
        student_data[2] = percent;
        db.execSQL("UPDATE student_data SET total_days="+(int)student_data[0]+", present_days="+(int)student_data[1]);
        return student_data;
    }

    public float[] current_data() {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();


        Log.d("dataaaaaaaaaaaaaaa", "Inside current data");

        List<Integer> data = new ArrayList<>();
        float[] student_data = {0, 0, 0};

        Cursor cursor = db.rawQuery("SELECT * FROM student_data", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("cursorrr", String.valueOf(cursor.getInt(0)));
                Log.d("cursorrr", String.valueOf(cursor.getInt(1)));
                student_data[0] = (float)cursor.getInt(0);
                student_data[1] = (float)cursor.getInt(1);
            } while (cursor.moveToNext());
        }
        Log.d("dataaaaaaaaaaaaaaa", String.valueOf(data));

        float percent = Math.round(student_data[1]/student_data[0]*100);
        student_data[2] = percent;
        return student_data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.d("a", "hereeeeeeeeeeeeee");
    }

    public List<Integer> execute(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student_data", null);
        List<Integer> data = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Log.d("cursorrr", String.valueOf(cursor.getInt(0)));
                data.add(cursor.getInt(0));
                data.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }
        return data;
    }

    public void insert(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE student_data (total_days integer, present_days integer)");
        db.execSQL("INSERT INTO student_data (total_days, present_days) values(163, 105)");
    }
}
