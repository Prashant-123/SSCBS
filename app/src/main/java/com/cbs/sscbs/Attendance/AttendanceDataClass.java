package com.cbs.sscbs.Attendance;

import android.widget.CheckBox;

/**
 * Created by Prashant on 31-12-2017.
 */

public class AttendanceDataClass {

    private String name;
    private String roll;
    private boolean checked;

    private String attendance;
    private CheckBox checkBox;

    public String getRoll() {
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
    public void setRoll(String roll) {
        this.roll = roll;
    }
    public String getAttendance() {
        return attendance;
    }
    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public AttendanceDataClass(String name, String roll) {
        this.roll = roll;
        this.name = name;
    }
}
