package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.repository.TripScheduleRepository;
import com.cma.gcvms.repository.support.EntityGraphLoader;

/**
 * Preloads the {@link TripSchedule} associations required by the view layer.
 * 
 * Do not use other GraphLoaders in this GraphLoader. 
 */
@Named
@Singleton
public class TripScheduleGraphLoader extends EntityGraphLoader<TripSchedule, Integer> {
    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected TripScheduleGraphLoader() {
        super();
    }

    @Inject
    public TripScheduleGraphLoader(TripScheduleRepository tripScheduleRepository) {
        super(tripScheduleRepository);
    }

    @Override
    public void loadGraph(TripSchedule tripSchedule) {
        loadSingular(tripSchedule.getRoute());
        loadSingular(tripSchedule.getVehicle());
    }
}