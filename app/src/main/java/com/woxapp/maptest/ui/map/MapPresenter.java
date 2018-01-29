package com.woxapp.maptest.ui.map;

import com.orhanobut.logger.Logger;
import com.woxapp.maptest.model.MapRepository;
import com.woxapp.maptest.model.Repository;

import java.util.List;


public class MapPresenter implements MapContract.Presenter {

    private static int startIndex = 0, endIndex = 1;
    private MapContract.View view;
    private Repository model;

    public MapPresenter(MapContract.View view) {
        this.view = view;
        model = MapRepository.getInstance();
    }

    @Override
    public void getDirection(List<String> addresses) {

        if (addresses.size() > 2) {
            model.getDirection(
                    addresses.get(startIndex),
                    addresses.get(endIndex),
                    addresses.subList(endIndex + 1, addresses.size()).toArray(new String[addresses.size() - 2])
            )
                    .subscribe(
                            result -> view.directionLoadSuccess(result),
                            throwable -> Logger.d(throwable.getMessage())
                    );
        } else if (addresses.size() == 2) {
            model.getDirection(addresses.get(startIndex), addresses.get(endIndex))
                    .subscribe(
                            result -> view.directionLoadSuccess(result),
                            throwable -> Logger.d(throwable.getMessage())
                    );
        }
    }
}
