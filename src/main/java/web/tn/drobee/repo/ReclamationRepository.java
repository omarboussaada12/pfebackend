package web.tn.drobee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Reclamation;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

	@Query(value = "SELECT * FROM reclamation ORDER BY date DESC", nativeQuery = true)
	List<Reclamation> findAllByOrderByDateAsc();

	@Query(value = "SELECT * FROM reclamation where user_id = ?1 ORDER BY date DESC", nativeQuery = true)
	List<Reclamation> findAllByuserOrderByDateAsc(Long user_id);

}
