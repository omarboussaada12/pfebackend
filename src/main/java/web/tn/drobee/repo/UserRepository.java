package web.tn.drobee.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM `users`\r\n" + "INNER JOIN `user_roles` ON `user_roles`.`user_id` = `users`.`id` \r\n"
			+ "INNER JOIN `roles` ON `roles`.`id` = `user_roles`.`role_id` \r\n"
			+ "where `roles`.`name` = \"ROLE_ADMIN\"", nativeQuery = true)
	List<User> findAllAdmin();
	
	@Query(value = "SELECT * FROM `users`\r\n" + "INNER JOIN `user_roles` ON `user_roles`.`user_id` = `users`.`id` \r\n"
			+ "INNER JOIN `roles` ON `roles`.`id` = `user_roles`.`role_id` \r\n"
			+ "where `roles`.`name` = \"ROLE_CLIENT\"", nativeQuery = true)
	List<User> findAllCLIENT();

}
