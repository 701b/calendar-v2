package com.example.calendar_v2;

import android.content.Context;

import com.example.calendar_v2.schedule.HolidayDBHelper;
import com.example.calendar_v2.schedule.Schedule;
import com.example.calendar_v2.schedule.ScheduleManager;
import com.example.calendar_v2.schedule.UserScheduleDBHelper;
import com.example.calendar_v2.time.Date;
import com.example.calendar_v2.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 모든 달력 관련 정보를 총괄하는 클래스이다.
 */
public class Calendar {

    private ScheduleManager scheduleManager;


    public Calendar(Context context) {
        scheduleManager = new ScheduleManager(context);
    }


    /**
     * 인자로 받은 기간 사이의 DayData를 List에 담아 반환한다.
     * 여기에 담긴 DayData들은 스케줄 정보가 겹치지 않게 추가된 상태이다.
     * 따라서 달력에 표시하는 경우, 그저 담긴 정보를 출력하기만 하면 된다.
     * 여기서 가져간 리스트를 사용하는 도중에, addUserSchedule을 이용해 스케줄을 추가하는 경우,
     * 반드시 기존의 리스트는 폐기하고 이 함수를 다시 호출하여 리스트를 받아야 한다.
     *
     * @param startDate 가져올 기간의 시작 날짜
     * @param endDate 가져올 기간의 끝 날짜
     * @return 해당 기간에 포함된 DayData가 담긴 List
     */
    public List<DayData> createDayDataListBetween(Date startDate, Date endDate) {
        List<DayData> dayDataList = new ArrayList<>();
        List<Schedule> schedules = scheduleManager.searchScheduleBetween(startDate, endDate);

        for (int i = 0; i < Date.getNumberOfDayBetween(startDate, endDate); i++) {
            dayDataList.add(new DayData(new Date(startDate.toLocalDate().plusDays(i))));
        }

        for (Schedule schedule : schedules) {
            DateTime scheduleStartDateTime = schedule.getStartDateTime();
            DateTime scheduleEndDateTime = schedule.getEndDateTime();
            Date startToShowDate;
            Date endToShowDate;
            int numberOfWeeks = 0;

            if (scheduleStartDateTime.toDate().compareTo(startDate) < 0) {
                try {
                    startToShowDate = startDate.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            } else {
                startToShowDate = scheduleStartDateTime.toDate();
            }

            if (scheduleEndDateTime.toDate().compareTo(endDate) > 0) {
                try {
                    endToShowDate = endDate.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            } else {
                endToShowDate = scheduleEndDateTime.toDate();
            }

            numberOfWeeks = Date.getNumberOfWeekBetween(startToShowDate, endToShowDate);

            for (int i = 0; i < numberOfWeeks; i++) {
                Date startToShowDateInWeek;
                Date endToShowDateInWeek;
                int indexOfSchedule = -1;

                if (i == 0) {
                    startToShowDateInWeek = startToShowDate;
                } else {
                    LocalDate startLocalDateTemp = startToShowDate.toLocalDate().plusWeeks(i);

                    startToShowDateInWeek = new Date(startLocalDateTemp.minusDays(startLocalDateTemp.getDayOfWeek().getValue()));
                }

                if (i == numberOfWeeks - 1) {
                    endToShowDateInWeek = endToShowDate;
                } else {
                    LocalDate endLocalDateTemp = startToShowDate.toLocalDate().plusWeeks(i);

                    endToShowDateInWeek = new Date(endLocalDateTemp.plusDays(6 - endLocalDateTemp.getDayOfWeek().getValue()));
                }

                for (int indexOfDayData = Date.getNumberOfDayBetween(startDate, startToShowDateInWeek) - 1;
                     indexOfDayData < Date.getNumberOfDayBetween(startDate, endToShowDateInWeek); indexOfDayData++) {
                    DayData dayData = dayDataList.get(indexOfDayData);

                    if (indexOfSchedule == -1) {
                        indexOfSchedule = dayData.getFirstEmptyIndex();
                    }

                    dayData.addSchedule(schedule, indexOfSchedule);
                }
            }
        }

        return dayDataList;
    }


    /* Getter Setter Line */
    public ScheduleManager getScheduleManager() {
        return scheduleManager;
    }

}
