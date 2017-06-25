package allink28.dnb.web.rest;

import com.codahale.metrics.annotation.Timed;
import allink28.dnb.domain.Spell;

import allink28.dnb.repository.SpellRepository;
import allink28.dnb.repository.search.SpellSearchRepository;
import allink28.dnb.web.rest.util.HeaderUtil;
import allink28.dnb.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Spell.
 */
@RestController
@RequestMapping("/api")
public class SpellResource {

    private final Logger log = LoggerFactory.getLogger(SpellResource.class);

    private static final String ENTITY_NAME = "spell";

    private final SpellRepository spellRepository;

    private final SpellSearchRepository spellSearchRepository;

    public SpellResource(SpellRepository spellRepository, SpellSearchRepository spellSearchRepository) {
        this.spellRepository = spellRepository;
        this.spellSearchRepository = spellSearchRepository;
    }

    /**
     * POST  /spells : Create a new spell.
     *
     * @param spell the spell to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spell, or with status 400 (Bad Request) if the spell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/spells")
    @Timed
    public ResponseEntity<Spell> createSpell(@Valid @RequestBody Spell spell) throws URISyntaxException {
        log.debug("REST request to save Spell : {}", spell);
        if (spell.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new spell cannot already have an ID")).body(null);
        }
        Spell result = spellRepository.save(spell);
        spellSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/spells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spells : Updates an existing spell.
     *
     * @param spell the spell to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spell,
     * or with status 400 (Bad Request) if the spell is not valid,
     * or with status 500 (Internal Server Error) if the spell couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/spells")
    @Timed
    public ResponseEntity<Spell> updateSpell(@Valid @RequestBody Spell spell) throws URISyntaxException {
        log.debug("REST request to update Spell : {}", spell);
        if (spell.getId() == null) {
            return createSpell(spell);
        }
        Spell result = spellRepository.save(spell);
        spellSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, spell.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spells : get all the spells.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of spells in body
     */
    @GetMapping("/spells")
    @Timed
    public ResponseEntity<List<Spell>> getAllSpells(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Spells");
        Page<Spell> page = spellRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/spells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /spells/:id : get the "id" spell.
     *
     * @param id the id of the spell to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spell, or with status 404 (Not Found)
     */
    @GetMapping("/spells/{id}")
    @Timed
    public ResponseEntity<Spell> getSpell(@PathVariable Long id) {
        log.debug("REST request to get Spell : {}", id);
        Spell spell = spellRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(spell));
    }

    /**
     * DELETE  /spells/:id : delete the "id" spell.
     *
     * @param id the id of the spell to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/spells/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpell(@PathVariable Long id) {
        log.debug("REST request to delete Spell : {}", id);
        spellRepository.delete(id);
        spellSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/spells?query=:query : search for the spell corresponding
     * to the query.
     *
     * @param query the query of the spell search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/spells")
    @Timed
    public ResponseEntity<List<Spell>> searchSpells(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Spells for query {}", query);
        Page<Spell> page = spellSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/spells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
