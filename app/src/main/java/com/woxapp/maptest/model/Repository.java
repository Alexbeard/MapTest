package com.woxapp.maptest.model;

import com.google.maps.model.DirectionsResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public interface Repository {

    Observable<DirectionsResult> getDirection(String origin, String destination, String... waypoints);

    void saveDirection(DirectionsResult result);

    Single<List<DirectionsResult>> getAllDirections();

}
