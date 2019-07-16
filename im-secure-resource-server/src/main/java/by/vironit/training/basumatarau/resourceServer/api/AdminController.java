package by.vironit.training.basumatarau.resourceServer.api;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.service.ContactEntryService;
import by.vironit.training.basumatarau.messengerService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactEntryService contactEntryService;

    @GetMapping(value = "/user{id}/contacts", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ContactEntryVo>
        getContactEntriesFroUser(Pageable pageable,
                                 @PathVariable Long id) {
        final UserProfileDto user = userService.getUserById(id);
        return contactEntryService.getContactEntriesForUser(user, pageable);
    }

    @DeleteMapping(value = "/contact{contactId}", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void
        deleteContact(@PathVariable Long contactId) {
        contactEntryService.removeContactEntryById(contactId);
    }

}
