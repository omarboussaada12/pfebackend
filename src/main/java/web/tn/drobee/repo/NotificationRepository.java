package web.tn.drobee.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.tn.drobee.entity.Notification;

public interface NotificationRepository extends JpaRepository <Notification, Long>  {
	
	@Query(value = "SELECT * FROM `notification` WHERE recipient = :username ORDER BY `id` DESC LIMIT 5", nativeQuery = true)
	List<Notification> getLast5NotificationsByUsername(@Param("username") String username);


}
