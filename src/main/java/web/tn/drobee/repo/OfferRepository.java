package web.tn.drobee.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.User;
@Repository
public interface OfferRepository  extends JpaRepository<Offer, Long> {
	

	Boolean existsByname(String name);

}
