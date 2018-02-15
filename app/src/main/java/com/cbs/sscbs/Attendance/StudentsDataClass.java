package com.cbs.sscbs.Attendance;

/**
 * Created by Tanya on 2/14/2018.
 */

public class StudentsDataClass {
    public StudentsDataClass(String subject, Double attendance) {
        this.subject = subject;
        this.attendance = attendance;
    }

    private String subject;
    private Double attendance;

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }

    public StudentsDataClass() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
