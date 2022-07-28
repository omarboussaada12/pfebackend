package web.tn.drobee.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Offer;
@Repository
public interface OfferRepository  extends JpaRepository<Offer, Long> {
	Boolean existsByname(String name);
}
