package com.cbs.sscbs.Attendance;

/**
 * Created by Tanya on 2/14/2018.
 */

public class StudentsDataClass {
    public StudentsDataClass(String subject) {
        this.subject = subject;
    }

    private String subject;

    public StudentsDataClass() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
