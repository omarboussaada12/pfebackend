package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import web.tn.drobee.payload.request.ReclamationRequest;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.payload.response.ReclamationResponse;
import web.tn.drobee.repo.UserRepository;
import web.tn.drobee.service.IReclamationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ReclamationController {

	@Autowired
	IReclamationService reclamationService;

	@Autowired
	UserRepository userrepository;

	@GetMapping("/get-all-reclamation")
	@ResponseBody
	public List<ReclamationResponse> getAllReclamation() {
		List<ReclamationResponse> list = reclamationService.Listreclamations();
		return list;
	}

	@GetMapping("/get-reclamation-byuser/{username}")
	@ResponseBody
	public List<ReclamationResponse> getReclamationUser(@PathVariable(value = "username") String username) {
		List<ReclamationResponse> list = reclamationService.Listreclamationsbyuser(username);
		return list;
	}

	@GetMapping("/get-reclamation/{reclamation-id}")
	@ResponseBody
	public ReclamationResponse getReclamation(@PathVariable("reclamation-id") Long reclamationId) {

		return reclamationService.getreclamationbyid(reclamationId);
	}

	@PostMapping("/add-reclamation")
	@ResponseBody
	public ResponseEntity<?> addReclamation(@Valid @RequestBody ReclamationRequest a) {

		if (!userrepository.existsByUsername(a.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: username name not found"));
		}
		reclamationService.Addreclamation(a);
		return ResponseEntity.ok(new MessageResponse("reclamation add successfully"));
	}

	@DeleteMapping("/delete-reclamation/{reclamation-id}")
	@ResponseBody
	public void deleteReclamation(@PathVariable("reclamation-id") Long reclamationId) {
		reclamationService.Deletereclamation(reclamationId);
	}

	// not perfect
	// we should check how many reponse we have
	@PutMapping("/update-reclamation/{reclamation-id}")
	@ResponseBody
	public ResponseEntity<?> updateReclamation(@Valid @RequestBody ReclamationRequest reclamationRequest) {
		ReclamationResponse r = reclamationService.getreclamationbyid(reclamationRequest.getId());
		if (r.getStatus() == "") {
			return ResponseEntity.badRequest().body(new MessageResponse("reclamation traited"));
		}
		reclamationService.Updatereclamation(reclamationRequest);
		return ResponseEntity.ok(new MessageResponse("reclamation updated successfully"));
	}
	
	@PutMapping("/ReclamationProcessing/{reclamation-id}")
	@ResponseBody
	public ResponseEntity<?> processreclamation(@PathVariable("reclamation-id") Long reclamationId ,@RequestBody String reponse) {
		return reclamationService.traitment(reclamationId,reponse) ;
		
	}
}
