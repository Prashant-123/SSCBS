package com.cbs.sscbs.Attendance;

/**
 * Created by Tanya on 2/14/2018.
 */

public class StudentsDataClass {
    public StudentsDataClass(String subject, Double attendance, Double total) {
        this.subject = subject;
        this.attendance = attendance;
        this.total = total;
    }

    private String subject;
    private Double attendance;
    private Double total;

    public Double getAttendance() {
        return attendance;
    }

//    public void setAttendance(Double attendance) {
//        this.total = attendance;
//    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.attendance = total;
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
