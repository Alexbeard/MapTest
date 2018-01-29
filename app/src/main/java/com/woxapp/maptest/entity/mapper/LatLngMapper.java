package com.woxapp.maptest.entity.mapper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class LatLngMapper implements Mapper<List<com.google.maps.model.LatLng>, List<LatLng>> {

    @Override
    public List<LatLng> map(List<com.google.maps.model.LatLng> latLng) {
        ArrayList<LatLng> latLngs = new ArrayList<>();

        for (int i = 0; i < latLng.size(); i++) {
            latLngs.add(new LatLng(latLng.get(i).lat, latLng.get(i).lng));
        }
        return latLngs;
    }
}
