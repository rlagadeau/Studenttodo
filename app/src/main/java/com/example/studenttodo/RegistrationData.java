package com.example.studenttodo;

public class RegistrationData {
    private int id;
    private String hours;
    private String school;
    private String location;
    private String subject;
    private String amountstuds;
    private String regdate;

    public RegistrationData(String hours, String school, String location, String subject, String amountstuds, String regdate, int id) {
        this.hours = hours;
        this.school = school;
        this.location = location;
        this.subject = subject;
        this.amountstuds = amountstuds;
        this.regdate = regdate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmountstuds() {
        return amountstuds;
    }

    public void setAmountstuds(String amountstuds) {
        this.amountstuds = amountstuds;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
