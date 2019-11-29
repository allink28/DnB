package allink28.dnb.repository.search;

import allink28.dnb.domain.Campaign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Campaign entity.
 */
public interface CampaignSearchRepository extends ElasticsearchRepository<Campaign, Long> {
}
