package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.payload.request.CommandeRequest;
import web.tn.drobee.payload.response.CommandeReponse;

public interface ICommandeService {
	 List<CommandeReponse> Listcommandes();
	 List<Commande> Listcommandesbyuser();
	 List<Commande> Listcommandesbyyear();
	 Commande Addcommande(CommandeRequest commanderequest); 
	 void Deletecommande(Long id); 
	 Commande Updatecommande(CommandeRequest a); 
	 Commande getcommandebyid(Long id);
	 

}
