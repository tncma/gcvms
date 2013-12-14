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
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "TRIP_SCHEDULE")
public class TripSchedule implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TripSchedule.class);

    // Raw attributes
    private Integer id;
    private Integer tripCnt;
    private Date startDt;
    private Date endDt;
    private Integer createdBy;
    private Date createdDate;

    // Many to one
    private Route route;
    private Vehicle vehicle;

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

    public TripSchedule id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    @XmlTransient
    public boolean isIdSet() {
        return id != null;
    }

    // -- [tripCnt] ------------------------

    @Digits(integer = 32, fraction = 0)
    @NotNull
    @Column(name = "TRIP_CNT", nullable = false, precision = 32)
    public Integer getTripCnt() {
        return tripCnt;
    }

    public void setTripCnt(Integer tripCnt) {
        this.tripCnt = tripCnt;
    }

    public TripSchedule tripCnt(Integer tripCnt) {
        setTripCnt(tripCnt);
        return this;
    }

    // -- [startDt] ------------------------

    @Column(name = "START_DT", length = 10)
    @Temporal(DATE)
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public TripSchedule startDt(Date startDt) {
        setStartDt(startDt);
        return this;
    }

    // -- [endDt] ------------------------

    @Column(name = "END_DT", length = 10)
    @Temporal(DATE)
    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public TripSchedule endDt(Date endDt) {
        setEndDt(endDt);
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

    public TripSchedule createdBy(Integer createdBy) {
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

    public TripSchedule createdDate(Date createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: TripSchedule.route ==> Route.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "ROUTE_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Route getRoute() {
        return route;
    }

    /**
     * Set the {@link #route} without adding this TripSchedule instance on the passed {@link #route}
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    public TripSchedule route(Route route) {
        setRoute(route);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: TripSchedule.vehicle ==> Vehicle.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "VEHICLE_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Set the {@link #vehicle} without adding this TripSchedule instance on the passed {@link #vehicle}
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TripSchedule vehicle(Vehicle vehicle) {
        setVehicle(vehicle);
        return this;
    }

    /**
     * Apply the default values.
     */
    public TripSchedule withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof TripSchedule && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this TripSchedule instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("id", getId()) //
                .add("tripCnt", getTripCnt()) //
                .add("startDt", getStartDt()) //
                .add("endDt", getEndDt()) //
                .add("createdBy", getCreatedBy()) //
                .add("createdDate", getCreatedDate()) //
                .toString();
    }
}