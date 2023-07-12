package com.example.p2pVoIP.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        ONLINE,
        OFFLINE
    }

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    ) private Set<Role> roles = new HashSet<>();
//    public Set<GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//        }
//        return authorities;
//    }

}
