package com.cbs.sscbs.Events;

/**
 * Created by Prashant on 25-11-2017.
 */

class EventsDataClass {

    private String title, organiser, venue, time, sot, desc, link, mobNo, imageUrl;
    private int img, delId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDelId() {
        return delId;
    }
    public void setDelId(int delId) {
        this.delId = delId;
    }
    public EventsDataClass() {}

    public String getSot() {
        return sot;
    }

    public void setSot(String sot) {
        this.sot = sot;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public int getImg() { return img; }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public EventsDataClass(String title, String organiser, String venue, String time, String sot , int img, int delId, String desc, String link, String mobNo, String imageUrl) {
        this.title = title;
        this.organiser = organiser;
        this.venue = venue;
        this.time = time;
        this.sot = sot;
        this.img = img ;
        this.delId = delId;
        this.desc = desc;
        this.link = link;
        this.imageUrl = imageUrl;
        this.mobNo = mobNo;
    }

}