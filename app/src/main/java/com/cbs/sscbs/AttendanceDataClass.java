package com.cbs.sscbs;

import android.widget.CheckBox;

/**
 * Created by Prashant on 31-12-2017.
 */

public class AttendanceDataClass {

    private String name;
    private long roll;
    private boolean checked;

    private int attendance;
    private CheckBox checkBox;

    public long getRoll() {
        return roll;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {

        this.checkBox = checkBox;
    }

    public boolean isChecked() {

        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public void setRoll(long roll) {
        this.roll = roll;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public AttendanceDataClass(String name, long roll, int attendance)
    {
        this.roll = roll;
        this.attendance = attendance;
        this.name = name;
    }
}
