package com.cbs.sscbs.Fragments;

import java.util.ArrayList;

/**
 * Created by prashant on 31/1/18.
 */

public class DataClas {
    String id, dept, name;
    static ArrayList<String> classes;

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
