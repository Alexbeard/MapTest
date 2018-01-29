package com.woxapp.maptest.model;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.woxapp.maptest.entity.map.DirectionResult;
import com.woxapp.maptest.entity.mapper.FromRealmDirections;
import com.woxapp.maptest.entity.mapper.ToRealmDirections;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;


public class MapRepository implements Repository {


    private static volatile MapRepository instance;

    private MapRepository() {
        //Prevent form the reflection api.
        if (instance != null) {
            throw new AssertionError("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static MapRepository getInstance() {
        if (instance == null) {
            synchronized (MapRepository.class) {
                if (instance == null) {
                    instance = new MapRepository();
                }
            }
        }
        return instance;
    }

    //Make singleton from serialize and deserialize operation.
    protected MapRepository readResolve() {
        return getInstance();
    }


    @Override
    public Observable<DirectionsResult> getDirection(String origin, String destination, String... waypoints) {
        return Observable.fromCallable(() -> getDirectionsDetails(origin, destination, waypoints))
                .doOnNext(this::saveDirection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveDirection(DirectionsResult result) {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(new ToRealmDirections().map(result)));
    }

    @Override
    public Single<List<DirectionsResult>> getAllDirections() {
        Realm realm = Realm.getDefaultInstance();

        return Observable.just(realm.copyFromRealm(realm.where(DirectionResult.class).findAll()))
                .flatMap(Observable::fromIterable)
                .map(directionResult -> new FromRealmDirections().map(directionResult))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, String... waypoints) throws InterruptedException, ApiException, IOException {
        DateTime now = new DateTime();
        return DirectionsApi.newRequest(getGeoContext())
                .mode(TravelMode.DRIVING)
                .origin(origin)
                .destination(destination)
                .optimizeWaypoints(true)
                .waypoints(waypoints)
                .departureTime(now)
                .await();
    }


    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey("AIzaSyD0qrhnKOWHVVXzKgWbG2IiKEusxkwEucA")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }
}
