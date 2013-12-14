package com.cma.gcvms.repository.support;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.Attribute;

/**
 * Range support for {@link Comparable} types.
 */
@SuppressWarnings("rawtypes")
public class Range<E, D extends Comparable> implements Serializable {

    /**
     * {@link Range} builder
     */
    public static <E, D extends Comparable> Range<E, D> newRange(Attribute<?, ?>... fields) {
        return new Range<E, D>(fields);
    }

    private static final long serialVersionUID = 1L;

    private final Path path;
    private D from;
    private D to;
    private Boolean includeNull;

    /**
     * Constructs a new {@link Range} with no boundaries and no restrictions on field's nullability.
     * @param attributes the path to the attribute of an existing entity.
     */
    public Range(Attribute<?, ?>... attributes) {
        path = new Path(attributes);
    }

    /**
     * Constructs a new Range.
     *
     * @param from the lower boundary of this range. Null means no lower boundary.
     * @param to the upper boundary of this range. Null means no upper boundary.
     * @param attributes the path to the attribute of an existing entity.
     */
    public Range(D from, D to, Attribute<?, ?>... attributes) {
        this(attributes);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a new Range.
     *
     * @param from the lower boundary of this range. Null means no lower boundary.
     * @param to the upper boundary of this range. Null means no upper boundary.
     * @param includeNull tells whether null should be filtered out or not.
     * @param attributes the path to the attribute of an existing entity.
     */
    public Range(D from, D to, Boolean includeNull, Attribute<?, ?>... attributes) {
        this(from, to, attributes);
        this.includeNull = includeNull;
    }

    /**
     * Constructs a new Range by copy.
     */
    public Range(Range<E, D> other) {
        this.path = other.path;
        this.from = other.from;
        this.to = other.to;
        this.includeNull = other.includeNull;
    }

    /**
     * @return the entity's attribute this {@link Range} refers to.
     */
    public List<Attribute<?, ?>> getAttributes() {
        return path.getAttributes();
    }

    /**
     * @return the lower range boundary or null for unbound lower range.
     */
    public D getFrom() {
        return from;
    }

    /**
     * Sets the lower range boundary. Accepts null for unbound lower range.
     */
    public void setFrom(D from) {
        this.from = from;
    }

    public Range<E, D> from(D from) {
        setFrom(from);
        return this;
    }

    public boolean isFromSet() {
        return getFrom() != null;
    }

    /**
     * @return the upper range boundary or null for unbound upper range.
     */
    public D getTo() {
        return to;
    }

    public Range<E, D> to(D to) {
        setTo(to);
        return this;
    }

    /**
     * Sets the upper range boundary. Accepts null for unbound upper range.
     */
    public void setTo(D to) {
        this.to = to;
    }

    public boolean isToSet() {
        return getTo() != null;
    }

    public void setIncludeNull(Boolean includeNull) {
        this.includeNull = includeNull;
    }

    public Range<E, D> includeNull(Boolean includeNull) {
        setIncludeNull(includeNull);
        return this;
    }

    public Boolean getIncludeNull() {
        return includeNull;
    }

    public boolean isIncludeNullSet() {
        return includeNull != null;
    }

    public boolean isBetween() {
        return isFromSet() && isToSet();
    }

    public boolean isSet() {
        return isFromSet() || isToSet() || isIncludeNullSet();
    }

    @SuppressWarnings("unchecked")
    public boolean isValid() {
        if (isBetween()) {
            return getFrom().compareTo(getTo()) <= 0;
        }
        return true;
    }

    public void resetRange() {
        from = null;
        to = null;
        includeNull = null;
    }
}