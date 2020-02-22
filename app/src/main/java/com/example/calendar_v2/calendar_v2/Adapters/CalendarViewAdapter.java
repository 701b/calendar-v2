package com.example.calendar_v2.calendar_v2.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.example.calendar_v2.R;
import com.example.calendar_v2.calendar_v2.MyConfig;
import com.example.calendar_v2.calendar_v2.time.Date;

import java.time.LocalDate;

public class CalendarViewAdapter extends PagerAdapter {

    /**
     * field
     */
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout.LayoutParams layoutParams;

    /**
     * View를 구성하는 변수
     */
    private int index;
    private LocalDate startOfMonth;
    private LocalDate endOfMonth;
    private int daysInFrontMonth;
    private int daysInRearMonth;
    private Date startDate;
    private Date endDate;
    private LocalDate dateIterator;


    /**
     * Refresh Schedule 관련
     */
    private RefreshScheduleHelper helper;
    private TextView scheduleBlock;
    private TextView[][] scheduleBlocks = new TextView[MyConfig.NUMBER_OF_COLUMNS][MyConfig.NUMBER_OF_ROWS];

    /**
     * 생성자
     */
    public CalendarViewAdapter(Context context) {
        this.context = context;
        init();
    }

    /**
     * 변수 초기화
     */
    private void init() {
        helper = new RefreshScheduleHelper(context, scheduleBlocks);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
    }


    /**
     * 과거, 미래로 DEFAULT_PAGE_VALUE만큼 조회가능하게 설정
     */
    @Override
    public int getCount() {
        return MyConfig.DEFAULT_PAGE_VALUE * 2;
    }


    /**
     * PagerAdapter Overriding 함수들
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     * View를 구성하는 함수
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        index = position - MyConfig.DEFAULT_PAGE_VALUE;
        startOfMonth = LocalDate.now().plusMonths(index);
        startOfMonth = startOfMonth.minusDays(startOfMonth.getDayOfMonth() - 1);
        endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        daysInFrontMonth = startOfMonth.getDayOfWeek().getValue();
        daysInRearMonth = MyConfig.NUMBER_OF_COLUMNS * MyConfig.NUMBER_OF_ROWS
                - daysInFrontMonth
                - endOfMonth.getDayOfMonth();
        dateIterator = startOfMonth.minusDays(daysInFrontMonth);
        startDate = new Date(dateIterator);
        endDate = new Date(endOfMonth.plusDays(daysInRearMonth));

        LinearLayout monthView = new LinearLayout(context);
        monthView.setOrientation(LinearLayout.VERTICAL);
        for (int r = 0; r < MyConfig.NUMBER_OF_ROWS; ++r) {
            LinearLayout weekView = new LinearLayout(context);
            weekView.setOrientation(LinearLayout.HORIZONTAL);
            weekView.setLayoutParams(layoutParams);
            for (int c = 0; c < MyConfig.NUMBER_OF_COLUMNS; ++c, dateIterator = dateIterator.plusDays(1)) {
                LinearLayout dayView = new LinearLayout(context);
                dayView.setOrientation(LinearLayout.VERTICAL);
                dayView.setLayoutParams(layoutParams);
                inflater.inflate(R.layout.dayview_layout, dayView, true);
                scheduleBlock = (TextView) dayView.findViewById(R.id.day);
                scheduleBlock.setText(String.valueOf(dateIterator.getDayOfMonth()));
                scheduleBlocks[c][r] = scheduleBlock;
                weekView.addView(dayView);
            }
            monthView.addView(weekView);
        }
        helper.printSchedule(startDate, endDate);

        container.addView(monthView);
        return monthView;
    }


}
