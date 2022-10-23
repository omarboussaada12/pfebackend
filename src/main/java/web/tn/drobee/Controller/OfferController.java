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

	@GetMapping("/get-all-offer")
	@ResponseBody
	public List<OfferResponse> getOffers() {
		List<OfferResponse> list = offerService.Listoffers();
		return list;
	}

	@GetMapping("/get-all-offer-names")
	@ResponseBody
	public List<String> getOffersname() {
		List<String> list = offerService.Listoffername();
		return list;
	}
	// list of offer name

	@GetMapping("/get-Offer/{offer-id}")
	@ResponseBody
	public Offer getOffer(@PathVariable("offer-id") Long OfferId) {
		return offerService.getofferbyid(OfferId);
	}

	@PostMapping("/add-Offer")
	@ResponseBody
	public ResponseEntity<?> addOffer(@Valid @RequestBody Offer a) {
		if (offerRepository.existsByname(a.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name is already taken!"));
		}

		offerService.Addoffer(a);
		return ResponseEntity.ok(new MessageResponse("offer add successfully!"));
	}

	@PutMapping("/offer-image/{offer-name}")
	@ResponseBody
	public void affecterimagetooffer(@Valid @PathVariable("offer-name") String offername,
			@RequestParam("file") MultipartFile file) {

		offerService.Addimageoffer(offername, file);
	}

	@DeleteMapping("/delete-Offer/{offer-id}")
	@ResponseBody
	public void deleteOffer(@PathVariable("offer-id") Long OfferId) {
		offerService.Deleteoffer(OfferId);
	}

	@PutMapping("/update-offer")
	@ResponseBody
	public ResponseEntity<?> updateOffer(@Valid @RequestBody Offer offer) {
		if (offerRepository.existsByname(offer.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: offer name is already taken!"));
		}
		offerService.Updateoffer(offer);
		return ResponseEntity.ok(new MessageResponse("offer updated successfully!"));
	}
}
