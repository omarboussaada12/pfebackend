package web.tn.drobee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.CommandeRequest;
import web.tn.drobee.payload.response.CommandeReponse;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.repo.CommandeRepository;
import web.tn.drobee.repo.RoleRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class CommandeService implements ICommandeService {

	@Autowired
	IOfferService offerService;

	@Autowired
	IUserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommandeRepository commandeRepository;

	@Autowired
	RoleRepository roleRepository;

	private static final Logger l = LogManager.getLogger(CommandeService.class);

	@Override
	public List<CommandeReponse> Listcommandes() {
		List<CommandeReponse> lcr = new ArrayList<CommandeReponse>();
		List<Commande> commandes = (List<Commande>) commandeRepository.findAllByOrderByDateAsc();
		l.info("fetching list de commande");
		for (Commande ac : commandes) {
			CommandeReponse cr = new CommandeReponse();
			cr.setId(ac.getId());
			cr.setNbrunit(ac.getNbrunit());
			cr.setOffername(ac.getOffer().getName());
			cr.setUsername(ac.getUser().getUsername());
			cr.setRegion(ac.getRegion());
			cr.setStatus(ac.getStatus());
			cr.setDatec(ac.getDatec());

			l.info(" for commande id : " + cr.getId());
			lcr.add(cr);

		}
		return lcr;
	}

	@Override
	public List<CommandeReponse> Listcommandesbyuser(String username) {
		User u = userRepository.findByUsername(username).get();
		List<CommandeReponse> lcr = new ArrayList<CommandeReponse>();
		List<Commande> commandes = (List<Commande>) commandeRepository.findAllByuserOrderByDateAsc(u.getId());
		l.info("fetching list de commande");
		for (Commande ac : commandes) {
			CommandeReponse cr = new CommandeReponse();
			cr.setId(ac.getId());
			cr.setNbrunit(ac.getNbrunit());
			cr.setOffername(ac.getOffer().getName());
			cr.setUsername(ac.getUser().getUsername());
			cr.setRegion(ac.getRegion());
			cr.setStatus(ac.getStatus());
			cr.setDate(ac.getDate());
			cr.setDatec(ac.getDatec()); 

			l.info(" for commande id : " + cr.getId());
			lcr.add(cr);

		}
		return lcr;
	}

	

	@Override
	public Commande Addcommande(CommandeRequest commandeRequest) throws UsernameNotFoundException {
		Commande c = new Commande();
		Offer o = offerService.getbyname(commandeRequest.getOffername());
		c.setUser(userRepository.findByUsername(commandeRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + commandeRequest.getUsername())));
		c.setNbrunit(commandeRequest.getNbrunit());
		c.setDate(new Date());
		c.setRegion(commandeRequest.getRegion());
		c.setStatus("not processed");
		c.setOffer(o);
		c.setDatec(commandeRequest.getDatec());
		/** chech if datec > a date **/
		
		l.info("adding commande with id {} and servicename {} for user {} for {} DateC", c.getId(),
				c.getOffer().getName(), c.getUser().getUsername(), c.getDatec());
		return commandeRepository.save(c);

	}

	@Override
	public void Deletecommande(Long id) {
		l.info("Deleting commande with ID: " + id);
		commandeRepository.deleteById(id);

	}

	@Override
	public ResponseEntity<?> Updatecommande(long id ,CommandeRequest commandeRequest) {
		l.info("Updating Commande with ID: " + commandeRequest.getId());
		Commande c = commandeRepository.findById(id).get();
		Offer o = offerService.getbyname(commandeRequest.getOffername());
		c.setNbrunit(commandeRequest.getNbrunit());
		c.setRegion(commandeRequest.getRegion());
		c.setStatus("not processed");
		c.setOffer(o);
		c.setDatec(commandeRequest.getDatec());
		this.commandeRepository.save(c);
		return ResponseEntity.badRequest().body(new MessageResponse("Commande updated successfully"));

	}

	@Override
	public CommandeReponse getcommandebyid(Long id) {
		l.info("fetching Commande with ID: " + id);
		Commande c = commandeRepository.findById(id).get();
		CommandeReponse cr = new CommandeReponse(c.getId(), c.getUser().getUsername(), c.getOffer().getName(),
				c.getNbrunit(), c.getRegion(), c.getDate(), c.getDatec() ,c.getStatus());
		return cr;
	}

	@Override
	public ResponseEntity<?> validercommande(long id) {
		Commande c = commandeRepository.findById(id).get();
		userService.changeroletouser(c.getUser().getUsername(), "ROLE_CLIENT");
		c.setStatus("approved");
		commandeRepository.save(c);

		return ResponseEntity.ok(new MessageResponse("commande confirmer successfully!"));
	}
	
	@Override
	public ResponseEntity<?> refusercommande(long id) {
		Commande c = commandeRepository.findById(id).get();
		userService.changeroletouser(c.getUser().getUsername(), "ROLE_CLIENT");
		c.setStatus("denied");
		commandeRepository.save(c);

		return ResponseEntity.ok(new MessageResponse("commande refuser "));
	}

}
