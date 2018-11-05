package com.mitrol.sponsor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BusinessContactProfile entity.
 */
public class BusinessContactProfileDTO implements Serializable {

    private Long id;

    private String attending;

    private String retention;

    private String customerService;

    private String customerServiceSpecial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getRetention() {
        return retention;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getCustomerServiceSpecial() {
        return customerServiceSpecial;
    }

    public void setCustomerServiceSpecial(String customerServiceSpecial) {
        this.customerServiceSpecial = customerServiceSpecial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessContactProfileDTO businessContactProfileDTO = (BusinessContactProfileDTO) o;
        if (businessContactProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessContactProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessContactProfileDTO{" +
            "id=" + getId() +
            ", attending='" + getAttending() + "'" +
            ", retention='" + getRetention() + "'" +
            ", customerService='" + getCustomerService() + "'" +
            ", customerServiceSpecial='" + getCustomerServiceSpecial() + "'" +
            "}";
    }
}
