package com.example.FulRest8.appuser;

/*
 This class is responsible for ensuring a user has authority to access certain endpoints and do  certain things depending on their roles.
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

//I used lombok for the getters and setters, it makes life easier that writing methods for every variable in the AppUser Class
@Getter
@Setter
@NoArgsConstructor
// This annotation shows it is a table in the DB
@Entity
public class AppUser implements UserDetails {

//    The id should be a sequence from java persistence
       @Id
       @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )

    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
//    id the user role (added as an enum in the enum class) remember to add the enum type
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
//    to check if the account is locked or enabled it should return True or False
//    Set the default to False as we register the appUser
    private Boolean locked = false;
//    We set to False so that we enable an appUser when the User confirms the email.
    private Boolean enabled = false;

//    This constructor is excluding id because ID is auto generated when a user entity is being created
//    you can press alt + insert for keyboard shortcuts



    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String password,
                   AppUserRole appUserRole
//                   Boolean locked,
//                   Boolean enabled
                  ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.appUserRole = appUserRole;
//        this.locked = locked;
//        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return a simple granted authority with the appUserRoleName
// This means we are changing a person into a user who can log in as an Admin or Normal User and has access to certain APIs
        SimpleGrantedAuthority authority =
//                app user Role.name will grab the user role
                new SimpleGrantedAuthority(appUserRole.name());
//        return the authority collection
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {

        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    public String getLastName() {

        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
//        We not keeping track but you can check if the account is expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        I reversed the flipped the isAccountLocked
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
