package by.vironit.training.basumatarau.messenger.api;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.service.ContactService;
import by.vironit.training.basumatarau.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactEntryService;

    @GetMapping(value = "/user{id}/contacts", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ContactEntryVo>
        getContactEntriesFroUser(@Valid
                                 @PageableDefault(page = 0, size = 20)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                 }) Pageable pageable,
                                 @PathVariable("id") Long id) {
        final UserProfileDto user = userService.getUserById(++id);
        return contactEntryService.getContactEntriesForUser(user, pageable);
    }

    @DeleteMapping(value = "/contact{contactId}", produces = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void
        deleteContact(@PathVariable("contactId") Long contactId) {
        contactEntryService.removeContactEntryById(contactId);
    }

}
