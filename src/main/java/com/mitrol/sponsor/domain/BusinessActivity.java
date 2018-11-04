package com.mitrol.sponsor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BusinessActivity.
 */
@Entity
@Table(name = "business_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @ManyToMany(mappedBy = "businessContacts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Sponsor> sponsors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public BusinessActivity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public BusinessActivity sponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
        return this;
    }

    public BusinessActivity addSponsor(Sponsor sponsor) {
        this.sponsors.add(sponsor);
        sponsor.getBusinessContacts().add(this);
        return this;
    }

    public BusinessActivity removeSponsor(Sponsor sponsor) {
        this.sponsors.remove(sponsor);
        sponsor.getBusinessContacts().remove(this);
        return this;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
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
        BusinessActivity businessActivity = (BusinessActivity) o;
        if (businessActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessActivity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            "}";
    }
}
