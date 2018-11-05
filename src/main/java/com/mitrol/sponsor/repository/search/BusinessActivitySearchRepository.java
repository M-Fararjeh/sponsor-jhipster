package com.mitrol.sponsor.repository.search;

import com.mitrol.sponsor.domain.BusinessActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BusinessActivity entity.
 */
public interface BusinessActivitySearchRepository extends ElasticsearchRepository<BusinessActivity, Long> {
}
