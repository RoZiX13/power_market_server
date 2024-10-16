package roz.power.market.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import roz.power.market.crud.repository.services.mongo.EmailService;
import roz.power.market.crud.repository.services.postgres.UserService;
import roz.power.market.dto.emailsDTO.EmailDTO;
import roz.power.market.dto.peopleDTO.AuthorizationDTO;
import roz.power.market.securiry.UserDetails;
import roz.power.market.utils.converters.EmailConverter;
import roz.power.market.utils.converters.UserConverter;
import roz.power.market.utils.jwt.AuthenticateUser;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private EmailConverter emailConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticateUser<String> authenticateUser;

    @PostMapping()
    public boolean userIsAuth(Map<String, String> map){
        Optional<Authentication> authenticationOptional = authenticateUser.authenticateUser(map.get("token"));
        return authenticationOptional.isPresent();
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthorizationDTO auth){
        System.out.println(auth.getLogin());
        System.out.println(auth.getPassword());
        Map<String, String> map = userService.authorization(auth.getLogin(), auth.getPassword());
        System.out.println(map.get("token"));
        return map;
    }

    @PostMapping("/ver")
    public HttpStatus verification(@RequestBody EmailDTO emailDTO){
        System.out.println(emailDTO.getMessage());
        emailService.verification(emailConverter.convertToEmailFromEmailDTO(emailDTO));
        return HttpStatus.OK;
    }

    @GetMapping("/message")
    public HttpStatus sendMessage(@RequestParam(value = "login",required = false) String email,
                                  @RequestParam(value = "code",required = false) String code) {
        emailService.findEmailByEmailAndCode(email, code);
        return HttpStatus.OK;
    }

}
