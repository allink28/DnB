package allink28.dnb.web.rest;

import allink28.dnb.DnBApp;

import allink28.dnb.domain.Spell;
import allink28.dnb.repository.SpellRepository;
import allink28.dnb.repository.search.SpellSearchRepository;
import allink28.dnb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpellResource REST controller.
 *
 * @see SpellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DnBApp.class)
public class SpellResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RANGE = "AAAAAAAAAA";
    private static final String UPDATED_RANGE = "BBBBBBBBBB";

    private static final String DEFAULT_CAST_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CAST_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPONENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DAMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DAMAGE = "BBBBBBBBBB";

    @Autowired
    private SpellRepository spellRepository;

    @Autowired
    private SpellSearchRepository spellSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpellMockMvc;

    private Spell spell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpellResource spellResource = new SpellResource(spellRepository, spellSearchRepository);
        this.restSpellMockMvc = MockMvcBuilders.standaloneSetup(spellResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spell createEntity(EntityManager em) {
        Spell spell = new Spell()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL)
            .type(DEFAULT_TYPE)
            .range(DEFAULT_RANGE)
            .castTime(DEFAULT_CAST_TIME)
            .components(DEFAULT_COMPONENTS)
            .duration(DEFAULT_DURATION)
            .description(DEFAULT_DESCRIPTION)
            .damage(DEFAULT_DAMAGE);
        return spell;
    }

    @Before
    public void initTest() {
        spellSearchRepository.deleteAll();
        spell = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpell() throws Exception {
        int databaseSizeBeforeCreate = spellRepository.findAll().size();

        // Create the Spell
        restSpellMockMvc.perform(post("/api/spells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spell)))
            .andExpect(status().isCreated());

        // Validate the Spell in the database
        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeCreate + 1);
        Spell testSpell = spellList.get(spellList.size() - 1);
        assertThat(testSpell.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpell.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testSpell.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSpell.getRange()).isEqualTo(DEFAULT_RANGE);
        assertThat(testSpell.getCastTime()).isEqualTo(DEFAULT_CAST_TIME);
        assertThat(testSpell.getComponents()).isEqualTo(DEFAULT_COMPONENTS);
        assertThat(testSpell.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSpell.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSpell.getDamage()).isEqualTo(DEFAULT_DAMAGE);

        // Validate the Spell in Elasticsearch
        Spell spellEs = spellSearchRepository.findOne(testSpell.getId());
        assertThat(spellEs).isEqualToComparingFieldByField(testSpell);
    }

    @Test
    @Transactional
    public void createSpellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spellRepository.findAll().size();

        // Create the Spell with an existing ID
        spell.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpellMockMvc.perform(post("/api/spells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spell)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = spellRepository.findAll().size();
        // set the field null
        spell.setName(null);

        // Create the Spell, which fails.

        restSpellMockMvc.perform(post("/api/spells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spell)))
            .andExpect(status().isBadRequest());

        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpells() throws Exception {
        // Initialize the database
        spellRepository.saveAndFlush(spell);

        // Get all the spellList
        restSpellMockMvc.perform(get("/api/spells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spell.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE.toString())))
            .andExpect(jsonPath("$.[*].castTime").value(hasItem(DEFAULT_CAST_TIME.toString())))
            .andExpect(jsonPath("$.[*].components").value(hasItem(DEFAULT_COMPONENTS.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].damage").value(hasItem(DEFAULT_DAMAGE.toString())));
    }

    @Test
    @Transactional
    public void getSpell() throws Exception {
        // Initialize the database
        spellRepository.saveAndFlush(spell);

        // Get the spell
        restSpellMockMvc.perform(get("/api/spells/{id}", spell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spell.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.range").value(DEFAULT_RANGE.toString()))
            .andExpect(jsonPath("$.castTime").value(DEFAULT_CAST_TIME.toString()))
            .andExpect(jsonPath("$.components").value(DEFAULT_COMPONENTS.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.damage").value(DEFAULT_DAMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpell() throws Exception {
        // Get the spell
        restSpellMockMvc.perform(get("/api/spells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpell() throws Exception {
        // Initialize the database
        spellRepository.saveAndFlush(spell);
        spellSearchRepository.save(spell);
        int databaseSizeBeforeUpdate = spellRepository.findAll().size();

        // Update the spell
        Spell updatedSpell = spellRepository.findOne(spell.getId());
        updatedSpell
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .type(UPDATED_TYPE)
            .range(UPDATED_RANGE)
            .castTime(UPDATED_CAST_TIME)
            .components(UPDATED_COMPONENTS)
            .duration(UPDATED_DURATION)
            .description(UPDATED_DESCRIPTION)
            .damage(UPDATED_DAMAGE);

        restSpellMockMvc.perform(put("/api/spells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpell)))
            .andExpect(status().isOk());

        // Validate the Spell in the database
        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeUpdate);
        Spell testSpell = spellList.get(spellList.size() - 1);
        assertThat(testSpell.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpell.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testSpell.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSpell.getRange()).isEqualTo(UPDATED_RANGE);
        assertThat(testSpell.getCastTime()).isEqualTo(UPDATED_CAST_TIME);
        assertThat(testSpell.getComponents()).isEqualTo(UPDATED_COMPONENTS);
        assertThat(testSpell.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSpell.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpell.getDamage()).isEqualTo(UPDATED_DAMAGE);

        // Validate the Spell in Elasticsearch
        Spell spellEs = spellSearchRepository.findOne(testSpell.getId());
        assertThat(spellEs).isEqualToComparingFieldByField(testSpell);
    }

    @Test
    @Transactional
    public void updateNonExistingSpell() throws Exception {
        int databaseSizeBeforeUpdate = spellRepository.findAll().size();

        // Create the Spell

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpellMockMvc.perform(put("/api/spells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spell)))
            .andExpect(status().isCreated());

        // Validate the Spell in the database
        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpell() throws Exception {
        // Initialize the database
        spellRepository.saveAndFlush(spell);
        spellSearchRepository.save(spell);
        int databaseSizeBeforeDelete = spellRepository.findAll().size();

        // Get the spell
        restSpellMockMvc.perform(delete("/api/spells/{id}", spell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean spellExistsInEs = spellSearchRepository.exists(spell.getId());
        assertThat(spellExistsInEs).isFalse();

        // Validate the database is empty
        List<Spell> spellList = spellRepository.findAll();
        assertThat(spellList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSpell() throws Exception {
        // Initialize the database
        spellRepository.saveAndFlush(spell);
        spellSearchRepository.save(spell);

        // Search the spell
        restSpellMockMvc.perform(get("/api/_search/spells?query=id:" + spell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spell.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE.toString())))
            .andExpect(jsonPath("$.[*].castTime").value(hasItem(DEFAULT_CAST_TIME.toString())))
            .andExpect(jsonPath("$.[*].components").value(hasItem(DEFAULT_COMPONENTS.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].damage").value(hasItem(DEFAULT_DAMAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spell.class);
        Spell spell1 = new Spell();
        spell1.setId(1L);
        Spell spell2 = new Spell();
        spell2.setId(spell1.getId());
        assertThat(spell1).isEqualTo(spell2);
        spell2.setId(2L);
        assertThat(spell1).isNotEqualTo(spell2);
        spell1.setId(null);
        assertThat(spell1).isNotEqualTo(spell2);
    }
}
