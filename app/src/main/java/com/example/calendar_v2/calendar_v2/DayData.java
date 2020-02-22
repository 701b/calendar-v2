package com.example.calendar_v2.calendar_v2;



import com.example.calendar_v2.calendar_v2.schedule.Schedule;
import com.example.calendar_v2.calendar_v2.time.Date;

import java.util.HashMap;
import java.util.Map;

/**
 * 특정 날짜에 대한 정보를 담고 있다.
 */
public class DayData {

    private Date date;
    private Map<Integer, Schedule> scheduleMap;


    public DayData(Date date) {
        this.date = date;

        scheduleMap = new HashMap<>();
    }


    /**
     * 비어있는 인덱스 중 가장 처음 인덱스를 반환한다.
     *
     * @return 비어있는 가장 첫 인덱스
     */
    public int getFirstEmptyIndex() {
        for (int i = 0;; i++) {
            if (!scheduleMap.containsKey(i)) {
                return i;
            }
        }
    }

    /**
     * 해당 인덱스의 Schedule을 반환한다.
     *
     * @param index 인덱스
     * @return 해당 인덱스의 Schedule
     */
    public Schedule getSchedule(int index) {
        return scheduleMap.get(index);
    }

    /**
     * 해당 인덱스에 Schedule을 추가한다.
     *
     * @param schedule 추가할 Schedule
     * @param index 추가할 Schedule의 위치
     * @exception AlreadyScheduleExistException 이미 스케줄이 있는 인덱스에 추가하면 던져진다.
     */
    public void addSchedule(Schedule schedule, int index) {
        if (scheduleMap.containsKey(index)) {
            throw new AlreadyScheduleExistException();
        }

        scheduleMap.put(index, schedule);
    }


    /* Getter Setter Line*/
    public Date getDate() {
        return date;
    }

    public Map<Integer, Schedule> getScheduleMap() {  return scheduleMap;  }

    /* Exception Class Line*/
    /**
     * 이미 스케줄이 있는 인덱스에 스케줄을 추가하려할 때 던져지는 예외
     */
    class AlreadyScheduleExistException extends RuntimeException {
        public AlreadyScheduleExistException() {
            super("해당 인덱스에 이미 스케줄이 존재합니다");
        }
    }
}
