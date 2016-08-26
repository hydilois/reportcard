package cm.elsha.cardreport.repository;

import cm.elsha.cardreport.domain.Matiere;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Matiere entity.
 */
@SuppressWarnings("unused")
public interface MatiereRepository extends JpaRepository<Matiere,Long> {

}
