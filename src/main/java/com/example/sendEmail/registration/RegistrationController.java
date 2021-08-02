package com.example.sendEmail.registration;

import com.example.sendEmail.exceptions.ServiceException;
import com.example.sendEmail.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) throws ServiceException {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) throws ServiceException {
        return registrationService.confirmToken(token);
    }
}
