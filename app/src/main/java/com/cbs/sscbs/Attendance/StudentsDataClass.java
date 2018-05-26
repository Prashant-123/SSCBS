package com.cbs.sscbs.Attendance;

/**
 * Created by Tanya on 2/14/2018.
 */

public class StudentsDataClass {
    public StudentsDataClass(String subject, Integer attendance, Integer total) {
        this.subject = subject;
        this.attendance = attendance;
        this.total = total;
    }

    private String subject;
    private Integer attendance;
    private Integer total;

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.total = attendance;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.attendance = total;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}