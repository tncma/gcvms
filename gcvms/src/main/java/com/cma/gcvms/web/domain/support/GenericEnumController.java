package com.cma.gcvms.web.domain.support;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.containsIgnoreCase;

import java.util.List;

import com.cma.gcvms.domain.LabelizedEnum;

public abstract class GenericEnumController<T extends Enum<? extends Enum<?>> & LabelizedEnum> {

    private final T[] values;

    public GenericEnumController(T[] values) {
        this.values = values;
    }

    public List<T> complete(String text) {
        List<T> ret = newArrayList();
        for (T value : values) {
            if (containsIgnoreCase(value.name(), text) || containsIgnoreCase(value.getLabel(), text)) {
                ret.add(value);
            }
        }
        return ret;
    }
}