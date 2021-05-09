package com.example.android_testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_testsqlite.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "School";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Students";
    private static int size = 0;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    public DatabaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = String.format("create table %s(%s integer primary key identity(1,1), %s text)",TABLE_NAME,KEY_ID,KEY_NAME);
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlDrop = String.format("drop table if exists %s",TABLE_NAME);
        db.execSQL(sqlDrop);

        onCreate(db);
    }

    public void addStudent(Student student){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,student.getName());

        database.insert(TABLE_NAME,null,values);
        database.close();
    }
    public void addStudentWithName(String name){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,size);
        values.put(KEY_NAME,name);

        database.insert(TABLE_NAME,null,values);
        database.close();
    }

    public List<Student> getListStudent(){
        List<Student> students = new ArrayList<>();
        String sqlString = "select * from " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sqlString,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Student student = new Student(cursor.getInt(0),cursor.getString(1));
            students.add(student);
            cursor.moveToNext();
        }
        size = students.size();
        return students;
    }

    public void removeStudent(int studentId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(studentId) });
        db.close();
    }
}
