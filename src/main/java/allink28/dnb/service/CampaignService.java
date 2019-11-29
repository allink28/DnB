package allink28.dnb.service;

import allink28.dnb.domain.Campaign;
import allink28.dnb.domain.Character;
import allink28.dnb.repository.CampaignRepository;
import allink28.dnb.repository.search.CampaignSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Campaign.
 */
@Service
@Transactional
public class CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;

    private final CampaignSearchRepository campaignSearchRepository;

    public CampaignService(CampaignRepository campaignRepository, CampaignSearchRepository campaignSearchRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignSearchRepository = campaignSearchRepository;
    }

    /**
     * Save a campaign.
     *
     * @param campaign the entity to save
     * @return the persisted entity
     */
    public Campaign save(Campaign campaign) {
        log.debug("Request to save Campaign : {}", campaign);
        Campaign result = campaignRepository.save(campaign);
        campaignSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the campaigns.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Campaign> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable);
    }

    /**
     *  Get one campaign by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Campaign findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findOne(id);
    }

    /**
     *  Delete the  campaign by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.delete(id);
        campaignSearchRepository.delete(id);
    }

    /**
     * Search for the campaign corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Campaign> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Campaigns for query {}", query);
        Page<Campaign> result = campaignSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    public Campaign generateCampaign(Set<Character> players) {
        Campaign campaign = new Campaign();
        if (players != null) {
            campaign.setPlayers(players);
        }
        if (campaignRepository.findByTitle("The Beginning") == null) {
            campaign.setTitle("The Beginning");
        } else {
            //generate campaign name
            Random rand = new Random();
            String campaignName = campaignNamePrefix[rand.nextInt(campaignNamePrefix.length)]
                + campaignNamePostfix[rand.nextInt(campaignNamePostfix.length)];
            campaign.setTitle(campaignName);
        }
        return campaign;
    }

    private static String[] campaignNamePrefix = new String[] {
        "The Broken ",
        "The Lost ",
        "The Golden ",
        "Revenge of the ",
        "Wrath of the ",
        "Rage of the "
    };
    private static String[] campaignNamePostfix = new String[] {
        "Arrow",
        "Dagger",
        "Sceptre",
        "Specter",
        "Temple",
        "Vampires",
        "Wolves",
        "Zombies"
    };
}
