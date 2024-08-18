package se.lexicon.g1.marketplaceapiproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g1.marketplaceapiproject.domain.entity.Advertisement;
import se.lexicon.g1.marketplaceapiproject.domain.entity.DTO.AdvertisementDTOForm;
import se.lexicon.g1.marketplaceapiproject.repository.AdvertisementRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<AdvertisementDTOForm> getAllNonExpiredAdvertisements() {
        LocalDate today = LocalDate.now();
        List<Advertisement> advertisements = advertisementRepository.findByExpiryDateAfter(today);

        return advertisements.stream()
                .map(ad -> AdvertisementDTOForm.builder()
                        .title(ad.getTitle())
                        .description(ad.getDescription())
                        .expiryDate(ad.getExpiryDate())
                        .build())
                .collect(Collectors.toList());
    }
}
