package by.vironit.training.basumatarau.resourceServer.api;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.repository.ContactEntryRepository;
import by.vironit.training.basumatarau.messengerService.service.ContactEntryService;
import by.vironit.training.basumatarau.messengerService.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactEntryService contactEntryService;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public String getUserInfo(Principal principal) throws JsonProcessingException {
        final UserProfileDto userProfile =
                userService.getUserProfileByUserEmail(principal.getName());
        return objectMapper.writeValueAsString(userProfile);
    }

    @GetMapping(value = "/contacts", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public Page<ContactEntryVo> getUserContactEntries(Principal principal, Pageable pageable)
            throws JsonProcessingException {

        final UserProfileDto currentUser =
                userService.getUserProfileByUserEmail(principal.getName());

        return contactEntryService.getContactEntriesForUser(currentUser, pageable);
    }
}

