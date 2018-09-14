package com.zack.dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * com.zack.dataModel.Shift class represents a shift to be added to whentowork.
 * A shift contains a date, time, day of week, course type, staff type
 * and a number for how many copies should be created.
 *
 * @author Zack Dreizler
 * @version 1
 */
public class Shift {
    private SimpleStringProperty date;
    private SimpleStringProperty dayOfWeek;
    private SimpleStringProperty courseType;
    private SimpleStringProperty time;
    private SimpleStringProperty staffType;
    private SimpleIntegerProperty numShifts;

    public Shift(String date, String dayOfWeek, String courseType,
                 String time, String staffType, Integer numShifts) {
        this.date = new SimpleStringProperty(date);
        this.dayOfWeek = new SimpleStringProperty(dayOfWeek);
        this.courseType = new SimpleStringProperty(courseType);
        this.time = new SimpleStringProperty(time);
        this.staffType = new SimpleStringProperty(staffType);
        this.numShifts = new SimpleIntegerProperty(numShifts);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDayOfWeek() {
        return dayOfWeek.get();
    }

    public SimpleStringProperty dayOfWeekProperty() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek.set(dayOfWeek);
    }

    public String getCourseType() {
        return courseType.get();
    }

    public SimpleStringProperty courseTypeProperty() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType.set(courseType);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getStaffType() {
        return staffType.get();
    }

    public SimpleStringProperty staffTypeProperty() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType.set(staffType);
    }

    public int getNumShifts() {
        return numShifts.get();
    }

    public SimpleIntegerProperty numShiftsProperty() {
        return numShifts;
    }

    public void setNumShifts(int numShifts) {
        this.numShifts.set(numShifts);
    }

    @Override
    public String toString() {
        return  date.get() + " " + dayOfWeek.get() + " " + courseType.get() + time.get() +
                " " + staffType.get() +  " " + numShifts.get();
    }
}
