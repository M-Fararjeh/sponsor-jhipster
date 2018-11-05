package com.mitrol.sponsor.repository.search;

import com.mitrol.sponsor.domain.BusinessContactProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BusinessContactProfile entity.
 */
public interface BusinessContactProfileSearchRepository extends ElasticsearchRepository<BusinessContactProfile, Long> {
}
