package com.cbs.sscbs.Attendance;

import android.widget.CheckBox;

class WaiverDataClass {

    private String name;
    private String roll;
    private String attendance;

    public WaiverDataClass(String name, String roll) {
        this.name = name;
        this.roll = roll;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRoll() {
        return roll;
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
}
