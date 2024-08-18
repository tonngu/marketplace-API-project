package se.lexicon.g1.marketplaceapiproject.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g1.marketplaceapiproject.domain.entity.Advertisement;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.AdvertisementDTOForm;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.UserDTOForm;
import se.lexicon.g1.marketplaceapiproject.domain.entity.User;
import se.lexicon.g1.marketplaceapiproject.repository.AdvertisementRepository;
import se.lexicon.g1.marketplaceapiproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public UserService(UserRepository userRepository, AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Transactional
    public void registerUserAndAd(UserDTOForm userDTOForm, AdvertisementDTOForm adDTOForm) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTOForm.getEmail());

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (!user.getPassword().equals(userDTOForm.getPassword())) {
                throw new IllegalArgumentException("Invalid password.");
            }
        } else {
            user = User.builder()
                    .email(userDTOForm.getEmail())
                    .password(userDTOForm.getPassword())
                    //TODO: Add password hashing later if have time
                    //TODO: trying to implement the password encoder using Spring Security adds some kind of security mode
                    //TODO: which I don't understand how to deal with and it stops any kind of request from Postman to the API
                    //TODO: so we'll do without password hashing for now
                    .build();
            user = userRepository.save(user);
        }

        Advertisement advertisement = Advertisement.builder()
                .title(adDTOForm.getTitle())
                .description(adDTOForm.getDescription())
                .expiryDate(adDTOForm.getExpiryDate())
                .user(user)
                .build();

        advertisementRepository.save(advertisement);
    }

    public List<AdvertisementDTOForm> getUserAdvertisements(UserDTOForm userDTOForm) {
        User user = userRepository.findByEmail(userDTOForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!user.getPassword().equals(userDTOForm.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        List<Advertisement> advertisements = advertisementRepository.findByUser_Id(user.getId());

        return advertisements.stream()
                .map(ad -> AdvertisementDTOForm.builder()
                        .title(ad.getTitle())
                        .description(ad.getDescription())
                        .expiryDate(ad.getExpiryDate())
                        .build())
                .collect(Collectors.toList());
    }
}
