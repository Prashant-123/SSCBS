package com.cbs.sscbs.TeachersTimetable;

public class TeacherDataClass {
    private String name ;
    private String  imageUrl , timetableUrl;

    public TeacherDataClass(String name, String imageUrl, String timetableUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.timetableUrl = timetableUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimetableUrl() {
        return timetableUrl;
    }

    public void setTimetableUrl(String timetableUrl) {
        this.timetableUrl = timetableUrl;
    }
}
