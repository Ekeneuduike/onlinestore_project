package com.ekene.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class RegistrationController {
 private final RegistrationService registrationService;
    @PostMapping("register")
    public String Register(@RequestBody RegistrationRequest request){

        return registrationService.register(request);
 }
 //http://localhost:8080/api/user?token=
     @GetMapping("/confirm")
     public String confirmUser(@RequestParam String token){
        return registrationService.confirmUser(token);
 }

}
