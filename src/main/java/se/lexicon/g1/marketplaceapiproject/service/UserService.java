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
import se.lexicon.g1.marketplaceapiproject.util.CustomPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AdvertisementRepository advertisementRepository, CustomPasswordEncoder customPasswordEncoder) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Transactional
    public void registerUserAndAd(UserDTOForm userDTOForm, AdvertisementDTOForm adDTOForm) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTOForm.getEmail());

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (!customPasswordEncoder.matches(userDTOForm.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid password.");
            }
        } else {
            user = User.builder()
                    .email(userDTOForm.getEmail())
                    .password(customPasswordEncoder.encode(userDTOForm.getPassword()))
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

        if (!customPasswordEncoder.matches(userDTOForm.getPassword(), user.getPassword())) {
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
