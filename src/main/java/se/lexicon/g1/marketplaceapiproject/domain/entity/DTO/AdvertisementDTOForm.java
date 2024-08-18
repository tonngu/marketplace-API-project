package se.lexicon.g1.marketplaceapiproject.domain.entity.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdvertisementDTOForm {

    @NotNull(message = "Advertisement title cannot be null")
    @Size(min = 1, max = 255)
    private String title;

    @NotNull(message = "Advertisement must include a description")
    @Size(min = 1, max = 1000)
    private String description;

    @NotNull(message = "Advertisement must include an expiry date")
    private LocalDate expiryDate;


}
