package com.cbs.sscbs.Attendance;

class WaiverListClass {
    String roll, waivers;

    public WaiverListClass(String roll, String waivers) {
        this.roll = roll;
        this.waivers = waivers;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getWaivers() {
        return waivers;
    }

    public void setWaivers(String waivers) {
        this.waivers = waivers;
    }
}
