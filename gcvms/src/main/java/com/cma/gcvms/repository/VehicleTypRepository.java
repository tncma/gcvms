package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link VehicleTyp} 
 */
@Named
@Singleton
public class VehicleTypRepository extends GenericRepository<VehicleTyp, Integer> {

    public VehicleTypRepository() {
        super(VehicleTyp.class);
    }

    @Override
    public VehicleTyp getNew() {
        return new VehicleTyp();
    }

    @Override
    public VehicleTyp getNewWithDefaults() {
        return getNew().withDefaults();
    }
}