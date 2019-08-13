package by.vironit.training.basumatarau.messenger.service.impl;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.SearchCriteriaDto;
import by.vironit.training.basumatarau.messenger.dto.UserAccountRegistrationDto;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.exception.NoEntityFound;
import by.vironit.training.basumatarau.messenger.exception.UserAccountOccupied;
import by.vironit.training.basumatarau.messenger.model.ContactEntry;
import by.vironit.training.basumatarau.messenger.model.User;
import by.vironit.training.basumatarau.messenger.repository.UserRepository;
import by.vironit.training.basumatarau.messenger.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<UserProfileDto> searchForUsers(
            Pageable pageable,
            SearchCriteriaDto criteriaDto) {

        final Page<User> users = userRepository.findAll(
                modelMapper.<Specification<User>>map(
                        criteriaDto,
                        new TypeToken<Specification<User>>(){}.getType()
                ),
                pageable);

        final List<UserProfileDto> content =
                users.getContent()
                        .stream()
                        .map(user -> modelMapper.map(user, UserProfileDto.class))
                        .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, users.getTotalElements());
    }

    @Override
    public UserProfileDto getUserById(Long id) {
        final User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoEntityFound("user not found"));
        return toUserProfileDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileByUserEmail(String email) {
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
            throw new UserAccountOccupied(
                    "account with email: " + accountDetails.getEmail() + " is already occupied");
        }
        final User newUser = toUser(accountDetails);
        userRepository.save(newUser);
    }

    private UserProfileDto toUserProfileDto(User user) {
        return modelMapper.map(user, UserProfileDto.class);
    }

    private Set<ContactEntryVo> toContactEntryVoSet(
            Set<ContactEntry> contactEntries){
        return modelMapper.map(contactEntries, new TypeToken<Set<ContactEntryVo>>(){}.getType());
    }

    private User toUser(UserAccountRegistrationDto accountDto){
        return modelMapper.map(accountDto, User.class);
    }
}
