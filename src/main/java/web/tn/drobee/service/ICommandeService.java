package web.tn.drobee.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import web.tn.drobee.entity.Commande;
import web.tn.drobee.payload.request.CommandeRequest;
import web.tn.drobee.payload.response.CommandeReponse;

public interface ICommandeService {
	List<CommandeReponse> Listcommandes();

	List<CommandeReponse> Listcommandesbyuser(String user);

	Commande Addcommande(CommandeRequest commanderequest);

	void Deletecommande(Long id);

	ResponseEntity<?> Updatecommande(long id ,CommandeRequest a);

	CommandeReponse getcommandebyid(Long id);

	ResponseEntity<?> validercommande(long id);

	ResponseEntity<?> refusercommande(long id);

}
