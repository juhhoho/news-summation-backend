package _2024.winter.newssummation.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;


    @Builder
    public User(String username, String password, String email, String phone, String name, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.role = role;
    }
}
