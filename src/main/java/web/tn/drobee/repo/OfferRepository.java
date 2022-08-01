package web.tn.drobee.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Offer;
@Repository
public interface OfferRepository  extends JpaRepository<Offer, Long> {
	
	Boolean existsByname(String name);

}
