package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.Offer;

public interface ICommandeService {
	 List<Commande> Listcommandes();
	 List<Commande> Listcommandesbyuser();
	 List<Commande> Listcommandesbyyear();
	 Offer Addcommande(Offer offer); 
	 void Deletecommande(Long id); 
	// Offer Updateoffer(Offer a); 
	 Offer getcommandebyid(Long id);

}
