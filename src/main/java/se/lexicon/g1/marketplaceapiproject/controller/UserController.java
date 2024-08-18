package se.lexicon.g1.marketplaceapiproject.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.AdvertisementDTOForm;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.RegistrationRequest;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.UserDTOForm;
import se.lexicon.g1.marketplaceapiproject.service.UserService;

import java.util.List;

@RequestMapping("/api/ads")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserAndAd(@RequestBody @Valid RegistrationRequest request) {
        userService.registerUserAndAd(request.getUserDTOForm(), request.getAdvertisementDTOForm());
        return ResponseEntity.ok("User and advertisement registered successfully.");
    }

    @PostMapping("/user_ads")
    public ResponseEntity<List<AdvertisementDTOForm>> getUserAdvertisements(@RequestBody UserDTOForm userDTOForm) {
        List<AdvertisementDTOForm> advertisements = userService.getUserAdvertisements(userDTOForm);
        return ResponseEntity.ok(advertisements);
    }
}
