package com.zack.dataModel;

/**
 * com.zack.DataModel.Shift class represents a shift to be added to whentowork.
 * A shift contains a date, time, day of week, course type, staff type
 * and a number for how many copies should be created.
 *
 * @author Zack Dreizler
 * @version 0.1
 */
public class Shift {
    private String date;
    private String dayOfWeek;
    private String courseType;
    private String time;
    private String staffType;
    private int numShifts;

    public Shift(String date, String dayOfWeek, String courseType, String time, String staffType, int numShifts) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.courseType = courseType;
        this.time = time;
        this.staffType = staffType;
        this.numShifts = numShifts;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getCourseType() {
        return courseType;
    }

    public String getTime() {
        return time;
    }

    public String getStaffType() {
        return staffType;
    }

    public int getNumShifts() {
        return numShifts;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "date='" + date + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", courseType='" + courseType + '\'' +
                ", time='" + time + '\'' +
                ", staffType='" + staffType + '\'' +
                ", numShifts=" + numShifts +
                '}';
    }
}
