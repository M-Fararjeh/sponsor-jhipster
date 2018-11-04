package com.mitrol.sponsor.repository;

import com.mitrol.sponsor.domain.BusinessActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessActivityRepository extends JpaRepository<BusinessActivity, Long> {

}
