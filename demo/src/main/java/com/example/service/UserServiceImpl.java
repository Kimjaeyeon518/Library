package com.example.service;

import com.example.domain.User;
import com.example.domain.UserRepository;
import com.example.exception.DuplicatedException;
import com.example.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final boolean DELETE_FAIL = false;
    private final boolean DELETE_SUCCESS = true;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public User save(User user) {
        System.out.println(user.getEmail());
        System.out.println(userRepository.findByEmail(user.getEmail()));
        System.out.println(userRepository.findByEmail(user.getEmail()).isPresent());
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new DuplicatedException("Email is already used :" + user.getEmail());

        return userRepository.save(user);
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cannot find user by Id : " + id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new DuplicatedException("Email is aready used : " + user.getEmail());

        if(!userRepository.findById(user.getId()).isPresent())
            throw new NotFoundException("Cannot find user by Id : " + user.getId());

        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        if(!userRepository.findById(id).isPresent())
            throw new NotFoundException("Cannot find user by Id : " + id);

        userRepository.deleteById(id);

        if(userRepository.findById(id).isPresent())
            return DELETE_FAIL;
        else
            return DELETE_SUCCESS;
    }
}
