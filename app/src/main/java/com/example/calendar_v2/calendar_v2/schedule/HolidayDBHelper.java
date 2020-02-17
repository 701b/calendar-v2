package com.example.calendar_v2.calendar_v2.schedule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.example.calendar_v2.calendar_v2.time.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayDBHelper extends SQLiteOpenHelper {

    private List<Holiday> holidays;

    public HolidayDBHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "holiday.db", factory, version);

        holidays = new ArrayList<>();
        readAll();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE HOLIDAYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT);");
    }

    /**
     * holiday 정보를 list에 저장함과 동시에 DB에도 저장한다.
     *
     * @param holiday 저장할 Holiday
     */
    public void add(Holiday holiday) {
        holidays.add(holiday);
        insert(holiday);
    }

    /**
     * 인자로 받은 Holiday를 list에서 지움과 동시에 DB에서도 삭제한다.
     *
     * @param holiday 지울 Holiday
     */
    public void remove(Holiday holiday) {
        holidays.remove(holiday);
        delete(holiday);
    }

    private void insert(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();
        String title = holiday.getTitle();
        String dateStr = holiday.getDate().toLocalDate().toString();

        db.execSQL("INSERT INTO HOLIDAYBOOK VALUES(null, '" + title + "', '" + dateStr + "');");
        db.close();
    }

    private void delete(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();
        String title = holiday.getTitle();
        String dateStr = holiday.getStartDateTime().toDate().toLocalDate().toString();

        db.execSQL("DELETE FROM HOLIDAYBOOK WHERE title='" + title  + "', date='" + dateStr +"';");
        db.close();
    }

    private void readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOLIDAYBOOK", null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            Date date = new Date(LocalDate.parse(cursor.getString(2)));
            Holiday holiday = new Holiday(title, date);

            holidays.add(holiday);
        }
    }


    /* Getter Setter Line*/
    public List<Holiday> getHolidays() {
        return holidays;
    }

}
