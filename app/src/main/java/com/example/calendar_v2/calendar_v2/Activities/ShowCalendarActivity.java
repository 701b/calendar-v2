package com.example.calendar_v2.calendar_v2.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.calendar_v2.R;
import com.example.calendar_v2.calendar_v2.Adapters.CalendarViewAdapter;
import com.example.calendar_v2.calendar_v2.MyConfig;
import com.example.calendar_v2.calendar_v2.Views.MyCalendarView;
import com.example.calendar_v2.calendar_v2.time.Date;

import java.time.LocalDate;

public class ShowCalendarActivity extends AppCompatActivity {
    private MyCalendarView myCalendarView;
    private CalendarViewAdapter adapter;
    private TextView tv;
    private ViewPager.OnPageChangeListener listener;
    private Date selectedMonth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_calendar_layout);
        init();
        setUI();
        myCalendarView.setOnPageChangeListener(listener);
    }

    void init(){
        myCalendarView = (MyCalendarView)findViewById(R.id.calendar_view);
        adapter = new CalendarViewAdapter(this);
        tv = findViewById(R.id.date);
        selectedMonth = new Date(LocalDate.now());
        listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedMonth = new Date(LocalDate.now().plusMonths(position - MyConfig.DEFAULT_PAGE_VALUE));
                tv.setText(selectedMonth.toString(true, true, false, false));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    void setUI(){
        tv.setText(selectedMonth.toString(true, true, false, false));
        myCalendarView.setAdapter(adapter);
        myCalendarView.setCurrentItem(MyConfig.DEFAULT_PAGE_VALUE);

    }
}
