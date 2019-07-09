package by.vironit.training.basumatarau.instantMessengerApp.api;

import by.vironit.training.basumatarau.instantMessengerApp.dto.ContactEntryVo;
import by.vironit.training.basumatarau.instantMessengerApp.dto.UserProfileDto;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserEndPoint {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserProfileDto> signin(Principal principal){
        return ResponseEntity.ok(
                userService.getUserProfileDtoByUserEmail(principal.getName())
        );
    }

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<UserProfileDto> getUserInfo(Principal principal){
        return ResponseEntity.ok(
                userService.getUserProfileDtoByUserEmail(principal.getName())
        );
    }

    @GetMapping("/contactEntries")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<Set<ContactEntryVo>> getContactEntries(Principal principal){
        return ResponseEntity.ok(userService.getUserContactEntriesByUserEmail(principal.getName()));
    }

}

