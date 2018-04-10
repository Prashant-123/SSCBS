package com.cbs.sscbs.TeachersTimetable;

public class TeacherDataClass {
    private String name ;
    private int  imageUrl , timetableUrl;

    public TeacherDataClass(String name, int imageUrl, int timetableUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.timetableUrl = timetableUrl;
        }
    public TeacherDataClass() {}


    public int getImg() { return imageUrl; }

    public void setImg(int img) {
        this.imageUrl = img;
    }

    public String getTName() {
        return name;
    }

    public void setTName(String name) {
        this.name = name;
    }

    public int getTimeTableUrl() {
        return timetableUrl;
    }

    public void setDesc(int timetableUrl) {
        this.timetableUrl = timetableUrl;
    }

}
