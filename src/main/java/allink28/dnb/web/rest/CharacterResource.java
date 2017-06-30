package allink28.dnb.web.rest;

import allink28.dnb.domain.Character;
import allink28.dnb.service.CharacterGeneratorService;
import allink28.dnb.service.CharacterService;
import allink28.dnb.web.rest.util.HeaderUtil;
import allink28.dnb.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing Character.
 */
@RestController
@RequestMapping("/api")
public class CharacterResource {

    private final Logger log = LoggerFactory.getLogger(CharacterResource.class);

    private static final String ENTITY_NAME = "character";

    private final CharacterService characterService;

    public CharacterResource(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * POST  /characters : Create a new character.
     *
     * @param character the character to create
     * @return the ResponseEntity with status 201 (Created) and with body the new character, or with status 400 (Bad Request) if the character has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/characters")
    @Timed
    public ResponseEntity<Character> createCharacter(@Valid @RequestBody Character character) throws URISyntaxException {
        log.debug("REST request to save Character : {}", character);
        if (character.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new character cannot already have an ID")).body(null);
        }
        Character result = characterService.save(character);
        return ResponseEntity.created(new URI("/api/characters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/characters/generate")
    @Timed
    public ResponseEntity<Character> generateCharacter(@RequestBody Character character) throws URISyntaxException {
        log.debug("REST request to generate random character: " + character);
        if (character == null)
            character = new Character();

        if (character.getName() == null || character.getName().isEmpty())
            character.setName(CharacterGeneratorService.generateName());

        if (character.getLevel() == null)
            character.setLevel(0);
        if (character.getSex() == null)
            character.setSex(CharacterGeneratorService.randomSex());
        if (character.getRace() == null)
            character.setRace(CharacterGeneratorService.randomRace());
        if (character.getClasses() == null || character.getClasses().isEmpty())
            character.setClasses(CharacterGeneratorService.randomClass());
        if (character.getAlignment() == null)
            character.setAlignment(CharacterGeneratorService.randomAlignment(character));
        if ((character.getHeight() == null || character.getHeight().isEmpty())
            && (character.getWeight() == null || character.getWeight() == 0))
            CharacterGeneratorService.setRandomHeightWeight(character);

        character = characterService.save(character);

        return ResponseEntity.created(new URI("/api/characters/" + character.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, character.getId().toString()))
            .body(character);
    }

    /**
     * PUT  /characters : Updates an existing character.
     *
     * @param character the character to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated character,
     * or with status 400 (Bad Request) if the character is not valid,
     * or with status 500 (Internal Server Error) if the character couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/characters")
    @Timed
    public ResponseEntity<Character> updateCharacter(@Valid @RequestBody Character character) throws URISyntaxException {
        log.debug("REST request to update Character : {}", character);
        if (character.getId() == null) {
            return createCharacter(character);
        }
        Character result = characterService.save(character);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, character.getId().toString()))
            .body(result);
    }

    /**
     * GET  /characters : get all the characters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of characters in body
     */
    @GetMapping("/characters")
    @Timed
    public ResponseEntity<List<Character>> getAllCharacters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Characters");
        Page<Character> page = characterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/characters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /characters/:id : get the "id" character.
     *
     * @param id the id of the character to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the character, or with status 404 (Not Found)
     */
    @GetMapping("/characters/{id}")
    @Timed
    public ResponseEntity<Character> getCharacter(@PathVariable Long id) {
        log.debug("REST request to get Character : {}", id);
        Character character = characterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(character));
    }

    /**
     * DELETE  /characters/:id : delete the "id" character.
     *
     * @param id the id of the character to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/characters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        log.debug("REST request to delete Character : {}", id);
        characterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/characters?query=:query : search for the character corresponding
     * to the query.
     *
     * @param query the query of the character search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/characters")
    @Timed
    public ResponseEntity<List<Character>> searchCharacters(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Characters for query {}", query);
        Page<Character> page = characterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/characters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
