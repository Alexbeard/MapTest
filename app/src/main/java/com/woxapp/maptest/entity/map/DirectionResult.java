package com.woxapp.maptest.entity.map;

import io.realm.RealmList;
import io.realm.RealmObject;


public class DirectionResult extends RealmObject {
    private RealmList<Route> routes = null;

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }

}
