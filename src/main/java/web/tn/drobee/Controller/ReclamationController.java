package web.tn.drobee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import web.tn.drobee.payload.response.ReclamationResponse;
import web.tn.drobee.service.IReclamationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ReclamationController {
	
	@Autowired
	IReclamationService ReclamationService;

	@GetMapping("/get-reclamation-byuser/{username}")
	@ResponseBody
	public List<ReclamationResponse> getCommandesbyuser(@PathVariable(value = "username") String username) {
		List<ReclamationResponse> list = ReclamationService.Listreclamationsbyuser(username);
		return list;
	}
}
