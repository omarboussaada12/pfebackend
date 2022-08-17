package web.tn.drobee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.Reponse;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Long> {

}
