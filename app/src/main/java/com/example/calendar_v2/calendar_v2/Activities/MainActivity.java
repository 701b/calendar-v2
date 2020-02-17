package com.example.calendar_v2.calendar_v2.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.calendar_v2.R;

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
