package com.example.calendar_v2.calendar_v2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar_v2.Calendar;
import com.example.calendar_v2.DayData;
import com.example.calendar_v2.R;
import com.example.calendar_v2.schedule.ScheduleManager;
import com.example.calendar_v2.time.Date;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = new Calendar(this);
        ScheduleManager scheduleManager = calendar.getScheduleManager();

        Date testStartDate = new Date(2020, 1, 1);
        Date testEndDate = new Date(2020, 2, 1);



        List<DayData> list = calendar.createDayDataListBetween(testStartDate, testEndDate);

        for (int i = 0; i < Date.getNumberOfDayBetween(testStartDate, testEndDate); i++) {
            DayData dayData = list.get(i);

            if (dayData.getSchedule(0) != null) {
                if (dayData.getSchedule(1) != null) {
                    Log.d("message", dayData.getDate() + " " + dayData.getSchedule(0).getTitle() + ", " + dayData.getSchedule(1).getTitle());
                } else {
                    Log.d("message", dayData.getDate() + " " + dayData.getSchedule(0).getTitle());
                }
            } else {
                Log.d("message", dayData.getDate() + " 스케줄x");
            }
        }

    }
}
