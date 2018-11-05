package com.mitrol.sponsor.service.mapper;

import com.mitrol.sponsor.domain.*;
import com.mitrol.sponsor.service.dto.BusinessActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BusinessActivity and its DTO BusinessActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessActivityMapper extends EntityMapper<BusinessActivityDTO, BusinessActivity> {


    @Mapping(target = "sponsors", ignore = true)
    BusinessActivity toEntity(BusinessActivityDTO businessActivityDTO);

    default BusinessActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessActivity businessActivity = new BusinessActivity();
        businessActivity.setId(id);
        return businessActivity;
    }
}
