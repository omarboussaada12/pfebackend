package web.tn.drobee.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import web.tn.drobee.entity.Notification;
import web.tn.drobee.payload.response.UserResponse;
import web.tn.drobee.service.INotificationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class NotificationController {



    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    INotificationService notificationService;

    //send notification only for all clients
    @MessageMapping("/UsersNotif")
    @SendTo("/users/messages")
    public Notification sendU(final Notification notif) throws Exception {
    	notificationService.notificationforallClients(notif);
        return notif;
    }
    //send notification only for all admins
    @MessageMapping("/AdminsNotif")
    @SendTo("/admins/messages")
    public Notification sendA(final Notification notif) throws Exception {
    	notificationService.notificationforalladmins(notif);
        return notif;
    }
    //send notification privately for one client
    @MessageMapping("/private/{username}")
    @SendTo("/specific/{username}")
    public Notification sendPrivateMessage(@Payload Notification message,@DestinationVariable String username) {
    	notificationService.notificationtoprivateuser(message) ;
    	return message; 	 
    }
    
    //get 5 last notification 
    @GetMapping("/getnotication/{username}")
	@ResponseBody
	public List<Notification> getusers(@PathVariable("username") String username) {
	
		return 	notificationService.getnotificationbyusername(username);
	}
	
    
    
    
    
}

