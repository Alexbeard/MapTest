package com.woxapp.maptest.ui.map;

import com.google.maps.model.DirectionsResult;

import java.util.List;


public interface MapContract {

    interface View {
        void directionLoadSuccess(DirectionsResult result);
    }

    interface Presenter {
        void getDirection(List<String> addresses);
    }
}
