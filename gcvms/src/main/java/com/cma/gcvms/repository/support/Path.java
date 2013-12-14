package com.cma.gcvms.repository.support;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.Attribute;

/**
 * Holder class for path used by the {@link OrderBy}, {@link PropertySelector}, {@link TermSelector} and {@link SearchParameters}.
 */
public class Path implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String path;
    private final Class<?> from;
    private transient List<Attribute<?, ?>> attributes;

    public Path(Attribute<?, ?>... attributes) {
        this(newArrayList(attributes));
    }

    public Path(List<Attribute<?, ?>> attributes) {
        JpaUtil.getInstance().verifyPath(checkNotNull(attributes));
        this.attributes = newArrayList(attributes);
        this.path = MetamodelUtil.getInstance().toPath(attributes);
        this.from = attributes.get(0).getDeclaringType().getJavaType();
    }

    public Path(String path, Class<?> from) {
        this.path = path;
        this.from = from;
        // to verify path
        getAttributes();
    }

    public List<Attribute<?, ?>> getAttributes() {
        if (attributes == null) {
            attributes = MetamodelUtil.getInstance().toAttributes(path, from);
        }
        return attributes;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Path other = (Path) obj;
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

}