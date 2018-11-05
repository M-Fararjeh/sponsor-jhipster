package com.mitrol.sponsor.repository.search;

import com.mitrol.sponsor.domain.BusinessContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BusinessContact entity.
 */
public interface BusinessContactSearchRepository extends ElasticsearchRepository<BusinessContact, Long> {
}
