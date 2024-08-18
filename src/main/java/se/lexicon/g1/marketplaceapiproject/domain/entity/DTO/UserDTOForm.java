package se.lexicon.g1.marketplaceapiproject.domain.entity.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDTOForm {
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")

    private String email;

    @NotNull(message = "Password must be included")
    @Size(min = 8, max = 100, message = "Password must include at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character")

    //one uppercase, one lowercase, one number, one special character
    private String password;

}
