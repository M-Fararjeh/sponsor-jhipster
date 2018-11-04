package com.mitrol.sponsor.repository;

import com.mitrol.sponsor.domain.BusinessContactProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessContactProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessContactProfileRepository extends JpaRepository<BusinessContactProfile, Long> {

}
