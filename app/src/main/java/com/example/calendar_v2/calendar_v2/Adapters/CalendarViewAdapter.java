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
import java.util.LinkedList;
import java.util.Queue;

public class CalendarViewAdapter<VH extends CalendarViewAdapter.ViewHolder> extends PagerAdapter {

    /**
     * Recycling 관련
     */
    Queue<VH> destroyedItems = new LinkedList<>();

    public static class ViewHolder {
        /**
         * ViewHolder의 View저장
         */
        LinearLayout view;
        TextView[][] dateText = new TextView[MyConfig.NUMBER_OF_COLUMNS][MyConfig.NUMBER_OF_ROWS];

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
        private RefreshScheduleHelper helper;
        private TextView[][][] scheduleBlocks = new TextView[MyConfig.NUMBER_OF_COLUMNS][MyConfig.NUMBER_OF_ROWS][MyConfig.NUMBER_OF_SCHEDULE_BLOCKS];


        public ViewHolder(Context context, LinearLayout view) {
            this.view = view;
            helper = new RefreshScheduleHelper(context, scheduleBlocks);

            for (int r = 0; r < MyConfig.NUMBER_OF_ROWS; ++r) {
                for (int c = 0; c < MyConfig.NUMBER_OF_COLUMNS; ++c) {
                    dateText[c][r] = view.findViewWithTag("row" + r + "col" + c).findViewById(R.id.day);

                    scheduleBlocks[c][r][0] = view.findViewWithTag("row" + r + "col" + c).findViewById(R.id.s0);
                    scheduleBlocks[c][r][1] = view.findViewWithTag("row" + r + "col" + c).findViewById(R.id.s1);
                    scheduleBlocks[c][r][2] = view.findViewWithTag("row" + r + "col" + c).findViewById(R.id.s2);
                    scheduleBlocks[c][r][3] = view.findViewWithTag("row" + r + "col" + c).findViewById(R.id.s3);

                }
            }
        }

        private void setDates(int position) {
            startOfMonth = LocalDate.now().plusMonths(position - MyConfig.DEFAULT_PAGE_VALUE);
            startOfMonth = startOfMonth.minusDays(startOfMonth.getDayOfMonth() - 1);
            endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
            daysInFrontMonth = startOfMonth.getDayOfWeek().getValue();
            daysInRearMonth = MyConfig.NUMBER_OF_COLUMNS * MyConfig.NUMBER_OF_ROWS
                    - daysInFrontMonth
                    - endOfMonth.getDayOfMonth();
            dateIterator = startOfMonth.minusDays(daysInFrontMonth);
            startDate = new Date(dateIterator);
            endDate = new Date(endOfMonth.plusDays(daysInRearMonth));
            helper.printSchedule(startDate, endDate);
            for(int r = 0 ; r<MyConfig.NUMBER_OF_ROWS ; ++r){
                for(int c= 0 ; c<MyConfig.NUMBER_OF_COLUMNS ; ++c){
                    dateText[c][r].setText(dateIterator.getDayOfMonth()+"");
                    dateIterator = dateIterator.plusDays(1);
                }
            }
        }

    }

    /**
     * field
     */
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout.LayoutParams layoutParams;


    /**
     * Refresh Schedule 관련
     */


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
        return ((VH) object).view == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((VH) object).view);;
        destroyedItems.add((VH) object);
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
        ViewHolder viewHolder = destroyedItems.poll();
        if (viewHolder != null) {
            container.addView(viewHolder.view);
            onBindViewHolder(viewHolder, position);
        } else {
            viewHolder = onCreateViewHolder(container);
            onBindViewHolder(viewHolder, position);


            container.addView(viewHolder.view);
        }
        return viewHolder;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LinearLayout monthView = new LinearLayout(context);
        monthView.setOrientation(LinearLayout.VERTICAL);
        for (int r = 0; r < MyConfig.NUMBER_OF_ROWS; ++r) {
            LinearLayout weekView = new LinearLayout(context);
            weekView.setOrientation(LinearLayout.HORIZONTAL);
            weekView.setLayoutParams(layoutParams);
            weekView.setTag("row" + r);
            for (int c = 0; c < MyConfig.NUMBER_OF_COLUMNS; ++c) {
                LinearLayout dayView = new LinearLayout(context);
                dayView.setOrientation(LinearLayout.VERTICAL);
                dayView.setLayoutParams(layoutParams);
                dayView.setTag("row" + r + "col" + c);
                inflater.inflate(R.layout.dayview_layout, dayView, true);
                weekView.addView(dayView);
            }
            monthView.addView(weekView);
        }
        return new ViewHolder(context, monthView);
    }

    /**
     * Bind data at position into viewHolder
     *
     * @param viewHolder
     * @param position
     */
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setDates(position);
    }

}
