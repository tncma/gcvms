package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link Municipality} 
 */
@Named
@Singleton
public class MunicipalityRepository extends GenericRepository<Municipality, Integer> {

    public MunicipalityRepository() {
        super(Municipality.class);
    }

    @Override
    public Municipality getNew() {
        return new Municipality();
    }

    @Override
    public Municipality getNewWithDefaults() {
        return getNew().withDefaults();
    }
}