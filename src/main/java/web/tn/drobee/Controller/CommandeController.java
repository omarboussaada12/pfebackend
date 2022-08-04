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
import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.payload.request.CommandeRequest;
import web.tn.drobee.payload.response.CommandeReponse;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.repo.CommandeRepository;
import web.tn.drobee.repo.OfferRepository;
import web.tn.drobee.repo.UserRepository;
import web.tn.drobee.service.ICommandeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommandeController {

	@Autowired
	ICommandeService commandeService;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommandeRepository commandeRepository;

	@GetMapping("/get-all-commandes")
	@ResponseBody
	public List<CommandeReponse> getCommandes() {
		List<CommandeReponse> list = commandeService.Listcommandes();
		return list;
	}

	@GetMapping("/get-all-commandesbyuser/{username}")
	@ResponseBody
	public List<CommandeReponse> getCommandesbyuser(@PathVariable(value = "username") String username) {
		List<CommandeReponse> list = commandeService.Listcommandesbyuser(username);
		return list;
	}
	
	@GetMapping("/get-commande/{commande-id}")
	@ResponseBody
	public CommandeReponse getCommande(@PathVariable("commande-id") Long commandeId) {
		
		return 	commandeService.getcommandebyid(commandeId);
	}

	@PostMapping("/add-commande")
	@ResponseBody
	public ResponseEntity<?> addCommande(@Valid @RequestBody CommandeRequest a) {
		
		if (!offerRepository.existsByname(a.getOffername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name not found "));
		}
		if (!userRepository.existsByUsername(a.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: username name not found"));
		}
		commandeService.Addcommande(a);
		return ResponseEntity.ok(new MessageResponse("commande add successfully"));
	}

	@DeleteMapping("/delete-Commande/{commande-id}")
	@ResponseBody
	public void deleteCommande(@PathVariable("commande-id") Long commandeId) {
		commandeService.Deletecommande(commandeId);
	}

	@PutMapping("/update-Commande")
	@ResponseBody
	public ResponseEntity<?> updateCommande(@Valid @RequestBody CommandeRequest commandeRequest) {
		if (!commandeRepository.existsByid(commandeRequest.getId())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: commande  not found"));
		}
		if (!offerRepository.existsByname(commandeRequest.getOffername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name is not found!"));
		}
		return 	commandeService.Updatecommande(commandeRequest);
	}
	
	@PutMapping("/valider-Commande/{commande-id}")
	@ResponseBody
	public ResponseEntity<?> validerCommande(@PathVariable("commande-id") Long commandeId) {
		if (!commandeRepository.existsByid(commandeId)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: commande  not found"));
		}
		return 	commandeService.validercommande(commandeId);
	}
	

}
