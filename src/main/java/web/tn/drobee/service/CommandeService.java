package web.tn.drobee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.CommandeRequest;
import web.tn.drobee.payload.response.CommandeReponse;
import web.tn.drobee.repo.CommandeRepository;
import web.tn.drobee.repo.OfferRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class CommandeService implements ICommandeService {

	@Autowired
	OfferService offerService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommandeRepository commandeRepository;
	
	private static final Logger l = LogManager.getLogger(CommandeService.class);
	
	@Override
	public List<CommandeReponse> Listcommandes() {
		List<CommandeReponse> lcr = new ArrayList<CommandeReponse>();
		List<Commande> commandes = (List<Commande>) commandeRepository.findAll();
		l.info("fetching list de commande");
		for(Commande ac : commandes)
		{
			CommandeReponse cr = new CommandeReponse();
			cr.setId(ac.getId());
			cr.setNbrunit(ac.getNbrunit());
			cr.setOffername(ac.getOffer().getName());
			cr.setUsername(ac.getUser().getUsername());
			cr.setRegion(ac.getRegion());
			cr.setStatus(ac.getStatus());
			cr.setDate(ac.getDate());
			
			l.info(" for commande id : "+ cr.getId());
			lcr.add(cr);
			
		}
		return lcr;
	}

	@Override
	public List<Commande> Listcommandesbyuser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Commande> Listcommandesbyyear() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commande Addcommande(CommandeRequest commandeRequest ) throws UsernameNotFoundException {
		Commande c = new Commande();
		Offer o = offerService.getbyname(commandeRequest.getOffername());
	  c.setUser(userRepository.findByUsername(commandeRequest.getUsername())
	  .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + commandeRequest.getUsername())));
	  c.setNbrunit(commandeRequest.getNbrunit());
	  c.setDate(new Date());
	  c.setRegion(commandeRequest.getRegion());
	  c.setStatus("waiting for confirmation");
	  Commande cc = new Commande(o ,c.getNbrunit(), c.getRegion(), c.getUser(), c.getDate(), c.getStatus());
	  l.info("adding commande with id {} and servicename {} for user {} ", cc.getId() ,cc.getOffer().getName() , cc.getUser().getUsername());
		return  commandeRepository.save(cc);
		
	}

	@Override
	public void Deletecommande(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Offer Updatecommande(Commande a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offer getcommandebyid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
