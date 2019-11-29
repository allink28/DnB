package allink28.dnb.repository;

import allink28.dnb.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Campaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {


    public Campaign findByTitle(String title);
}
