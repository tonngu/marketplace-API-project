package se.lexicon.g1.marketplaceapiproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.AdvertisementDTOForm;
import se.lexicon.g1.marketplaceapiproject.service.AdvertisementService;

import java.util.List;

@RequestMapping("/api/ads")
@RestController
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/non-expired")
    public ResponseEntity<List<AdvertisementDTOForm>> getAllNonExpiredAdvertisements() {
        List<AdvertisementDTOForm> advertisements = advertisementService.getAllNonExpiredAdvertisements();
        return ResponseEntity.ok(advertisements);
    }
}
