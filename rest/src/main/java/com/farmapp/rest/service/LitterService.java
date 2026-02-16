package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.entity.Litter;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LitterService {

    Single<Litter> setLitter(Litter litter);
    Single<List<Litter>> getAllLitters();
    Single<List<Litter>> getLittersById(List<Long> litterIds);
    Completable deleteLittersById(List<Long> litterIds);

}
