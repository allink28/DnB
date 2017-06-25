package allink28.dnb.repository.search;

import allink28.dnb.domain.Spell;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Spell entity.
 */
public interface SpellSearchRepository extends ElasticsearchRepository<Spell, Long> {
}
