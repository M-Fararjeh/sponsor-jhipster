package com.mitrol.sponsor.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BusinessActivitySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BusinessActivitySearchRepositoryMockConfiguration {

    @MockBean
    private BusinessActivitySearchRepository mockBusinessActivitySearchRepository;

}
