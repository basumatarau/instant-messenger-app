package by.vironit.training.basumatarau.instantMessengerApp.api;

import by.vironit.training.basumatarau.instantMessengerApp.dto.UserProfileDto;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class User {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/api/user")
    public ResponseEntity<UserProfileDto> test(Principal principal){
        final Optional<by.vironit.training.basumatarau.instantMessengerApp.model.User> theUser
                = userRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(toUserProfileDto(theUser.orElseThrow(()->new RuntimeException("fail"))));
    }

    private UserProfileDto toUserProfileDto(by.vironit.training.basumatarau.instantMessengerApp.model.User user) {
        return modelMapper.map(user, UserProfileDto.class);
    }
}

