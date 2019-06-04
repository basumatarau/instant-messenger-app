package by.vironit.training.basumatarau.instantMessengerApp.controller;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.ContactRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.PrivateMessageRepository;
import by.vironit.training.basumatarau.instantMessengerApp.repository.RoleRepsitory;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {

    @Autowired
    private PrivateMessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RoleRepsitory roleRepsitory;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/test")
    public String userListing(Model model){
        User user = new User();
        user.setActive(true);
        user.setEmail("email@email.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setNickName("test");
        user.setPasswordHash("test");
        user.setRole(roleRepsitory.findById(1).get());
        userRepository.save(user);
        return "userListingTest";
    }
}
