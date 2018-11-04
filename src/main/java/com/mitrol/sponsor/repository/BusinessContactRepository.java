package com.mitrol.sponsor.repository;

import com.mitrol.sponsor.domain.BusinessContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessContactRepository extends JpaRepository<BusinessContact, Long> {

}
