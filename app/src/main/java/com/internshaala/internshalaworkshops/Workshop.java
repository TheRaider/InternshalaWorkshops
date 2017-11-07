package com.internshaala.internshalaworkshops;

/**
 * Created by vinee_000 on 07-11-2017.
 */

public class Workshop {
    String workshopId;

    String title="";
    String company="";
    String location="";
    String duration="";
    String cost="";
    String applyby="";
    boolean applied=false;

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public Workshop() {
    }

    public Workshop(String workshopId, String title, String company, String location, String duration, String cost, String applyby) {

        this.workshopId = workshopId;
        this.title = title;
        this.company = company;
        this.location = location;
        this.duration = duration;
        this.cost = cost;
        this.applyby = applyby;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getApplyby() {
        return applyby;
    }

    public void setApplyby(String applyby) {
        this.applyby = applyby;
    }
}
