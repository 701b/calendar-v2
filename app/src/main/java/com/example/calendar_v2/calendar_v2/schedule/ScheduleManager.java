package com.example.calendar_v2.calendar_v2.schedule;

import android.content.Context;

import com.example.calendar_v2.schedule.Holiday;
import com.example.calendar_v2.schedule.HolidayDBHelper;
import com.example.calendar_v2.schedule.Schedule;
import com.example.calendar_v2.schedule.UserSchedule;
import com.example.calendar_v2.schedule.UserScheduleDBHelper;
import com.example.calendar_v2.time.Date;
import com.example.calendar_v2.time.DateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 모든 Schedule을 관리하는 클래스
 * Schedule을 추가 및 제거하고, 원하는 스케줄을 찾을 수 있도록 기능을 제공한다.
 */
public class ScheduleManager {

    private UserScheduleDBHelper userScheduleDBHelper;
    private HolidayDBHelper holidayDBHelper;


    public ScheduleManager(Context context) {
        userScheduleDBHelper = new UserScheduleDBHelper(context, null, 1);
        holidayDBHelper = new HolidayDBHelper(context, null, 1);
    }


    /**
     * 저장할 Schedule을 인자로 받아서 db에 저장한다.
     *
     * @param schedule 영구 저장할 Schedule
     */
    public void addSchedule(Schedule schedule) {
        if (schedule instanceof UserSchedule) {
            userScheduleDBHelper.add((UserSchedule) schedule);
        } else if (schedule instanceof Holiday) {
            holidayDBHelper.add((Holiday) schedule);
        }
    }

    /**
     * 제거할 Schedule을 인자로 받아서 db에서 삭제한다.
     *
     * @param schedule 제거할 Schedule
     */
    public void removeSchedule(Schedule schedule) {
        if (schedule instanceof UserSchedule) {
            userScheduleDBHelper.remove((UserSchedule) schedule);
        } else if (schedule instanceof Holiday) {
            holidayDBHelper.remove((Holiday) schedule);
        }
    }

    /**
     * 인자로 넘긴 날짜 사이에 포함되는 Holiday와 UserSchedule을 찾아 List에 담아 반환한다.
     *
     * @param startDate 탐색할 기간의 시작 날짜
     * @param endDate 탐색할 기간의 끝 날짜
     * @return 탐색한 기간 사이에 포함되는 Schedule들이 담긴 List
     */
    public List<Schedule> searchScheduleBetween(Date startDate, Date endDate) {
        List<Schedule> schedules = new ArrayList<>();
        DateTime startDateTime = startDate.atTime(0, 0);
        DateTime endDateTime = endDate.atTime(23, 59);

        for (Holiday holiday : holidayDBHelper.getHolidays()) {
            Date holidayDate = holiday.getDate();

            // 공휴일이 범위안에 있다면 schedules에 추가
            if (holidayDate.compareTo(startDate) >= 0 && holidayDate.compareTo(endDate) <= 0)
            {
                schedules.add(holiday);
            }
        }

        for (UserSchedule userSchedule : userScheduleDBHelper.getUserSchedules()) {
            DateTime scheduleStartDateTime = userSchedule.getStartDateTime();
            DateTime scheduleEndDateTime = userSchedule.getEndDateTime();

            // 스케줄이 범위안에 있다면 schedules에 추가
            if ((scheduleStartDateTime.compareTo(startDateTime) >= 0 && scheduleStartDateTime.compareTo(endDateTime) <= 0)
                    || (scheduleEndDateTime.compareTo((startDateTime)) >= 0 && scheduleEndDateTime.compareTo(endDateTime) <= 0))
            {
                schedules.add(userSchedule);
            }
        }

        sortSchedules(schedules);

        return schedules;
    }


    /* private method line */
    /**
     * 인자로 받은 schedules를 시작 날짜 순서로 정렬한다.
     * 만약 시작 날짜가 같다면, 스케줄의 길이가 긴 순서대로 정렬한다.
     *
     * @param schedules 정렬할 List<Schedule>
     */
    private void sortSchedules(List<Schedule> schedules) {
        Comparator<Schedule> comparator = new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                int result = o1.getStartDateTime().toDate().compareTo(o2.getStartDateTime().toDate());

                if (result != 0) {
                    return result;
                } else {
                    int length1 = Date.getNumberOfDayBetween(o1.getStartDateTime().toDate(), o1.getEndDateTime().toDate());
                    int length2 = Date.getNumberOfDayBetween(o2.getStartDateTime().toDate(), o2.getEndDateTime().toDate());

                    return length2 - length1;
                }
            }
        };

        schedules.sort(comparator);
    }

}
