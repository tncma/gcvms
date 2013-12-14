package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link DailyTrip} 
 */
@Named
@Singleton
public class DailyTripRepository extends GenericRepository<DailyTrip, Integer> {

    public DailyTripRepository() {
        super(DailyTrip.class);
    }

    @Override
    public DailyTrip getNew() {
        return new DailyTrip();
    }

    @Override
    public DailyTrip getNewWithDefaults() {
        return getNew().withDefaults();
    }
}