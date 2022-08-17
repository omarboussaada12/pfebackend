package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;

public interface IUserService {
	List<User> Listusers();

	User Adduser(SignupRequest user);

	void Deleteuser(Long id);

	User Updateuser(User a , Long id);

	User getuserbyusername(String username);
	
	User addroletouser(String username , String rolename );

}
