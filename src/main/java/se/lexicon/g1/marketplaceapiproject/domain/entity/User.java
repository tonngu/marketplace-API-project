package se.lexicon.g1.marketplaceapiproject.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Advertisement> advertisements = new ArrayList<>();

    public void addAdvertisement(Advertisement advertisement) {
        if (advertisement == null) {
            throw new IllegalArgumentException("Advertisement cannot be null");
        }
        advertisement.setUser(this);
        advertisements.add(advertisement);
    }
}
