package com.example.FulRest8.registration.token;

import com.example.FulRest8.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    //    The id should be a sequence from java persistence so create all constructors except id
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )

    private long id;
//    We want to store the actual token and make it required
    @Column(nullable = false)
    private String token;
//    to calculate the time it expires
    @Column(nullable = false)
    private LocalDateTime createdAt;
    //    to calculate the time it expires
    @Column(nullable = false)
    private LocalDateTime expiresAt;


    private LocalDateTime confirmedAt;

//    One user can have many confirmation tokens
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;


    //    to tie the token to its user
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser
                                 ) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }


}
