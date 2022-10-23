package web.tn.drobee.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.entity.User;
import web.tn.drobee.payload.response.OfferResponse;
import web.tn.drobee.payload.response.ResponseFile;
import web.tn.drobee.payload.response.UserResponse;
import web.tn.drobee.repo.FileDBRepository;
import web.tn.drobee.repo.OfferRepository;

@Service
public class OfferService implements IOfferService {

	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	FileStorageService storageService;
	
	@Autowired
	FileDBRepository fileDBRepository ;
	private static final Logger l = LogManager.getLogger(OfferService.class);

	@Override
	public List<OfferResponse> Listoffers() {
		List<OfferResponse> lor = new ArrayList<OfferResponse>();
		List<Offer> Offers = (List<Offer>) offerRepository.findAll();
		l.info("fetching list d'offer");
		for (Offer ac : Offers) {
			OfferResponse or = new OfferResponse();
			l.info("name offer : " + ac.getName());
			FileDB dbFile = storageService.getFile(ac.getImage().getId());
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();
			or.setImage(new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length));
			or.setId(ac.getId());
			or.setName(ac.getName());
			or.setPrixunit(ac.getPrixunit());
			or.setDescription(ac.getDescription());
			lor.add(or);
		}
		return lor;
		// TODO Auto-generated method stub

	}

	

	@Override
	public void Deleteoffer(Long id) {
		l.info("Deleting Offer with ID: " + id);
		
		offerRepository.deleteById(id);
		// TODO Auto-generated method stub

	}

	@Override
	public Offer Updateoffer(Offer a) {
		l.info("Updating Offer with ID: " + a.getId());
		return this.offerRepository.save(a);
		// TODO Auto-generated method stub

	}

	@Override
	public Offer getofferbyid(Long id) {
		l.info("Retriving offer with ID: " + id);
		return this.offerRepository.findById(id).get();

	}

	@Override
	public Offer getbyname(String name) {
		List<Offer> Offers = (List<Offer>) offerRepository.findAll();
		for (Offer ac : Offers) {
			if (ac.getName().equals(name)) {
				l.info("Retriving offer with ID: " + ac.getId());
				return ac;

			}
		}
		return null;
	}

	@Override
	public List<String> Listoffername() {
		List<Offer> Offers = (List<Offer>) offerRepository.findAll();
		List<String> names = new ArrayList<String>() ;
		l.info("fetching list d'offer");
		for (Offer ac : Offers) {
			l.info("name offer : " + ac.getName());
			names.add(ac.getName());
		}
		return names;
	}



	@Override
	public Offer Addoffer(Offer a) {
			a.setImage(null);
			 offerRepository.save(a);
			l.info("Adding Offer with ID: " + a.getId());
			return a;

		}
	@Override
	public Offer Addimageoffer(String offername, MultipartFile file) {
		Offer o = getbyname(offername);
		if( o.getImage()== null)
		{
			try {
				o.setImage(storageService.store(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else
		{
			fileDBRepository.delete(o.getImage());
			try {
				o.setImage(storageService.store(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return offerRepository.save(o);
	}
	}
