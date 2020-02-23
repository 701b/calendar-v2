package com.example.calendar_v2.calendar_v2.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.calendar_v2.R;
import com.example.calendar_v2.calendar_v2.schedule.ScheduleManager;
import com.example.calendar_v2.calendar_v2.schedule.UserSchedule;
import com.example.calendar_v2.calendar_v2.time.Date;

public class MainActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
        setUI();
    }

    private void init() {
        tabHost = getTabHost();
        /*
        ScheduleManager scheduleManager = new ScheduleManager(this);
        scheduleManager.addSchedule(new UserSchedule("1/20~2/3", new Date(2020, 1, 20),
                new Date(2020, 2, 3)));

        scheduleManager.addSchedule(new UserSchedule("1/25~2/2", new Date(2020, 1, 25),
                new Date(2020, 2, 2)));

        scheduleManager.addSchedule(new UserSchedule("2/2~2/4", new Date(2020, 2, 2),
                new Date(2020, 2, 4)));


        scheduleManager.addSchedule(new UserSchedule("19/12/1~20/1/21", new Date(2019, 12, 1),
                new Date(2020, 1, 21)));

         */
    }

    private void setUI() {
        TabHost.TabSpec spec = tabHost.newTabSpec("showCalendar");
        spec.setIndicator("캘린더")
                .setContent(new Intent(this, ShowCalendarActivity.class));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("configuration");
        spec.setIndicator("설정")
                .setContent(new Intent(this, ConfigurationActivity.class));
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

    }
}
