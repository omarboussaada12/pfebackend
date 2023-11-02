package web.tn.drobee.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.SignupRequest;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.payload.response.UserResponse;

public interface IUserService {
	List<UserResponse> Listusers();

	void Deleteuser(String username);

	ResponseEntity<MessageResponse> Updateuserinfo(String  username , SignupRequest a );
	
	User Updateuserimage(String  username , MultipartFile file );

	UserResponse getuserbyusername(String username);
	
	User changeroletouser(String username , String rolename );
	
	List<UserResponse> ListusersAdmin();

}
