package com.example.nirbhaya_womensafetyapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "Student.db";
    public final static String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MOBILE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1  );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY Autoincrement,NAME TEXT,MOBILE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public int insertData(String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if(res!=null && res.getCount()>2){
            return 3;
        }
        else {

            ContentValues cv = new ContentValues();
            cv.put(COL_2  , name);
            cv.put(COL_3  , mobile);
            long result = db.insert(TABLE_NAME, null, cv);
            if (result == -1  ) return 0;
            else return 1;
        }
    }


    public boolean updateData(String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2  , name);
        contentValues.put(COL_3  , mobile);

        db.update(TABLE_NAME, contentValues, "NAME=?", new String[]{name});
        return true;
    }

    public Integer deleteData (String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME = ?", new String[]{name});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null,null);
    }
}