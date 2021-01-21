package com.mappingexercise.softunigame.Service;

import com.mappingexercise.softunigame.domain.dtos.UserLoginDto;
import com.mappingexercise.softunigame.domain.dtos.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto loginDto);

    String logOut();
}
