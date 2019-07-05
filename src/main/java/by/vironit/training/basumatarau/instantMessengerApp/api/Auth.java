package by.vironit.training.basumatarau.instantMessengerApp.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static by.vironit.training.basumatarau.instantMessengerApp.config.security.SecurityConstants.AUTH_ENDPOINT;

@RestController
public class Auth {

    @RequestMapping(value = AUTH_ENDPOINT)
    @ResponseBody
    public ResponseEntity authenticate(Principal principal){
        if(principal != null){
            return ResponseEntity.ok(HttpStatus.OK);
        }else
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
    }
}
