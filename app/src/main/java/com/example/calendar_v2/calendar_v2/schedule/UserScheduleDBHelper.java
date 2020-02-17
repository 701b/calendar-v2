package com.example.calendar_v2.calendar_v2.schedule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.example.calendar_v2.calendar_v2.time.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserScheduleDBHelper extends SQLiteOpenHelper {

    private List<UserSchedule> userSchedules;

    public UserScheduleDBHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "userSchedule2.db", factory, version);

        userSchedules = new ArrayList<>();
        readAll();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERSCHEDULEBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, title TEXT, startDateTime TEXT, endDateTime TEXT, isAllDay BOOLEAN, memo TEXT);");
    }

    /**
     * userSchedule 정보를 list에 저장함과 동시에 DB에도 저장한다.
     *
     * @param userSchedule 저장할 userSchedule
     */
    public void add(UserSchedule userSchedule) {
        userSchedules.add(userSchedule);
        insert(userSchedule);
    }

    /**
     * 인자로 받은 schedule을 list에서 지움과 동시에 DB에서도 삭제한다.
     *
     * @param userSchedule 지울 userSchedule
     */
    public void remove(UserSchedule userSchedule) {
        userSchedules.remove(userSchedule);
        delete(userSchedule);
    }

    private void insert(UserSchedule userSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        int id = userSchedule.getId();
        String title = userSchedule.getTitle();
        String startStr = userSchedule.getStartDateTime().toLocalDateTime().toString();
        String endStr = userSchedule.getEndDateTime().toLocalDateTime().toString();
        String memo = userSchedule.getMemo();
        boolean isAllDay = userSchedule.isAllDay();

        db.execSQL("INSERT INTO USERSCHEDULEBOOK VALUES(null, '" + id + "', '" + title + "', '" + startStr + "', '" + endStr + "', '" + isAllDay + "', '" + memo + "');");
        db.close();
    }

    private void delete(UserSchedule userSchedule) {
        SQLiteDatabase db = getWritableDatabase();
        int id = userSchedule.getId();

        db.execSQL("DELETE FROM USERSCHEDULEBOOK WHERE id='" + id + "';");
        db.close();
    }


    private void readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERSCHEDULEBOOK", null);
        int maxID = 0;

        while (cursor.moveToNext()) {
            int id = Integer.valueOf(cursor.getString(1));
            String title = cursor.getString(2);
            DateTime startDateTime = new DateTime(LocalDateTime.parse(cursor.getString(3)));
            DateTime endDateTime = new DateTime(LocalDateTime.parse(cursor.getString(4)));
            boolean isHoliday = Boolean.valueOf(cursor.getString(5));
            UserSchedule userSchedule = new UserSchedule(title, startDateTime, endDateTime, id);

            userSchedule.setMemo(cursor.getString(6));
            userSchedules.add(userSchedule);

            if (id > maxID) {
                maxID = id;
            }
        }

        UserSchedule.nextID = maxID + 1;
    }


    /* Getter Setter Line*/
    public List<UserSchedule> getUserSchedules() {
        return userSchedules;
    }
}
