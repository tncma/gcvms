package com.cma.gcvms.web.domain.support;

import static com.google.common.collect.Maps.newHashMap;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Identifiable;

@Named
@Singleton
@SuppressWarnings("rawtypes")
public class ControllerLocator {

    private Map<Class, GenericController> permissions = newHashMap();

    @Inject
    void buildCache(List<GenericController> registredPermissions) {
        for (GenericController permission : registredPermissions) {
            permissions.put(permission.getRepository().getType(), permission);
        }
    }

    @SuppressWarnings("unchecked")
    public <E extends Identifiable<? extends Serializable>> GenericController<E, ?> getController(E entity) {
        return (GenericController<E, ?>) permissions.get(getClassWithoutInitializingProxy(entity));
    }
}
