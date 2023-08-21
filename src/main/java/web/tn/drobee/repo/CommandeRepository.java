package web.tn.drobee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Commande;
@Repository
public interface CommandeRepository  extends JpaRepository<Commande, Long> {
	
	Boolean existsByid(long id);
	
	@Query(value = "SELECT * FROM commande ORDER BY date DESC",nativeQuery = true)
	List<Commande> findAllByOrderByDateAsc();
	
	@Query(value = "SELECT * FROM commande where user_id = ?1 ORDER BY date DESC",nativeQuery = true)
	List<Commande> findAllByuserOrderByDateAsc(long userid);
	
	@Query(value = "SELECT * FROM commande where offer_id = ?1 ORDER BY date DESC",nativeQuery = true)
	List<Commande> findAllByofferOrderByDateAsc(long offerid);
	
	
}
