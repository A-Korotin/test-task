package org.ra.atomidtesttask.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "usrs",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ToString.Exclude
    private String password;
}
