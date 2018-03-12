package com.cbs.sscbs.Events;

/**
 * Created by Prashant on 22-12-2017.
 */

public class BookingsDataClass {

    String venue, time;

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


    public BookingsDataClass(String venue, String time) {

        this.venue = venue;
        this.time = time;
    }
}
