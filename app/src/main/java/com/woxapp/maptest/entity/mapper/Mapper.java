package com.woxapp.maptest.entity.mapper;


public interface Mapper<T, R> {

    R map(T t);
}
