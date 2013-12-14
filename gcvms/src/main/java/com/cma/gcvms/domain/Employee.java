package com.cma.gcvms.domain;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Employee.class);

    // Raw attributes
    private Integer id;
    private String fname;
    private String lname;
    private String pwd;
    private String designation;

    // Many to one
    private Municipality mun;
    private Employee supervisor;
    private Dept dept;

    // -- [id] ------------------------

    @Override
    @Column(name = "EMPID", precision = 32)
    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Employee id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    @XmlTransient
    public boolean isIdSet() {
        return id != null;
    }

    // -- [fname] ------------------------

    @NotEmpty
    @Size(max = 20)
    @Column(name = "FNAME", nullable = false, length = 20)
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Employee fname(String fname) {
        setFname(fname);
        return this;
    }

    // -- [lname] ------------------------

    @NotEmpty
    @Size(max = 20)
    @Column(name = "LNAME", nullable = false, length = 20)
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Employee lname(String lname) {
        setLname(lname);
        return this;
    }

    // -- [pwd] ------------------------

    @NotEmpty
    @Size(max = 20)
    @Column(name = "PWD", nullable = false, length = 20)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Employee pwd(String pwd) {
        setPwd(pwd);
        return this;
    }

    // -- [designation] ------------------------

    @NotEmpty
    @Size(max = 20)
    @Column(name = "DESIGNATION", nullable = false, length = 20)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Employee designation(String designation) {
        setDesignation(designation);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: Employee.mun ==> Municipality.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "MUN_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Municipality getMun() {
        return mun;
    }

    /**
     * Set the {@link #mun} without adding this Employee instance on the passed {@link #mun}
     */
    public void setMun(Municipality mun) {
        this.mun = mun;
    }

    public Employee mun(Municipality mun) {
        setMun(mun);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: Employee.supervisor ==> Employee.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @JoinColumn(name = "SUPERVISOR_ID")
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Employee getSupervisor() {
        return supervisor;
    }

    /**
     * Set the {@link #supervisor} without adding this Employee instance on the passed {@link #supervisor}
     */
    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Employee supervisor(Employee supervisor) {
        setSupervisor(supervisor);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: Employee.dept ==> Dept.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "DEPT_ID", nullable = false)
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = LAZY)
    public Dept getDept() {
        return dept;
    }

    /**
     * Set the {@link #dept} without adding this Employee instance on the passed {@link #dept}
     */
    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Employee dept(Dept dept) {
        setDept(dept);
        return this;
    }

    /**
     * Apply the default values.
     */
    public Employee withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Employee && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this Employee instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("id", getId()) //
                .add("fname", getFname()) //
                .add("lname", getLname()) //
                .add("pwd", "XXXX") //
                .add("designation", getDesignation()) //
                .toString();
    }
}