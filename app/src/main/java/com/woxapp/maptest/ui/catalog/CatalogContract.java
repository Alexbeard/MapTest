package com.woxapp.maptest.ui.catalog;


import com.google.maps.model.DirectionsResult;

import java.util.List;


public interface CatalogContract {

    interface View {
        void onLoadDirectionsSuccess(List<DirectionsResult> directions);
    }

    interface Presenter {
        void loadDirections();
    }

}
