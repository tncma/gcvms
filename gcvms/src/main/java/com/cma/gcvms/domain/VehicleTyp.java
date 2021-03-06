package com.cma.gcvms.domain;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "VEHICLE_TYP")
public class VehicleTyp implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(VehicleTyp.class);

    // Raw attributes
    private Integer id;
    private String typName;
    private Double capacity;
    private String capUnit;
    private Integer createdBy;
    private Date createdDate;

    // -- [id] ------------------------

    @Override
    @Column(name = "ID", precision = 32)
    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleTyp id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    @XmlTransient
    public boolean isIdSet() {
        return id != null;
    }

    // -- [typName] ------------------------

    @NotEmpty
    @Size(max = 20)
    @Column(name = "TYP_NAME", nullable = false, length = 20)
    public String getTypName() {
        return typName;
    }

    public void setTypName(String typName) {
        this.typName = typName;
    }

    public VehicleTyp typName(String typName) {
        setTypName(typName);
        return this;
    }

    // -- [capacity] ------------------------

    @Digits(integer = 7, fraction = 3)
    @NotNull
    @Column(name = "CAPACITY", nullable = false, precision = 10, scale = 3)
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public VehicleTyp capacity(Double capacity) {
        setCapacity(capacity);
        return this;
    }

    // -- [capUnit] ------------------------

    @Size(max = 10)
    @Column(name = "CAP_UNIT", length = 10)
    public String getCapUnit() {
        return capUnit;
    }

    public void setCapUnit(String capUnit) {
        this.capUnit = capUnit;
    }

    public VehicleTyp capUnit(String capUnit) {
        setCapUnit(capUnit);
        return this;
    }

    // -- [createdBy] ------------------------

    @Digits(integer = 32, fraction = 0)
    @Column(name = "CREATED_BY", precision = 32)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public VehicleTyp createdBy(Integer createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    // -- [createdDate] ------------------------

    @Column(name = "CREATED_DATE", length = 10)
    @Temporal(DATE)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public VehicleTyp createdDate(Date createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    /**
     * Apply the default values.
     */
    public VehicleTyp withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof VehicleTyp && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this VehicleTyp instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("id", getId()) //
                .add("typName", getTypName()) //
                .add("capacity", getCapacity()) //
                .add("capUnit", getCapUnit()) //
                .add("createdBy", getCreatedBy()) //
                .add("createdDate", getCreatedDate()) //
                .toString();
    }
}