package com.woxapp.maptest.entity.mapper;

import io.realm.RealmObject;


public interface FromRealmMapper<T extends RealmObject, U> extends Mapper<T, U> {
    @Override
    U map(T t);
}
