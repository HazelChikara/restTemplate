package com.example.FulRest8.appuser;

import com.example.FulRest8.registration.token.ConfirmationToken;
import com.example.FulRest8.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.FulRest8.appuser.AppUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

//This will implement an interface for Spring Security
//You use it to find users once we login
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
//Find User by UserName (email in this case) so we need to query the DB by adding an Repository Interface StudentRepository

//    the response message when user is not found (ideally it should reference to a messages.properties file
    private final static String USER_NOT_FOUND = "User with email %s not found";

//    Reference to the repository
    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    to call the confirmation service when creating a user in DB instead of using the repository directly
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

//    The link they have to confirm
    public String signUpUser(AppUser appUser){
//        check if user exists
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        if(userExist){

        // TODO check of attributes are the same and
        // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("Email already taken!");
        }



        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

//        to save the user
        appUserRepository.save(appUser);

//        Email validation with token
//create a token and save it
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
//                this should have its own config
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

//        save the confirmation service
        confirmationTokenService.saveConfirmationToken(confirmationToken);
// TODO :Send Email
        return token;
    }

    public void enableAppUser(String email) {
    }
}
