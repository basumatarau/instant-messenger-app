package by.vironit.training.basumatarau.instantMessengerApp.service.impl;

import by.vironit.training.basumatarau.instantMessengerApp.model.User;
import by.vironit.training.basumatarau.instantMessengerApp.repository.UserRepository;
import by.vironit.training.basumatarau.instantMessengerApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
