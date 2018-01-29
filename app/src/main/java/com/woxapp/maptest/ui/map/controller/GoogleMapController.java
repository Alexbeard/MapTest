package com.woxapp.maptest.ui.map.controller;

import android.graphics.Color;
import android.location.Address;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.woxapp.maptest.entity.GeoSearchResult;
import com.woxapp.maptest.entity.mapper.LatLngMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GoogleMapController implements MapController {

    private GoogleMap googleMap;
    private List<Polyline> polylines = new ArrayList<>();
    private Map<String, Marker> markerMap = new LinkedHashMap<>();
    private Marker carMarker;


    public GoogleMapController(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public Marker addMarker(Address address) {
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(address.getLatitude(), address.getLongitude())));
    }

    public void addPolyline(DirectionsResult results) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        polylines.add(googleMap.addPolyline(new PolylineOptions().color(Color.RED).addAll(decodedPath)));
    }

    public void positionCamera(DirectionsRoute route) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[0].startLocation.lat, route.legs[0].startLocation.lng), 12));
    }

    public void positionCamera(Address address) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 12));
    }

    public void clearPolylines() {
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).remove();
        }
    }

    @Override
    public void clearWay() {
        if (carMarker != null) {
            carMarker.remove();
        }
        clearPolylines();
    }

    public void clearMap() {
        googleMap.clear();
        markerMap.clear();
    }

    @Override
    public void clearMarker(String address) {
        if (markerMap.containsKey(address)) {
            markerMap.get(address).remove();
            markerMap.remove(address);
        }
    }

    @Override
    public void addAddress(GeoSearchResult result) {
        if (markerMap.size() <= 5) {
            markerMap.put(result.getAddress(), addMarker(result.getFullAddress()));
            positionCamera(result.getFullAddress());
        }
    }

    @Override
    public List<String> getAddresses() {
        return new ArrayList<>(markerMap.keySet());
    }

    public void setAnimation(DirectionsResult result, @DrawableRes final int image) {
        List<LatLng> directionPoint;
        directionPoint = getDirectionPoint(result);

        carMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(image))
                .position(directionPoint.get(0))
                .flat(true));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(directionPoint.get(0), 12));

        animateMarker(carMarker, directionPoint, false);
    }

    private List<LatLng> getDirectionPoint(DirectionsResult result) {
        ArrayList<LatLng> routelist = new ArrayList<>();
        if (result.routes.length > 0) {
            DirectionsRoute routeA = result.routes[0];
            if (routeA.legs.length > 0) {
                for (int i = 0; i < routeA.legs.length; i++) {
                    List<DirectionsStep> steps = Arrays.asList(routeA.legs[i].steps);
                    DirectionsStep step;
                    for (int j = 0; j < steps.size(); j++) {
                        step = steps.get(j);
                        routelist.add(new LatLng(step.startLocation.lat, step.startLocation.lng));
                        routelist.addAll(new LatLngMapper().map(step.polyline.decodePath()));
                        routelist.add(new LatLng(step.endLocation.lat, step.endLocation.lng));
                    }
                }
            }
        }
        return routelist;
    }

    private void animateMarker(final Marker marker, final List<LatLng> directionPoint,
                               final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 30000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                if (i < directionPoint.size())
                    marker.setPosition(directionPoint.get(i));
                i++;
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
}
