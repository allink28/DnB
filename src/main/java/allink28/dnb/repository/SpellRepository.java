package allink28.dnb.repository;

import allink28.dnb.domain.Spell;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Spell entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpellRepository extends JpaRepository<Spell,Long> {
    
}
