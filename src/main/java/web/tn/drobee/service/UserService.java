package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.Role;
import web.tn.drobee.entity.User;

public interface UserService {
 User saveUser(User user);
 Role saveRole(Role role);
 void addRoleToUser(String username ,String rolename);
 User getUser(String username);
 //need to load 50 user per time
 List<User> getUsers();
}
