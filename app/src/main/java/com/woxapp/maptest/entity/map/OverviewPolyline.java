package com.woxapp.maptest.entity.map;

import io.realm.RealmObject;


public class OverviewPolyline extends RealmObject {

    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

}