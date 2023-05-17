package users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Entity
@Table(name = "users")
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString

public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "login")
    String login;

    @Column(name = "email")
    String email;

}
