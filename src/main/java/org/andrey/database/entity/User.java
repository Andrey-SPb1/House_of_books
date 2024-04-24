package org.andrey.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birthDate;

    private String password;

    @NotAudited
    @Default
    @OneToMany(mappedBy = "user")
    private List<BookInFavorites> booksInFavorites = new ArrayList<>();

    @NotAudited
    @Default
    @OneToMany(mappedBy = "user")
    private List<BookInBasket> booksInBasket = new ArrayList<>();

    @NotAudited
    @Default
    @OneToMany(mappedBy = "user")
    private List<PurchaseHistory> purchaseHistories = new ArrayList<>();

}

