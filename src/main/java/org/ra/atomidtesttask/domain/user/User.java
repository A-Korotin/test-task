package org.ra.atomidtesttask.domain.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "usrs")
@Getter
@Setter
@EqualsAndHashCode(of = {"username"})
public class User {
    @Id
    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ToString.Exclude
    private String password;
}
