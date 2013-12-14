package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link TripSchedule} 
 */
@Named
@Singleton
public class TripScheduleRepository extends GenericRepository<TripSchedule, Integer> {

    public TripScheduleRepository() {
        super(TripSchedule.class);
    }

    @Override
    public TripSchedule getNew() {
        return new TripSchedule();
    }

    @Override
    public TripSchedule getNewWithDefaults() {
        return getNew().withDefaults();
    }
}