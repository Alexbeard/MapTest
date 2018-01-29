package com.woxapp.maptest.ui.map.controller;

import android.location.Address;
import android.support.annotation.DrawableRes;

import com.google.android.gms.maps.model.Marker;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.woxapp.maptest.entity.GeoSearchResult;

import java.util.List;

public interface MapController {

    Marker addMarker(Address address);

    void addPolyline(DirectionsResult results);

    void positionCamera(DirectionsRoute route);

    void positionCamera(Address address);

    void setAnimation(DirectionsResult result, @DrawableRes final int image);

    void clearPolylines();

    void clearMarker(String address);

    void clearWay();

    void clearMap();

    void addAddress(GeoSearchResult result);

    List<String> getAddresses();
}
