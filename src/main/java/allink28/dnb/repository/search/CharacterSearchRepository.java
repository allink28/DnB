package allink28.dnb.repository.search;

import allink28.dnb.domain.Character;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Character entity.
 */
public interface CharacterSearchRepository extends ElasticsearchRepository<Character, Long> {
}
