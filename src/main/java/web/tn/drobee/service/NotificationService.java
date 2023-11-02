package web.tn.drobee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Notification;
import web.tn.drobee.entity.User;
import web.tn.drobee.repo.NotificationRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class NotificationService implements INotificationService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notificationRepository ;
	
	@Override
	public void notificationforalladmins(Notification n) {
		
	 List<User>	 admins = userRepository.findAllAdmin();
		for ( User  a : admins) {
			Notification cr = new Notification();
			cr.setText(n.getText());
			cr.setTo(a.getUsername());
			notificationRepository.save(cr);
		}
	}

	@Override
	public void notificationtoprivateuser(Notification n) {
		User u = userRepository.findByUsername(n.getTo())
		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " ));
		Notification cr = new Notification();
		cr.setText(n.getText());
		cr.setTo(u.getUsername());
		notificationRepository.save(cr);
		
	}

	@Override
	public void notificationforallClients(Notification n) {
		 List<User>	 Clients = userRepository.findAllCLIENT();
			for ( User  c : Clients) {
				Notification cr = new Notification();
				cr.setText(n.getText());
				cr.setTo(c.getUsername());
				notificationRepository.save(cr);
			}
		
	}

	@Override
	public List<Notification> getnotificationbyusername(String username) {
		return  notificationRepository.getLast5NotificationsByUsername(username);
	}

}
