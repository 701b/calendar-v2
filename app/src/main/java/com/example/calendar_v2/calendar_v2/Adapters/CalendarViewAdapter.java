package com.example.calendar_v2.calendar_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.calendar_v2.MyConfig;
import com.example.calendar_v2.R;

public class CalendarViewAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private LinearLayout.LayoutParams layoutParams;

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
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return getMonthView(position);
    }


    private LinearLayout getMonthView() {
        LinearLayout monthView = new LinearLayout(context);
        monthView.setOrientation(LinearLayout.VERTICAL);
        for (int r = 0; r < MyConfig.NUMBER_OF_ROWS; ++r) {
            LinearLayout weekView = new LinearLayout(context);
            weekView.setOrientation(LinearLayout.HORIZONTAL);
            weekView.setLayoutParams(layoutParams);
            for (int c = 0; c < MyConfig.NUMBER_OF_COLUMNS; ++c) {
                LinearLayout dayView = new LinearLayout(context);
                dayView.setOrientation(LinearLayout.VERTICAL);
                dayView.setLayoutParams(layoutParams);
                inflater.inflate(R.layout.dayview_layout, dayView, true);
                weekView.addView(dayView);
            }
            monthView.addView(weekView);
        }
        return monthView;
    }

}
