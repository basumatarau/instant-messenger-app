package by.vironit.training.basumatarau.messenger.api;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.model.Contact;
import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.service.ContactEntryService;
import by.vironit.training.basumatarau.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactEntryService contactEntryService;

    @GetMapping(value = {"/login", "/me"})
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDto getUserInfo(Principal principal) {
        return userService.getUserProfileByUserEmail(principal.getName());
    }

    @PostMapping(value = "/signup", consumes = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody UserAccountRegistrationDto newAccount,
            Principal principal) {
        userService.registerNewUserAccount(newAccount);
    }

    @GetMapping(value = "/contacts", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public Page<ContactEntryVo>
        getUserContactEntries(Principal principal,
                              @Valid
                              @PageableDefault(page = 0, size = 20)
                              @SortDefault.SortDefaults({
                                      @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                              }) Pageable pageable) {
        final UserProfileDto currentUser =
                userService.getUserProfileByUserEmail(principal.getName());

        return contactEntryService.getContactEntriesForUser(currentUser, pageable);
    }

    @GetMapping(value = "/contacts/pending", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public Page<ContactEntryVo>
    getPendingUserContacts(Principal principal,
                          @Valid
                          @PageableDefault(page = 0, size = 20)
                          @SortDefault.SortDefaults({
                                  @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                          }) Pageable pageable) {
        final UserProfileDto currentUser =
                userService.getUserProfileByUserEmail(principal.getName());

        return contactEntryService.getPendingContactsForUser(currentUser, pageable);
    }

    @GetMapping(value = "/contacts/search", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public Page<ContactEntryVo>
        searchContactEntries(Principal principal,
                          @Valid
                          @PageableDefault(page = 0, size = 20)
                          @SortDefault.SortDefaults({
                                  @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                          }) Pageable pageable) {
        final UserProfileDto currentUser =
                userService.getUserProfileByUserEmail(principal.getName());

        //todo
        return null;
    }

    @DeleteMapping(value = "/contact{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<String>
        deleteContact(Authentication auth, @PathVariable("id") Long id) {

        final ContactEntry contact =
                contactEntryService
                        .findContactEntryById(id)
                        .orElseThrow(() -> new NoEntityFound("contact not found"));

        final UserDetails principal = (UserDetails) auth.getPrincipal();

        if (contact.getOwner().getEmail().equals(principal.getUsername())) {
            contactEntryService.removeContactEntryById(contact.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/contact{id}/request")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void
        sendContactRequest(Principal principal, @PathVariable("id") Long id)
            throws InstantiationException {

        final String username = principal.getName();
        final UserProfileDto currentUserProfile = userService.getUserProfileByUserEmail(username);
        final UserProfileDto person = userService.getUserById(id);
        contactEntryService.sendContactRequest(currentUserProfile, person);
    }

    @PutMapping(value = "/contact{id}/confirm")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<String>
        confirmContactRequest(Principal principal, @PathVariable("id") Long id)
            throws InstantiationException {

        final Contact contact = contactEntryService
                .findContactById(id)
                .orElseThrow(() -> new NoEntityFound("no contact found"));

        final String currentUserName = principal.getName();
        if (!contact.getPerson().getEmail().equals(currentUserName)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        contactEntryService.confirmContactRequest(contact);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/contact{id}/decline")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<String>
        declineContactRequest(Authentication auth, @PathVariable("id") Long id)
            throws InstantiationException {

        final Contact contact = contactEntryService
                .findContactById(id)
                .orElseThrow(() -> new NoEntityFound("no contact found"));

        final String currentUserName = ((UserDetails) auth.getPrincipal()).getUsername();
        if (!contact.getPerson().getEmail().equals(currentUserName)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        contactEntryService.declineContactRequest(contact);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

