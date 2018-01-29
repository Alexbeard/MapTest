package com.woxapp.maptest.entity.map;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Route extends RealmObject {

    private RealmList<Leg> legs = null;
    private OverviewPolyline overviewPolyline;

    public RealmList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(RealmList<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

}
