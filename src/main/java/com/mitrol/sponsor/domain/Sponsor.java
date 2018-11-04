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
 * A Sponsor.
 */
@Entity
@Table(name = "sponsor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sponsor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "home_page")
    private String homePage;

    @OneToMany(mappedBy = "sponsor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessContact> businessContacts = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sponsor_business_activity",
               joinColumns = @JoinColumn(name = "sponsors_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "business_activities_id", referencedColumnName = "id"))
    private Set<BusinessActivity> businessActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sponsor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Sponsor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Sponsor city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public Sponsor region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Sponsor postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Sponsor country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public Sponsor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Sponsor fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomePage() {
        return homePage;
    }

    public Sponsor homePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Set<BusinessContact> getBusinessContacts() {
        return businessContacts;
    }

    public Sponsor businessContacts(Set<BusinessContact> businessContacts) {
        this.businessContacts = businessContacts;
        return this;
    }

    public Sponsor addBusinessContact(BusinessContact businessContact) {
        this.businessContacts.add(businessContact);
        businessContact.setSponsor(this);
        return this;
    }

    public Sponsor removeBusinessContact(BusinessContact businessContact) {
        this.businessContacts.remove(businessContact);
        businessContact.setSponsor(null);
        return this;
    }

    public void setBusinessContacts(Set<BusinessContact> businessContacts) {
        this.businessContacts = businessContacts;
    }

    public Set<BusinessActivity> getBusinessActivities() {
        return businessActivities;
    }

    public Sponsor businessActivities(Set<BusinessActivity> businessActivities) {
        this.businessActivities = businessActivities;
        return this;
    }

    public Sponsor addBusinessActivity(BusinessActivity businessActivity) {
        this.businessActivities.add(businessActivity);
        businessActivity.getSponsors().add(this);
        return this;
    }

    public Sponsor removeBusinessActivity(BusinessActivity businessActivity) {
        this.businessActivities.remove(businessActivity);
        businessActivity.getSponsors().remove(this);
        return this;
    }

    public void setBusinessActivities(Set<BusinessActivity> businessActivities) {
        this.businessActivities = businessActivities;
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
        Sponsor sponsor = (Sponsor) o;
        if (sponsor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sponsor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sponsor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", homePage='" + getHomePage() + "'" +
            "}";
    }
}
