package web.tn.drobee.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import web.tn.drobee.entity.Offer;
import web.tn.drobee.payload.response.OfferResponse;

public interface IOfferService {
	List<OfferResponse> Listoffers();

	Boolean Addoffer(Offer offer);
	
	Offer Addimageoffer(String offername , MultipartFile file);

	void Deleteoffer(Long id);

	OfferResponse getofferbyid(Long id);

	Offer getbyname(String name);
	
	List<String> Listoffername();

	Offer Updateoffer(long id, Offer a);
	
	OfferResponse converttoOR(Offer of);

}
