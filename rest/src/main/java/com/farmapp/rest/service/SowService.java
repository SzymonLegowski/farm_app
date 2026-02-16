package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.entity.Sow;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface SowService {

    Single<Sow> setSow(Sow sow);
    Single<List<Sow>> getAllSows();
    Single<List<Sow>> getSowsById(List<Long> sowIds);
    Completable deleteSowsById(List<Long> sowIds);

}
