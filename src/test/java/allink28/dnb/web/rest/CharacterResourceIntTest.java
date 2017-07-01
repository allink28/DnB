package allink28.dnb.web.rest;

import allink28.dnb.DnBApp;
import allink28.dnb.domain.Character;
import allink28.dnb.domain.enumeration.Alignment;
import allink28.dnb.domain.enumeration.Race;
import allink28.dnb.domain.enumeration.Sex;
import allink28.dnb.repository.CharacterRepository;
import allink28.dnb.repository.search.CharacterSearchRepository;
import allink28.dnb.service.CharacterService;
import allink28.dnb.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Test class for the CharacterResource REST controller.
 *
 * @see CharacterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DnBApp.class)
public class CharacterResourceIntTest {

    private final Logger log = LoggerFactory.getLogger(CharacterResourceIntTest.class);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Race DEFAULT_RACE = Race.Dragonborn;
    private static final Race UPDATED_RACE = Race.Dwarf;

    private static final String DEFAULT_CLASSES = "AAAAAAAAAA";
    private static final String UPDATED_CLASSES = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.Male;
    private static final Sex UPDATED_SEX = Sex.Female;

    private static final Alignment DEFAULT_ALIGNMENT = Alignment.Lawful_Good;
    private static final Alignment UPDATED_ALIGNMENT = Alignment.Neutral_Good;

    private static final String DEFAULT_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_HEIGHT = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_MAX_HP = 1;
    private static final Integer UPDATED_MAX_HP = 2;

    private static final Integer DEFAULT_CURRENT_HP = 1;
    private static final Integer UPDATED_CURRENT_HP = 2;

    private static final Integer DEFAULT_STRENGTH = 1;
    private static final Integer UPDATED_STRENGTH = 2;

    private static final Integer DEFAULT_DEXTERITY = 1;
    private static final Integer UPDATED_DEXTERITY = 2;

    private static final Integer DEFAULT_CONSTITUTION = 1;
    private static final Integer UPDATED_CONSTITUTION = 2;

    private static final Integer DEFAULT_WISDOM = 1;
    private static final Integer UPDATED_WISDOM = 2;

    private static final Integer DEFAULT_INTELLIGENCE = 1;
    private static final Integer UPDATED_INTELLIGENCE = 2;

    private static final Integer DEFAULT_CHARISMA = 1;
    private static final Integer UPDATED_CHARISMA = 2;

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    private static final Long DEFAULT_EXP = 1L;
    private static final Long UPDATED_EXP = 2L;

    private static final Integer DEFAULT_LEVEL = 0;
    private static final Integer UPDATED_LEVEL = 1;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterSearchRepository characterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCharacterMockMvc;

    private Character character;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharacterResource characterResource = new CharacterResource(characterService);
        this.restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
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
    public static Character createEntity(EntityManager em) {
        Character character = new Character()
            .name(DEFAULT_NAME)
            .race(DEFAULT_RACE)
            .classes(DEFAULT_CLASSES)
            .sex(DEFAULT_SEX)
            .alignment(DEFAULT_ALIGNMENT)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .maxHP(DEFAULT_MAX_HP)
            .currentHP(DEFAULT_CURRENT_HP)
            .strength(DEFAULT_STRENGTH)
            .dexterity(DEFAULT_DEXTERITY)
            .constitution(DEFAULT_CONSTITUTION)
            .wisdom(DEFAULT_WISDOM)
            .intelligence(DEFAULT_INTELLIGENCE)
            .charisma(DEFAULT_CHARISMA)
            .background(DEFAULT_BACKGROUND)
            .exp(DEFAULT_EXP)
            .level(DEFAULT_LEVEL);
        return character;
    }

    @Before
    public void initTest() {
        characterSearchRepository.deleteAll();
        character = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharacter() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isCreated());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate + 1);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacter.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testCharacter.getClasses()).isEqualTo(DEFAULT_CLASSES);
        assertThat(testCharacter.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testCharacter.getAlignment()).isEqualTo(DEFAULT_ALIGNMENT);
        assertThat(testCharacter.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testCharacter.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCharacter.getMaxHP()).isEqualTo(DEFAULT_MAX_HP);
        assertThat(testCharacter.getCurrentHP()).isEqualTo(DEFAULT_CURRENT_HP);
        assertThat(testCharacter.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testCharacter.getDexterity()).isEqualTo(DEFAULT_DEXTERITY);
        assertThat(testCharacter.getConstitution()).isEqualTo(DEFAULT_CONSTITUTION);
        assertThat(testCharacter.getWisdom()).isEqualTo(DEFAULT_WISDOM);
        assertThat(testCharacter.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
        assertThat(testCharacter.getCharisma()).isEqualTo(DEFAULT_CHARISMA);
        assertThat(testCharacter.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testCharacter.getExp()).isEqualTo(DEFAULT_EXP);
        assertThat(testCharacter.getLevel()).isEqualTo(DEFAULT_LEVEL);

        // Validate the Character in Elasticsearch
        Character characterEs = characterSearchRepository.findOne(testCharacter.getId());
        assertThat(characterEs).isEqualToComparingFieldByField(testCharacter);
    }

    @Test
    @Transactional
    public void generateCharacter() throws Exception {
        log.info("GenerateCharacter Test");
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character
        restCharacterMockMvc.perform(post("/api/characters/generate")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(DEFAULT_NAME)))
            .andExpect(status().isCreated());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate + 1);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testCharacter.getExp()).isEqualTo(DEFAULT_EXP);
//        assertThat(testCharacter.getLevel()).isEqualTo(DEFAULT_LEVEL);
//        assertThat(testCharacter.getClasses()).isEqualTo(DEFAULT_CLASSES);
//        assertThat(testCharacter.getSex()).isEqualTo(DEFAULT_SEX);
//        assertThat(testCharacter.getHeight()).isEqualTo(DEFAULT_HEIGHT);
//        assertThat(testCharacter.getWeight()).isEqualTo(DEFAULT_WEIGHT);
//        assertThat(testCharacter.getMaxHP()).isEqualTo(DEFAULT_MAX_HP);
//        assertThat(testCharacter.getCurrentHP()).isEqualTo(DEFAULT_CURRENT_HP);
//        assertThat(testCharacter.getStrength()).isEqualTo(DEFAULT_STRENGTH);
//        assertThat(testCharacter.getDexterity()).isEqualTo(DEFAULT_DEXTERITY);
//        assertThat(testCharacter.getWisdom()).isEqualTo(DEFAULT_WISDOM);
//        assertThat(testCharacter.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
//        assertThat(testCharacter.getCharisma()).isEqualTo(DEFAULT_CHARISMA);
//        assertThat(testCharacter.getAlignment()).isEqualTo(DEFAULT_ALIGNMENT);
//        assertThat(testCharacter.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
//        assertThat(testCharacter.getRace()).isEqualTo(DEFAULT_RACE);

        // Validate the Character in Elasticsearch
        Character characterEs = characterSearchRepository.findOne(testCharacter.getId());
        assertThat(characterEs).isEqualToComparingFieldByField(testCharacter);
    }

    @Test
    @Transactional
    public void createCharacterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character with an existing ID
        character.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = characterRepository.findAll().size();
        // set the field null
        character.setName(null);

        // Create the Character, which fails.

        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = characterRepository.findAll().size();
        // set the field null
        character.setLevel(null);

        // Create the Character, which fails.

        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isBadRequest());

        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCharacters() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get all the characterList
        restCharacterMockMvc.perform(get("/api/characters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE.toString())))
            .andExpect(jsonPath("$.[*].classes").value(hasItem(DEFAULT_CLASSES.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].alignment").value(hasItem(DEFAULT_ALIGNMENT.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxHP").value(hasItem(DEFAULT_MAX_HP)))
            .andExpect(jsonPath("$.[*].currentHP").value(hasItem(DEFAULT_CURRENT_HP)))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].dexterity").value(hasItem(DEFAULT_DEXTERITY)))
            .andExpect(jsonPath("$.[*].constitution").value(hasItem(DEFAULT_CONSTITUTION)))
            .andExpect(jsonPath("$.[*].wisdom").value(hasItem(DEFAULT_WISDOM)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].exp").value(hasItem(DEFAULT_EXP.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", character.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(character.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.race").value(DEFAULT_RACE.toString()))
            .andExpect(jsonPath("$.classes").value(DEFAULT_CLASSES.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.alignment").value(DEFAULT_ALIGNMENT.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.maxHP").value(DEFAULT_MAX_HP))
            .andExpect(jsonPath("$.currentHP").value(DEFAULT_CURRENT_HP))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.dexterity").value(DEFAULT_DEXTERITY))
            .andExpect(jsonPath("$.constitution").value(DEFAULT_CONSTITUTION))
            .andExpect(jsonPath("$.wisdom").value(DEFAULT_WISDOM))
            .andExpect(jsonPath("$.intelligence").value(DEFAULT_INTELLIGENCE))
            .andExpect(jsonPath("$.charisma").value(DEFAULT_CHARISMA))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()))
            .andExpect(jsonPath("$.exp").value(DEFAULT_EXP.intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingCharacter() throws Exception {
        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacter() throws Exception {
        // Initialize the database
        characterService.save(character);

        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Update the character
        Character updatedCharacter = characterRepository.findOne(character.getId());
        updatedCharacter
            .name(UPDATED_NAME)
            .race(UPDATED_RACE)
            .classes(UPDATED_CLASSES)
            .sex(UPDATED_SEX)
            .alignment(UPDATED_ALIGNMENT)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .maxHP(UPDATED_MAX_HP)
            .currentHP(UPDATED_CURRENT_HP)
            .strength(UPDATED_STRENGTH)
            .dexterity(UPDATED_DEXTERITY)
            .constitution(UPDATED_CONSTITUTION)
            .wisdom(UPDATED_WISDOM)
            .intelligence(UPDATED_INTELLIGENCE)
            .charisma(UPDATED_CHARISMA)
            .background(UPDATED_BACKGROUND)
            .exp(UPDATED_EXP)
            .level(UPDATED_LEVEL);

        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharacter)))
            .andExpect(status().isOk());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacter.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testCharacter.getClasses()).isEqualTo(UPDATED_CLASSES);
        assertThat(testCharacter.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCharacter.getAlignment()).isEqualTo(UPDATED_ALIGNMENT);
        assertThat(testCharacter.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testCharacter.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCharacter.getMaxHP()).isEqualTo(UPDATED_MAX_HP);
        assertThat(testCharacter.getCurrentHP()).isEqualTo(UPDATED_CURRENT_HP);
        assertThat(testCharacter.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testCharacter.getDexterity()).isEqualTo(UPDATED_DEXTERITY);
        assertThat(testCharacter.getConstitution()).isEqualTo(UPDATED_CONSTITUTION);
        assertThat(testCharacter.getWisdom()).isEqualTo(UPDATED_WISDOM);
        assertThat(testCharacter.getIntelligence()).isEqualTo(UPDATED_INTELLIGENCE);
        assertThat(testCharacter.getCharisma()).isEqualTo(UPDATED_CHARISMA);
        assertThat(testCharacter.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testCharacter.getExp()).isEqualTo(UPDATED_EXP);
        assertThat(testCharacter.getLevel()).isEqualTo(UPDATED_LEVEL);

        // Validate the Character in Elasticsearch
        Character characterEs = characterSearchRepository.findOne(testCharacter.getId());
        assertThat(characterEs).isEqualToComparingFieldByField(testCharacter);
    }

    @Test
    @Transactional
    public void updateNonExistingCharacter() throws Exception {
        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Create the Character

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(character)))
            .andExpect(status().isCreated());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCharacter() throws Exception {
        // Initialize the database
        characterService.save(character);

        int databaseSizeBeforeDelete = characterRepository.findAll().size();

        // Get the character
        restCharacterMockMvc.perform(delete("/api/characters/{id}", character.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean characterExistsInEs = characterSearchRepository.exists(character.getId());
        assertThat(characterExistsInEs).isFalse();

        // Validate the database is empty
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCharacter() throws Exception {
        // Initialize the database
        characterService.save(character);

        // Search the character
        restCharacterMockMvc.perform(get("/api/_search/characters?query=id:" + character.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE.toString())))
            .andExpect(jsonPath("$.[*].classes").value(hasItem(DEFAULT_CLASSES.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].alignment").value(hasItem(DEFAULT_ALIGNMENT.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxHP").value(hasItem(DEFAULT_MAX_HP)))
            .andExpect(jsonPath("$.[*].currentHP").value(hasItem(DEFAULT_CURRENT_HP)))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].dexterity").value(hasItem(DEFAULT_DEXTERITY)))
            .andExpect(jsonPath("$.[*].constitution").value(hasItem(DEFAULT_CONSTITUTION)))
            .andExpect(jsonPath("$.[*].wisdom").value(hasItem(DEFAULT_WISDOM)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].exp").value(hasItem(DEFAULT_EXP.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Character.class);
        Character character1 = new Character();
        character1.setId(1L);
        Character character2 = new Character();
        character2.setId(character1.getId());
        assertThat(character1).isEqualTo(character2);
        character2.setId(2L);
        assertThat(character1).isNotEqualTo(character2);
        character1.setId(null);
        assertThat(character1).isNotEqualTo(character2);
    }
}
