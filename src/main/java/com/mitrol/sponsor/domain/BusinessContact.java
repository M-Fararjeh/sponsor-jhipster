package com.mitrol.sponsor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BusinessContact.
 */
@Entity
@Table(name = "business_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "businesscontact")
public class BusinessContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "personal_phone")
    private String personalPhone;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JsonIgnoreProperties("businessContacts")
    private Sponsor sponsor;

    @OneToOne    @JoinColumn(unique = true)
    private BusinessContactProfile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public BusinessContact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BusinessContact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public BusinessContact personalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
        return this;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public BusinessContact workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail() {
        return email;
    }

    public BusinessContact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public BusinessContact sponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public BusinessContactProfile getProfile() {
        return profile;
    }

    public BusinessContact profile(BusinessContactProfile businessContactProfile) {
        this.profile = businessContactProfile;
        return this;
    }

    public void setProfile(BusinessContactProfile businessContactProfile) {
        this.profile = businessContactProfile;
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
        BusinessContact businessContact = (BusinessContact) o;
        if (businessContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessContact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", personalPhone='" + getPersonalPhone() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
