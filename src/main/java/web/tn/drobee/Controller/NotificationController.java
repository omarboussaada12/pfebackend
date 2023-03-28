package web.tn.drobee.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import web.tn.drobee.entity.Notification;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class NotificationController {




    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    // Mapped as /app/application
    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Notification send(final Notification notif) throws Exception {
        return notif;
    }
 // Mapped as /app/private
    @MessageMapping("/private/{username}")
    @SendTo("/specific/{username}")
    public Notification sendPrivateMessage(@Payload Notification message,@DestinationVariable String username) {
    	 return message;
    }
    
    
}

