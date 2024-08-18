package se.lexicon.g1.marketplaceapiproject.domain.entity.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegistrationRequest {

    @NotNull
    private UserDTOForm userDTOForm;

    @NotNull
    private AdvertisementDTOForm advertisementDTOForm;

}
