package com.mitrol.sponsor.service.mapper;

import com.mitrol.sponsor.domain.*;
import com.mitrol.sponsor.service.dto.SponsorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sponsor and its DTO SponsorDTO.
 */
@Mapper(componentModel = "spring", uses = {BusinessActivityMapper.class})
public interface SponsorMapper extends EntityMapper<SponsorDTO, Sponsor> {


    @Mapping(target = "businessContacts", ignore = true)
    Sponsor toEntity(SponsorDTO sponsorDTO);

    default Sponsor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sponsor sponsor = new Sponsor();
        sponsor.setId(id);
        return sponsor;
    }
}
