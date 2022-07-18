package web.tn.drobee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Commande;
@Repository
public interface CommandeRepository  extends JpaRepository<Commande, Long> {

}
