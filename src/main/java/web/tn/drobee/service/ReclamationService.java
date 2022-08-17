package web.tn.drobee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Reclamation;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.request.ReclamationRequest;
import web.tn.drobee.payload.response.ReclamationResponse;
import web.tn.drobee.repo.ReclamationRepository;
import web.tn.drobee.repo.UserRepository;

@Service
public class ReclamationService implements IReclamationService {

	@Autowired
	ReclamationRepository reclamationRepository;

	@Autowired
	UserRepository userRepository;

	private static final Logger l = LogManager.getLogger(ReclamationService.class);

	@Override
	public List<ReclamationResponse> Listreclamations() {
		List<ReclamationResponse> lrr = new ArrayList<ReclamationResponse>();
		List<Reclamation> Reclamations = (List<Reclamation>) reclamationRepository.findAllByOrderByDateAsc();
		l.info("fetching list d'offer");
		for (Reclamation ac : Reclamations) {
			ReclamationResponse rr = new ReclamationResponse();
			l.info("Reclamation for user : " + ac.getUser().getUsername());
			rr.setId(ac.getId());
			rr.setUsername(ac.getUser().getUsername());
			rr.setMessage(ac.getMessage());
			rr.setDate(ac.getDate());
			rr.setStatus(ac.isStatus());
			lrr.add(rr);
		}
		return lrr;
	}

	@Override
	public List<ReclamationResponse> Listreclamationsbyuser(String username) {
		User u = userRepository.findByUsername(username).get();
		List<ReclamationResponse> lrr = new ArrayList<ReclamationResponse>();
		List<Reclamation> Reclamations = (List<Reclamation>) reclamationRepository
				.findAllByuserOrderByDateAsc(u.getId());
		for (Reclamation ac : Reclamations) {
			ReclamationResponse rr = new ReclamationResponse();
			l.info("Reclamation for user : " + ac.getUser().getUsername());
			rr.setId(ac.getId());
			rr.setUsername(ac.getUser().getUsername());
			rr.setMessage(ac.getMessage());
			rr.setDate(ac.getDate());
			rr.setStatus(ac.isStatus());
			lrr.add(rr);
		}
		return lrr;
	}

	@Override
	public Reclamation Addreclamation(ReclamationRequest reclamationRequest) {
		Reclamation r = new Reclamation();
		r.setMessage(reclamationRequest.getMessage());
		r.setDate(new Date());
		r.setStatus(false);
		r.setUser(userRepository.findByUsername(reclamationRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(
						"User Not Found with username: " + reclamationRequest.getUsername())));

		return reclamationRepository.save(r);
	}

	@Override
	public void Deletereclamation(Long id) {
		reclamationRepository.deleteById(id);

	}

	@Override
	public Reclamation Updatereclamation(ReclamationRequest reclamationRequest, Long idr) {
		Reclamation r = reclamationRepository.findById(idr).get();
		r.setMessage(reclamationRequest.getMessage());
		r.setStatus(false);
		return reclamationRepository.save(r);
	}

	@Override
	public ReclamationResponse getreclamationbyid(Long id) {
		Reclamation r = reclamationRepository.findById(id).get();
		ReclamationResponse rp = new ReclamationResponse(r.getId(), r.getMessage(), r.getUser().getUsername(),
				r.getDate(), r.isStatus());
		return rp;
	}

}
