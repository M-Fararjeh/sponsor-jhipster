package com.mitrol.sponsor.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BusinessContactProfileSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BusinessContactProfileSearchRepositoryMockConfiguration {

    @MockBean
    private BusinessContactProfileSearchRepository mockBusinessContactProfileSearchRepository;

}
