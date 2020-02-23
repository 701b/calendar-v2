package com.example.calendar_v2.calendar_v2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.calendar_v2.R;
import com.example.calendar_v2.calendar_v2.Calendar;
import com.example.calendar_v2.calendar_v2.DayData;
import com.example.calendar_v2.calendar_v2.MyConfig;
import com.example.calendar_v2.calendar_v2.schedule.Schedule;
import com.example.calendar_v2.calendar_v2.time.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefreshScheduleHelper {
    private Context context;
    private TextView[][][] scheduleBlocks;
    private Calendar calendar;
    private List<DayData> list = new ArrayList<>();
    private Map<Integer, Schedule> map = new HashMap<>();

    public RefreshScheduleHelper(Context context, TextView[][][] scheduleBlocks) {
        this.context = context;
        this.calendar = new Calendar(context);
        this.scheduleBlocks = scheduleBlocks;
    }

    void printSchedule(Date startDate, Date endDate) {
        list = calendar.createDayDataListBetween(startDate, endDate);
        int numberOfDays = Date.getNumberOfDayBetween(startDate, endDate);

        initialize();
        for (int c = 0; c < numberOfDays; ++c) {
            map = list.get(c).getScheduleMap();
            if (map.isEmpty()) {
                continue;
            } else {
                for (Integer index : map.keySet()) {
                    if (index >= MyConfig.NUMBER_OF_SCHEDULE_BLOCKS){
                        continue;
                    }
                    else{
                        scheduleBlocks[c%7][c/7][index].setBackgroundColor(context.getResources().getColor(R.color.scheduleBlockColor));
                    }

                }
            }
        }

    }
    void initialize(){
        for(int c = 0 ; c<MyConfig.NUMBER_OF_COLUMNS ; ++c){
            for(int r = 0 ; r<MyConfig.NUMBER_OF_ROWS ; ++r){
                for(int s = 0 ; s< MyConfig.NUMBER_OF_SCHEDULE_BLOCKS ; ++s){
                    scheduleBlocks[c][r][s].setBackgroundColor(Color.WHITE);
                    scheduleBlocks[c][r][s].setText("");

                }
            }
        }
    }
}
