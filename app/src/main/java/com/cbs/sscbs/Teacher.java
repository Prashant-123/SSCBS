package com.cbs.sscbs;

public class Teacher {

    private String name ;
    private String pos ;
    private int img;
    private int index ;
    private String qualification ;
    private String email ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getImg() { return img; }

    public void setImg(int img) {
        this.img = img;
    }

    public String getQualification()
    {
        return qualification ;
    }
    public void setQualification(String text )
    {
    this.qualification = text ;
    }
    public String getEmail()
    {
        return email ;
    }
    public void setEmail(String email )
    {
        this.email = email ;
    }
}


