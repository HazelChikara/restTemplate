package com.example.FulRest8.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

//    Reference the service
    private RegistrationService registrationService;
    //Add post mapping so that it will show when it hit "api/v1/registration"
    @PostMapping
//    method to register
        public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
    @GetMapping(path = "confirm")
        public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
