package com.example.storedemo;

public class dataholder {

    String roll, name, course, duration;

    dataholder(){

    }

    public dataholder(String roll, String name, String course, String duration) {
        this.roll = roll;
        this.name = name;
        this.course = course;
        this.duration = duration;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
