package com.woxapp.maptest.entity.map;

import io.realm.RealmObject;

public class Step extends RealmObject {

    private EndLocation endLocation;
    private Polyline polyline;
    private StartLocation startLocation;

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

}