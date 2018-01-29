package com.woxapp.maptest.entity.mapper;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.woxapp.maptest.entity.map.DirectionResult;
import com.woxapp.maptest.entity.map.EndLocation;
import com.woxapp.maptest.entity.map.Leg;
import com.woxapp.maptest.entity.map.OverviewPolyline;
import com.woxapp.maptest.entity.map.Polyline;
import com.woxapp.maptest.entity.map.Route;
import com.woxapp.maptest.entity.map.StartLocation;
import com.woxapp.maptest.entity.map.Step;

import io.realm.RealmList;


public class ToRealmDirections implements ToRealmMapper<DirectionsResult, DirectionResult> {

    @Override
    public DirectionResult map(DirectionsResult directionResult) {

        DirectionResult result = new DirectionResult();
        result.setRoutes(getRoutes(directionResult));

        return result;
    }


    private RealmList<Route> getRoutes(DirectionsResult result) {

        RealmList<Route> routes = new RealmList<>();

        for (int i = 0; i < result.routes.length; i++) {
            Route route = new Route();
            route.setLegs(getLegs(result.routes[i]));
            OverviewPolyline overviewPolyline = new OverviewPolyline();
            overviewPolyline.setPoints(result.routes[i].overviewPolyline.getEncodedPath());
            route.setOverviewPolyline(overviewPolyline);
            routes.add(route);
        }


        return routes;
    }

    private RealmList<Leg> getLegs(DirectionsRoute route) {
        RealmList<Leg> legs = new RealmList<>();
        for (int i = 0; i < route.legs.length; i++) {
            Leg leg = new Leg();
            leg.setSteps(getSteps(route.legs[i]));
            leg.setStartAddress(route.legs[i].startAddress);
            leg.setEndAddress(route.legs[i].endAddress);
            legs.add(leg);
        }
        return legs;
    }

    private RealmList<Step> getSteps(DirectionsLeg leg) {
        RealmList<Step> steps = new RealmList<>();
        for (int i = 0; i < leg.steps.length; i++) {
            Step step = new Step();

            StartLocation startLocation = new StartLocation();
            startLocation.setLat(leg.steps[i].startLocation.lat);
            startLocation.setLng(leg.steps[i].startLocation.lat);
            step.setStartLocation(startLocation);

            EndLocation endlocation = new EndLocation();
            endlocation.setLat(leg.steps[i].endLocation.lat);
            endlocation.setLng(leg.steps[i].endLocation.lat);
            step.setEndLocation(endlocation);

            Polyline polyline = new Polyline();
            polyline.setPoints(leg.steps[i].polyline.getEncodedPath());
            step.setPolyline(polyline);

            steps.add(step);
        }

        return steps;
    }
}
