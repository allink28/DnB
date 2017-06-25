package allink28.dnb.service;

import allink28.dnb.domain.Character;
import allink28.dnb.repository.CharacterRepository;
import allink28.dnb.repository.search.CharacterSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Character.
 */
@Service
@Transactional
public class CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterService.class);

    private final CharacterRepository characterRepository;

    private final CharacterSearchRepository characterSearchRepository;

    public CharacterService(CharacterRepository characterRepository, CharacterSearchRepository characterSearchRepository) {
        this.characterRepository = characterRepository;
        this.characterSearchRepository = characterSearchRepository;
    }

    /**
     * Save a character.
     *
     * @param character the entity to save
     * @return the persisted entity
     */
    public Character save(Character character) {
        log.debug("Request to save Character : {}", character);
        Character result = characterRepository.save(character);
        characterSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the characters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Character> findAll(Pageable pageable) {
        log.debug("Request to get all Characters");
        return characterRepository.findAll(pageable);
    }

    /**
     *  Get one character by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Character findOne(Long id) {
        log.debug("Request to get Character : {}", id);
        return characterRepository.findOne(id);
    }

    /**
     *  Delete the  character by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Character : {}", id);
        characterRepository.delete(id);
        characterSearchRepository.delete(id);
    }

    /**
     * Search for the character corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Character> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Characters for query {}", query);
        Page<Character> result = characterSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
