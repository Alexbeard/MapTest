package com.woxapp.maptest.entity.mapper;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;
import com.woxapp.maptest.entity.map.DirectionResult;
import com.woxapp.maptest.entity.map.Leg;
import com.woxapp.maptest.entity.map.Route;


public class FromRealmDirections implements FromRealmMapper<DirectionResult, DirectionsResult> {

    @Override
    public DirectionsResult map(DirectionResult directionResult) {

        DirectionsResult result = new DirectionsResult();
        result.routes = getRoutes(directionResult);

        return result;
    }

    private DirectionsRoute[] getRoutes(DirectionResult result) {
        DirectionsRoute[] routes = new DirectionsRoute[result.getRoutes().size()];
        for (int i = 0; i < result.getRoutes().size(); i++) {
            DirectionsRoute route = new DirectionsRoute();
            route.legs = getLegs(result.getRoutes().get(i));

            if (result.getRoutes().get(i).getOverviewPolyline() != null) {
                route.overviewPolyline = new EncodedPolyline(result.getRoutes().get(i).getOverviewPolyline().getPoints());
            }
            routes[i] = route;
        }

        return routes;
    }

    private DirectionsLeg[] getLegs(Route route) {
        DirectionsLeg[] legs = new DirectionsLeg[route.getLegs().size()];
        for (int i = 0; i < route.getLegs().size(); i++) {
            DirectionsLeg leg = new DirectionsLeg();
            leg.steps = getSteps(route.getLegs().get(i));
            leg.startAddress = route.getLegs().get(i).getStartAddress();
            leg.endAddress = route.getLegs().get(i).getEndAddress();
            legs[i] = leg;
        }
        return legs;
    }

    private DirectionsStep[] getSteps(Leg leg) {
        DirectionsStep[] steps = new DirectionsStep[leg.getSteps().size()];
        for (int i = 0; i < leg.getSteps().size(); i++) {
            DirectionsStep step = new DirectionsStep();
            step.endLocation = new LatLng(leg.getSteps().get(i).getEndLocation().getLat(),
                    leg.getSteps().get(i).getEndLocation().getLng());
            step.startLocation = new LatLng(leg.getSteps().get(i).getStartLocation().getLat(),
                    leg.getSteps().get(i).getStartLocation().getLng());

            if (leg.getSteps().get(i).getPolyline() != null) {
                step.polyline = new EncodedPolyline(leg.getSteps().get(i).getPolyline().getPoints());
            }
            steps[i] = step;
        }
        return steps;
    }
}
