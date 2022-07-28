package web.tn.drobee.service;

import java.util.List;

import web.tn.drobee.entity.Offer;


public interface IOfferService {
	 List<Offer> Listoffers(); 
	 Offer Addoffer(Offer offer); 
	 void Deleteoffer(Long id); 
	 Offer Updateoffer(Offer a); 
	 Offer getofferbyid(Long id);

}
