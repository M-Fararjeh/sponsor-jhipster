package com.mitrol.sponsor.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BusinessContact entity.
 */
public class BusinessContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String personalPhone;

    private String workPhone;

    private String email;

    private Long sponsorId;

    private Long profileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long businessContactProfileId) {
        this.profileId = businessContactProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessContactDTO businessContactDTO = (BusinessContactDTO) o;
        if (businessContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessContactDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", personalPhone='" + getPersonalPhone() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", sponsor=" + getSponsorId() +
            ", profile=" + getProfileId() +
            "}";
    }
}
