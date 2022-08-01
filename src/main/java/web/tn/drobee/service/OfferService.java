package web.tn.drobee.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.tn.drobee.entity.Offer;
import web.tn.drobee.repo.OfferRepository;


@Service
public class OfferService  implements IOfferService{
	
	@Autowired
	OfferRepository offerRepository;
	private static final Logger l = LogManager.getLogger(OfferService.class);
	

	
	public List<Offer> Listoffers() {

		List<Offer> Offers = (List<Offer>) offerRepository.findAll();
		l.info("fetching list d'offer");
		for(Offer ac : Offers)
		{
			l.info("name offer : "+ac.getName());
		}
		return Offers;
		// TODO Auto-generated method stub
		
	}

	
	public Offer Addoffer(Offer a) {
		l.info("Adding Offer with ID: "+a.getId());
		return offerRepository.save(a);
		// TODO Auto-generated method stub
		
	}

	
	public void Deleteoffer (Long id) {
		l.info("Deleting activity with ID: "+id);
		offerRepository.deleteById(id);
		// TODO Auto-generated method stub
		
	}

	
	public Offer Updateoffer(Offer a) {
		l.info("Updating activity with ID: "+a.getId());
		return this.offerRepository.save(a);
		// TODO Auto-generated method stub
		
	}

	
	public Offer getofferbyid( Long id) {
		l.info("Retriving offer with ID: "+id);
		return this.offerRepository.findById(id).get();
		// TODO Auto-generated method stub
		
	}


	@Override
	public Offer getbyname(String name) {
		List<Offer> Offers = (List<Offer>) offerRepository.findAll();
		for(Offer ac : Offers)
		{
			if(ac.getName().equals(name))
			{
				l.info("Retriving offer with ID: "+ ac.getId());
				return ac;
				
			}
		}
		return null;
	}


	
}
