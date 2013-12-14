package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link Dept} 
 */
@Named
@Singleton
public class DeptRepository extends GenericRepository<Dept, Integer> {

    public DeptRepository() {
        super(Dept.class);
    }

    @Override
    public Dept getNew() {
        return new Dept();
    }

    @Override
    public Dept getNewWithDefaults() {
        return getNew().withDefaults();
    }
}