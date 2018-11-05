package com.mitrol.sponsor.service.mapper;

import com.mitrol.sponsor.domain.*;
import com.mitrol.sponsor.service.dto.BusinessContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BusinessContact and its DTO BusinessContactDTO.
 */
@Mapper(componentModel = "spring", uses = {SponsorMapper.class, BusinessContactProfileMapper.class})
public interface BusinessContactMapper extends EntityMapper<BusinessContactDTO, BusinessContact> {

    @Mapping(source = "sponsor.id", target = "sponsorId")
    @Mapping(source = "profile.id", target = "profileId")
    BusinessContactDTO toDto(BusinessContact businessContact);

    @Mapping(source = "sponsorId", target = "sponsor")
    @Mapping(source = "profileId", target = "profile")
    BusinessContact toEntity(BusinessContactDTO businessContactDTO);

    default BusinessContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessContact businessContact = new BusinessContact();
        businessContact.setId(id);
        return businessContact;
    }
}
