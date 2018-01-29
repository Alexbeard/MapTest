package com.woxapp.maptest.entity.mapper;


import io.realm.RealmObject;


public interface ToRealmMapper<T, U extends RealmObject> extends Mapper<T, U> {
    @Override
    U map(T t);
}