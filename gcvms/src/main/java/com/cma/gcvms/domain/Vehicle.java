package com.cma.gcvms.domain;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "VEHICLE")
public class Vehicle implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Vehicle.class);

    // Raw attributes
    private Integer id;
    private String regNo;
    private Integer mileage;
    private Integer createdBy;
    private Date createdDate;

    // Many to one
    private VehicleTyp typ;

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

    public Vehicle id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    @XmlTransient
    public boolean isIdSet() {
        return id != null;
    }

    // -- [regNo] ------------------------

    @Size(max = 20)
    @Column(name = "REG_NO", unique = true, length = 20)
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Vehicle regNo(String regNo) {
        setRegNo(regNo);
        return this;
    }

    // -- [mileage] ------------------------

    @Digits(integer = 32, fraction = 0)
    @Column(name = "MILEAGE", precision = 32)
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Vehicle mileage(Integer mileage) {
        setMileage(mileage);
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

    public Vehicle createdBy(Integer createdBy) {
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

    public Vehicle createdDate(Date createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: Vehicle.typ ==> VehicleTyp.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "TYP_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public VehicleTyp getTyp() {
        return typ;
    }

    /**
     * Set the {@link #typ} without adding this Vehicle instance on the passed {@link #typ}
     */
    public void setTyp(VehicleTyp typ) {
        this.typ = typ;
    }

    public Vehicle typ(VehicleTyp typ) {
        setTyp(typ);
        return this;
    }

    /**
     * Apply the default values.
     */
    public Vehicle withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Vehicle && hashCode() == other.hashCode());
    }

    private volatile int previousHashCode = 0;

    @Override
    public int hashCode() {
        int hashCode = Objects.hashCode(getRegNo());

        if (previousHashCode != 0 && previousHashCode != hashCode) {
            log.warn("DEVELOPER: hashCode has changed!." //
                    + "If you encounter this message you should take the time to carefuly review equals/hashCode for: " //
                    + getClass().getCanonicalName());
        }

        previousHashCode = hashCode;
        return hashCode;
    }

    /**
     * Construct a readable string representation for this Vehicle instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("id", getId()) //
                .add("regNo", getRegNo()) //
                .add("mileage", getMileage()) //
                .add("createdBy", getCreatedBy()) //
                .add("createdDate", getCreatedDate()) //
                .toString();
    }
}