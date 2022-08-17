package web.tn.drobee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.Role;
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
			cr.setDate(ac.getDate());

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

			l.info(" for commande id : " + cr.getId());
			lcr.add(cr);

		}
		return lcr;
	}

	@Override
	public List<Commande> Listcommandesbyyear() {
		// TODO Auto-generated method stub
		return null;
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
		c.setStatus("waiting for confirmation");
		Commande cc = new Commande(o, c.getNbrunit(), c.getRegion(), c.getUser(), c.getDate(), c.getStatus());
		l.info("adding commande with id {} and servicename {} for user {} ", cc.getId(), cc.getOffer().getName(),
				cc.getUser().getUsername());
		return commandeRepository.save(cc);

	}

	@Override
	public void Deletecommande(Long id) {
		l.info("Deleting commande with ID: " + id);
		commandeRepository.deleteById(id);

	}

	@Override
	public ResponseEntity<?> Updatecommande(CommandeRequest commandeRequest) {
		l.info("Updating Commande with ID: " + commandeRequest.getId());
		Commande c = commandeRepository.findById(commandeRequest.getId()).get();
		if (c.getStatus().equals("confirmer")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: commande already traited"));
		}
		Offer o = offerService.getbyname(commandeRequest.getOffername());
		c.setUser(userRepository.findByUsername(commandeRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + commandeRequest.getUsername())));
		c.setNbrunit(commandeRequest.getNbrunit());
		c.setRegion(commandeRequest.getRegion());
		c.setId(commandeRequest.getId());
		c.setStatus("waiting for confirmation");
		c.setOffer(o);
		Commande cc = new Commande(c.getId(), o, c.getNbrunit(), c.getRegion(), c.getUser(), c.getDate(),
				c.getStatus());
		Deletecommande(c.getId());
		this.commandeRepository.save(cc);
		return ResponseEntity.badRequest().body(new MessageResponse("Commande updated successfully"));

	}

	@Override
	public CommandeReponse getcommandebyid(Long id) {
		l.info("fetching Commande with ID: " + id);
		Commande c = commandeRepository.findById(id).get();
		CommandeReponse cr = new CommandeReponse(c.getId(), c.getUser().getUsername(), c.getOffer().getName(),
				c.getNbrunit(), c.getRegion(), c.getDate(), c.getStatus());
		return cr;
	}

	@Override
	public ResponseEntity<?> validercommande(long id) {
		Commande c = commandeRepository.findById(id).get();
		Set<Role> roles = new HashSet<>();
		roles = c.getUser().getRoles();
		boolean haveroleclient = false;
		for (Role r : roles) {
			if (r.getName().equals(ERole.ROLE_CLIENT)) {
				haveroleclient = true;
				break;
			}
		}
		if (haveroleclient == false) {
			userService.addroletouser(c.getUser().getUsername(), "ROLE_CLIENT");
			c.setStatus("confimer");
			commandeRepository.save(c);

		}
		return ResponseEntity.ok(new MessageResponse("commande confirmer successfully!"));
	}

}
