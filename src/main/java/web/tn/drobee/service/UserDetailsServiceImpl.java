package web.tn.drobee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import web.tn.drobee.entity.User;
import web.tn.drobee.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	
	public UserDetails loadUserByToken(String token) throws UsernameNotFoundException {
        String username = getUsernameFromToken(token);
        return loadUserByUsername(username);
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey("drobeeSecretKey").parseClaimsJws(token).getBody().getSubject();
    }

}

