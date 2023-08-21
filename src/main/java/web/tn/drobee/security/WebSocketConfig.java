package web.tn.drobee.security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import web.tn.drobee.jwt.JwtUtils;
import web.tn.drobee.service.UserService;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	
	 @Autowired
	 private JwtUtils jwtTokenProvider;
	 private static final Logger l = LogManager.getLogger(WebSocketConfig.class);
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/users","/specific","/admins");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
        
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/ws");
    	registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
    
 /* 
    public void configureClientInboundChannel(ChannelRegistration registration) {
    	  registration.interceptors(new ChannelInterceptor() {
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                   
                    String token = accessor.getFirstNativeHeader("Authorization");

                    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                        // Validate the token
                        if (jwtTokenProvider.validateJwtToken(token)) {
                            l.info("we are here");
                            String username = jwtTokenProvider.getUserNameFromJwtToken(token);
                            // Add the user-specific destination prefix
                            accessor.setDestination("/private/" + username);
                        }
                    }
                }
                return message;
            }
        });
    }
*/
   

}







