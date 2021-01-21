package com.mappingexercise.softunigame.Service;

import com.mappingexercise.softunigame.domain.dtos.UserDto;
import com.mappingexercise.softunigame.domain.dtos.UserLoginDto;
import com.mappingexercise.softunigame.domain.dtos.UserRegisterDto;
import com.mappingexercise.softunigame.domain.entities.Role;
import com.mappingexercise.softunigame.domain.entities.User;
import com.mappingexercise.softunigame.repositories.UserRepository;
import com.mappingexercise.softunigame.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final GameService gameService;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private UserDto loggedUser;

    @Autowired
    public UserServiceImp(GameService gameService, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.gameService = gameService;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

    }


    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            sb.append("Password don't match.");
        } else if (this.validatorUtil.isValid(userRegisterDto)) {

            User user = this.modelMapper.map(userRegisterDto, User.class);

            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
                System.out.println(user.getRole());
            } else {
                user.setRole(Role.USER);
                System.out.println(user.getRole());
            }
            sb.append(String.format("%s was registered", userRegisterDto.getFullName()));
            this.userRepository.saveAndFlush(user);
            //TODO String

        } else {
            this.validatorUtil.violations(userRegisterDto)
                    .forEach(e -> sb.append(String.format("%s%n", e.getMessage())));

        }
        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDto loginDto) {
        StringBuilder sb = new StringBuilder();
        Optional<User> user = this.userRepository
                .findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user.isPresent()) {
            if (this.loggedUser != null) {
                sb.append("User is already logged in.");
            } else {

                loggedUser = this.modelMapper.map(user.get(), UserDto.class);

                this.modelMapper.validate();
                this.gameService.setLoggedUser(this.loggedUser);
                sb.append(String.format("Successfully logged in %s", user.get().getFullName()));
            }
        } else {
            sb.append("Incorrect email/ password");
        }

        return sb.toString();
    }

    @Override
    public String logOut() {
        if (this.loggedUser == null) {
            return "Cannot log out. No user was logged in.";
        } else {
            String message = String.format("User %s successfully logged out",
                    this.loggedUser.getFullName());
            this.loggedUser = null;
            return message;
        }
    }
}
