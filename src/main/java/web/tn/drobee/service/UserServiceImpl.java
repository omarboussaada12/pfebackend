package web.tn.drobee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;
import web.tn.drobee.repo.RoleRepo;
import web.tn.drobee.repo.UserRepo;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

	 private static Logger logger = LoggerFactory.getLogger(Slf4j.class);
	 @Autowired
	  UserRepo userRepo;
	 @Autowired
	  RoleRepo roleRepo;
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		logger.info("saving new user = {}",user.getUsername());
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		logger.info("saving new role = {}",role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		// TODO Auto-generated method stub
		logger.info("adding role {} to user  {}",rolename,username);
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(rolename);
		user.getRoles().add(role);
		userRepo.save(user);
	}

	@Override
	public User getUser(String username) {
		logger.info("fetching user {}",username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User>getUsers() {
		logger.info("fetching all users ");
		return userRepo.findAll();
	}

}
