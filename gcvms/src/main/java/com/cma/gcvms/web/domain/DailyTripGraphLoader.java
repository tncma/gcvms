package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.repository.DailyTripRepository;
import com.cma.gcvms.repository.support.EntityGraphLoader;

/**
 * Preloads the {@link DailyTrip} associations required by the view layer.
 * 
 * Do not use other GraphLoaders in this GraphLoader. 
 */
@Named
@Singleton
public class DailyTripGraphLoader extends EntityGraphLoader<DailyTrip, Integer> {
    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected DailyTripGraphLoader() {
        super();
    }

    @Inject
    public DailyTripGraphLoader(DailyTripRepository dailyTripRepository) {
        super(dailyTripRepository);
    }

    @Override
    public void loadGraph(DailyTrip dailyTrip) {
        loadSingular(dailyTrip.getVehicle());
        loadSingular(dailyTrip.getRoute());
        loadSingular(dailyTrip.getSupervisor());
    }
}