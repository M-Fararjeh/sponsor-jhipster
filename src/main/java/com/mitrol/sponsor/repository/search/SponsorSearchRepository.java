package com.mitrol.sponsor.repository.search;

import com.mitrol.sponsor.domain.Sponsor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sponsor entity.
 */
public interface SponsorSearchRepository extends ElasticsearchRepository<Sponsor, Long> {
}
