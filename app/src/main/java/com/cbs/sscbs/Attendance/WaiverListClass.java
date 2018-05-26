package com.cbs.sscbs.Attendance;

class WaiverListClass {

    String roll , waivers;
    public
    WaiverListClass(String roll , String waivers){
        this.roll = roll ;
        this.waivers = waivers;
    }
    public String getRollNo() {
        return roll;
    }
    public String getWaivers(){
        return waivers;
    }


}
