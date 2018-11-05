package com.mitrol.sponsor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BusinessActivity entity.
 */
public class BusinessActivityDTO implements Serializable {

    private Long id;

    private String activityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessActivityDTO businessActivityDTO = (BusinessActivityDTO) o;
        if (businessActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessActivityDTO{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            "}";
    }
}
