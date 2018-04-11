package com.cbs.sscbs.TeachersTimetable;

public class TeacherDataClass {
    private String name ;
    private long  imageUrl , timetableUrl;

    public TeacherDataClass(String name, long imageUrl, long timetableUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.timetableUrl = timetableUrl;
        }
    public TeacherDataClass() {}


    public long getImg() { return imageUrl; }

    public void setImg(long img) {
        this.imageUrl = img;
    }

    public String getTName() {
        return name;
    }

    public void setTName(String name) {
        this.name = name;
    }

    public long getTimeTableUrl() {
        return timetableUrl;
    }

    public void setDesc(long timetableUrl) {
        this.timetableUrl = timetableUrl;
    }

}
