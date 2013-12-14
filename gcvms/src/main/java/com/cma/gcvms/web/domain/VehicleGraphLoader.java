package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.repository.VehicleRepository;
import com.cma.gcvms.repository.support.EntityGraphLoader;

/**
 * Preloads the {@link Vehicle} associations required by the view layer.
 * 
 * Do not use other GraphLoaders in this GraphLoader. 
 */
@Named
@Singleton
public class VehicleGraphLoader extends EntityGraphLoader<Vehicle, Integer> {
    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected VehicleGraphLoader() {
        super();
    }

    @Inject
    public VehicleGraphLoader(VehicleRepository vehicleRepository) {
        super(vehicleRepository);
    }

    @Override
    public void loadGraph(Vehicle vehicle) {
        loadSingular(vehicle.getTyp());
    }
}