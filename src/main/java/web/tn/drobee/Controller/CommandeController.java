package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


@RestController
public class CommandeController {
	
	@Autowired 
	 ICommandeService commandeService; 
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	UserRepository userRepository;
	
	 @GetMapping("/get-all-commandes") 
	 @ResponseBody 
	 public List<CommandeReponse> getCommandes() { 
		 List<CommandeReponse> list = commandeService.Listcommandes(); 
		 return list; } 
	 
	 @PostMapping("/add-commande") 
	 @ResponseBody 
	 public ResponseEntity<?> addCommande(@Valid @RequestBody CommandeRequest  a) { 
		 if (!offerRepository.existsByname(a.getOffername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: offer name not found "));
			}
		 if (!userRepository.existsByUsername(a.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: username name not found"));
			}
		 commandeService.Addcommande(a); 
		 return ResponseEntity.ok(new MessageResponse("commande add successfully")); }
	 
	 @DeleteMapping("/delete-Commande/{commande-id}") 
	 @ResponseBody 
	 public void deleteCommande(@PathVariable("commande-id") Long commandeId) {
		 commandeService.Deletecommande(commandeId); 
		  } 
	 @PutMapping("/update-Commande") 
	 @ResponseBody 
	 public ResponseEntity<?> updateCommande(@Valid @RequestBody CommandeRequest commandeRequest) { 
		 if (!offerRepository.existsByname(commandeRequest.getOffername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: offer name is not found!"));
			}
		 commandeService.Updatecommande(commandeRequest);
		 return ResponseEntity.ok(new MessageResponse("offer updated successfully!"));
		 }
	 


}
