package web.tn.drobee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import web.tn.drobee.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {
User findByUsername(String username);
}
