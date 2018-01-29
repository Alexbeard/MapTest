package com.woxapp.maptest.ui.catalog;

import com.orhanobut.logger.Logger;
import com.woxapp.maptest.model.MapRepository;
import com.woxapp.maptest.model.Repository;


public class CatalogPresenter implements CatalogContract.Presenter {

    CatalogContract.View view;
    Repository model;

    public CatalogPresenter(CatalogContract.View view) {
        this.view = view;
        model = MapRepository.getInstance();
    }

    @Override
    public void loadDirections() {
        model.getAllDirections()
                .subscribe(
                        directionsResults -> {
                            view.onLoadDirectionsSuccess(directionsResults);
                        },
                        throwable -> {
                            Logger.d(throwable.getMessage());
                        }
                );
    }
}
