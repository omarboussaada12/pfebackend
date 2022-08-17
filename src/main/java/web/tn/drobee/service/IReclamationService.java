package web.tn.drobee.service;

import java.util.List;
import web.tn.drobee.entity.Reclamation;
import web.tn.drobee.payload.request.ReclamationRequest;
import web.tn.drobee.payload.response.ReclamationResponse;

public interface IReclamationService {

	List<ReclamationResponse> Listreclamations();

	Reclamation Addreclamation(ReclamationRequest reclamationRequest);

	void Deletereclamation(Long id);

	Reclamation Updatereclamation(ReclamationRequest reclamationRequest, Long idr);

	ReclamationResponse getreclamationbyid(Long id);

	List<ReclamationResponse> Listreclamationsbyuser(String username);

}
