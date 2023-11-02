package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.Notification;

public interface INotificationService {
	
	void notificationforalladmins (Notification n);
	
	void notificationtoprivateuser(Notification n  );
	
	void notificationforallClients(Notification n); 
	
	List<Notification> getnotificationbyusername( String username);

}
