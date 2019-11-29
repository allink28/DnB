package allink28.dnb.web.rest;

import allink28.dnb.domain.Campaign;
import allink28.dnb.service.CampaignService;
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
 * REST controller for managing Campaign.
 */
@RestController
@RequestMapping("/api")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private static final String ENTITY_NAME = "campaign";

    private final CampaignService campaignService;

    public CampaignResource(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * POST  /campaigns : Create a new campaign.
     *
     * @param campaign the campaign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaign, or with status 400 (Bad Request) if the campaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campaigns")
    @Timed
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) throws URISyntaxException {
        log.debug("REST request to save Campaign : {}", campaign);
        if (campaign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new campaign cannot already have an ID")).body(null);
        }
        Campaign result = campaignService.save(campaign);
        return ResponseEntity.created(new URI("/api/campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campaigns : Updates an existing campaign.
     *
     * @param campaign the campaign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaign,
     * or with status 400 (Bad Request) if the campaign is not valid,
     * or with status 500 (Internal Server Error) if the campaign couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campaigns")
    @Timed
    public ResponseEntity<Campaign> updateCampaign(@Valid @RequestBody Campaign campaign) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaign);
        if (campaign.getId() == null) {
            return createCampaign(campaign);
        }
        Campaign result = campaignService.save(campaign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campaigns : get all the campaigns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     */
    @GetMapping("/campaigns")
    @Timed
    public ResponseEntity<List<Campaign>> getAllCampaigns(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Campaigns");
        Page<Campaign> page = campaignService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /campaigns/:id : get the "id" campaign.
     *
     * @param id the id of the campaign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaign, or with status 404 (Not Found)
     */
    @GetMapping("/campaigns/{id}")
    @Timed
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
        log.debug("REST request to get Campaign : {}", id);
        Campaign campaign = campaignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campaign));
    }

    /**
     * DELETE  /campaigns/:id : delete the "id" campaign.
     *
     * @param id the id of the campaign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campaigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        log.debug("REST request to delete Campaign : {}", id);
        campaignService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/campaigns?query=:query : search for the campaign corresponding
     * to the query.
     *
     * @param query the query of the campaign search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/campaigns")
    @Timed
    public ResponseEntity<List<Campaign>> searchCampaigns(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Campaigns for query {}", query);
        Page<Campaign> page = campaignService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/campaigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
