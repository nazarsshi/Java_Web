package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"participatedTasks", "createdTasks"})
@Entity
@Table(name = "volunteer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "about_user")
    private String aboutUser;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "trust_level")
    private TrustLevel trustLevel;

    @Column(name = "is_blocked")
    private boolean blocked;

    @Column(name = "api_key")
    private String apiKey;

    @OneToMany(mappedBy = "creator")
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserTask> participatedTasks = new HashSet<>();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        User user = (User) other;
        return id == user.id
                && blocked == user.blocked
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(email, user.email)
                && Objects.equals(photo, user.photo)
                && Objects.equals(password, user.password)
                && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(aboutUser, user.aboutUser)
                && Objects.equals(dateOfBirth, user.dateOfBirth)
                && trustLevel == user.trustLevel
                && Objects.equals(apiKey, user.apiKey)
                && Objects.equals(createdTasks, user.createdTasks)
                && Objects.equals(participatedTasks, user.participatedTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}