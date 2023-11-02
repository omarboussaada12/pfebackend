package web.tn.drobee.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.Offer;
import web.tn.drobee.payload.response.MessageResponse;
import web.tn.drobee.payload.response.OfferResponse;
import web.tn.drobee.repo.OfferRepository;
import web.tn.drobee.service.IOfferService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OfferController {
	@Autowired
	IOfferService offerService;
	@Autowired
	OfferRepository offerRepository;
    
	//fetching a list of offer 
	@GetMapping("/get-all-offer")
	@ResponseBody
	public List<OfferResponse> getOffers() {
		List<OfferResponse> list = offerService.Listoffers();
		return list;
	}

	// fetch a list of all the offers name
	@GetMapping("/get-all-offer-names")
	@ResponseBody
	public List<String> getOffersname() {
		List<String> list = offerService.Listoffername();
		return list;
	}
	
	// fetch an offer based on ID
	@GetMapping("/get-Offer/{offer-id}")
	@ResponseBody
	public OfferResponse getOffer(@PathVariable("offer-id") Long OfferId) {
		return offerService.getofferbyid(OfferId);
	}
	//fetchofferbyname
	@GetMapping("/get-Offerbyname/{offer-name}")
	@ResponseBody
	public OfferResponse getOffer(@PathVariable("offer-name") String offername) {
		Offer of = offerService.getbyname(offername);
		return   offerService.converttoOR(of);
	}

	// add a new offer without image
	@PostMapping("/add-Offer")
	@ResponseBody
	public ResponseEntity<?> addOffer(@Valid @RequestBody Offer a) {
		if (offerRepository.existsByname(a.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name is already taken!"));
		}
		if (a.getDescription().length()>255) {
			return ResponseEntity.badRequest().body(new MessageResponse("Description need to be less then 255 "));
		}
		if(offerService.Addoffer(a))
		{
			return ResponseEntity.ok(new MessageResponse("offer add successfully!"));
		}else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("something went wrong please try later"));
		}
		
	}

	
	//add image to an exsisted offer and delete the old image 
	@PutMapping("/offer-image/{offer-name}")
	@ResponseBody
	public void affecterimagetooffer(@Valid @PathVariable("offer-name") String offername,
			@RequestParam("file") MultipartFile file) {

		offerService.Addimageoffer(offername, file);
	}
    // need more work
	@DeleteMapping("/delete-Offer/{offer-id}")
	@ResponseBody
	public void deleteOffer(@PathVariable("offer-id") Long OfferId) {
		offerService.Deleteoffer(OfferId);
	}

	//need more work
	@PutMapping("/update-offer/{offer-id}")
	@ResponseBody
	public ResponseEntity<?> updateOffer(@PathVariable("offer-id") Long OfferId,@Valid @RequestBody Offer offer) {
		if (offerRepository.findById(OfferId) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name is already taken!"));
		}
		
		offerService.Updateoffer(OfferId,offer);
		return ResponseEntity.ok(new MessageResponse("offer updated successfully!"));
	}
}
