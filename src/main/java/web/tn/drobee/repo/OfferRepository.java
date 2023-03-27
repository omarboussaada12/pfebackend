package web.tn.drobee.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.response.OfferResponse;
@Repository
public interface OfferRepository  extends JpaRepository<Offer, Long> {
	
	Boolean existsByid(long id);

	Boolean existsByname(String name);

}
