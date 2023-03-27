package web.tn.drobee.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import web.tn.drobee.jwt.JwtUtils;


@Component
public class StompHandler implements ChannelInterceptor {
    private JwtUtils tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!tokenProvider.validateJwtToken(accessor.getFirstNativeHeader("Authorization")))
                throw new AccessDeniedException("");
        }
        return message;
    }
}
