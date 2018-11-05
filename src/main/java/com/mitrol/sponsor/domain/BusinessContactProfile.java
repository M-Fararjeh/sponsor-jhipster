package com.mitrol.sponsor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BusinessContactProfile.
 */
@Entity
@Table(name = "business_contact_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "businesscontactprofile")
public class BusinessContactProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attending")
    private String attending;

    @Column(name = "retention")
    private String retention;

    @Column(name = "customer_service")
    private String customerService;

    @Column(name = "customer_service_special")
    private String customerServiceSpecial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttending() {
        return attending;
    }

    public BusinessContactProfile attending(String attending) {
        this.attending = attending;
        return this;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getRetention() {
        return retention;
    }

    public BusinessContactProfile retention(String retention) {
        this.retention = retention;
        return this;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public String getCustomerService() {
        return customerService;
    }

    public BusinessContactProfile customerService(String customerService) {
        this.customerService = customerService;
        return this;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getCustomerServiceSpecial() {
        return customerServiceSpecial;
    }

    public BusinessContactProfile customerServiceSpecial(String customerServiceSpecial) {
        this.customerServiceSpecial = customerServiceSpecial;
        return this;
    }

    public void setCustomerServiceSpecial(String customerServiceSpecial) {
        this.customerServiceSpecial = customerServiceSpecial;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessContactProfile businessContactProfile = (BusinessContactProfile) o;
        if (businessContactProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessContactProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessContactProfile{" +
            "id=" + getId() +
            ", attending='" + getAttending() + "'" +
            ", retention='" + getRetention() + "'" +
            ", customerService='" + getCustomerService() + "'" +
            ", customerServiceSpecial='" + getCustomerServiceSpecial() + "'" +
            "}";
    }
}
