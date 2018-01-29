package com.woxapp.maptest.entity.map;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Leg extends RealmObject {

    private String endAddress;
    private EndLocation endLocation;
    private String startAddress;
    private StartLocation startLocation;
    private RealmList<Step> steps = null;

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(RealmList<Step> steps) {
        this.steps = steps;
    }
}
