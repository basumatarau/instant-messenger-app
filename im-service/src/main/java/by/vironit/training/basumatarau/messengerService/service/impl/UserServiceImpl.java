package by.vironit.training.basumatarau.messengerService.service.impl;

import by.vironit.training.basumatarau.messengerService.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messengerService.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messengerService.dto.UserProfileDto;
import by.vironit.training.basumatarau.messengerService.exception.NoEntityFound;
import by.vironit.training.basumatarau.messengerService.exception.UserAccountOccupied;
import by.vironit.training.basumatarau.messengerService.model.ContactEntry;
import by.vironit.training.basumatarau.messengerService.model.Role;
import by.vironit.training.basumatarau.messengerService.model.User;
import by.vironit.training.basumatarau.messengerService.repository.RoleRepository;
import by.vironit.training.basumatarau.messengerService.repository.UserRepository;
import by.vironit.training.basumatarau.messengerService.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepsitory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileDtoByUserEmail(String email) {
        final User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("no user found"));

        return toUserProfileDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ContactEntryVo> getUserContactEntriesByUserEmail(String email) {
        final User userWithContactEntries = userRepository
                .findUserWithContactEntriesByEmail(email)
                .orElseThrow(() -> new NoEntityFound("no user found"));

        return toContactEntryVoSet(userWithContactEntries.getContactEntries());
    }

    @Override
    @Transactional
    public void registerNewUserAccount(UserAccountRegistrationDto accountDetails) {
        if (userRepository.findByEmail(accountDetails.getEmail()).isPresent()) {
            throw new UserAccountOccupied("account with email: " + accountDetails.getEmail() + " is already occupied");
        }
        final Role defaultRole = roleRepsitory.findByName("USER").orElseThrow(() -> new EntityNotFoundException("role not found"));

        final User newUserAccount = new User.UserBuilder()
                .email(accountDetails.getEmail())
                .passwordHash(passwordEncoder.encode(accountDetails.getRawPassword()))
                .enabled(true)
                .role(defaultRole)
                .nickName(accountDetails.getNickName())
                .firstName(accountDetails.getFirstName())
                .lastName(accountDetails.getLastName())
                .build();
        userRepository.save(newUserAccount);
    }

    private UserProfileDto toUserProfileDto(
            by.vironit.training.basumatarau.messengerService.model.User user) {
        return modelMapper.map(user, UserProfileDto.class);
    }

    private Set<ContactEntryVo> toContactEntryVoSet(
            Set<ContactEntry> contactEntries){
        return modelMapper.map(contactEntries, new TypeToken<Set<ContactEntryVo>>(){}.getType());
    }
}
