package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.entity.Litter;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class LitterServiceImpl implements LitterService{

    @Override
    public Single<Litter> setLitter(Litter litter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLitter'");
    }

    @Override
    public Single<List<Litter>> getAllLitters() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllLitters'");
    }

    @Override
    public Single<List<Litter>> getLittersById(List<Long> litterIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLittersById'");
    }

    @Override
    public Completable deleteLittersById(List<Long> litterIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteLittersById'");
    }

}
