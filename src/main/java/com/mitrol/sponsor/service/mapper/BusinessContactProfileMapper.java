package com.mitrol.sponsor.service.mapper;

import com.mitrol.sponsor.domain.*;
import com.mitrol.sponsor.service.dto.BusinessContactProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BusinessContactProfile and its DTO BusinessContactProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessContactProfileMapper extends EntityMapper<BusinessContactProfileDTO, BusinessContactProfile> {



    default BusinessContactProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessContactProfile businessContactProfile = new BusinessContactProfile();
        businessContactProfile.setId(id);
        return businessContactProfile;
    }
}
