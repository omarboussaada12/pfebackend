package web.tn.drobee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import web.tn.drobee.entity.Role;


public interface RoleRepo extends JpaRepository<Role,Long>{
	Role findByName(String name);
}
