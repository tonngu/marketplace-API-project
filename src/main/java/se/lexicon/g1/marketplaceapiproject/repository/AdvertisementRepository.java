package se.lexicon.g1.marketplaceapiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.g1.marketplaceapiproject.domain.entity.Advertisement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByUser_Id(Long id);

    List<Advertisement> findByExpiryDateBefore(LocalDate date);

    List<Advertisement> findByExpiryDateAfter(LocalDate date);
}
