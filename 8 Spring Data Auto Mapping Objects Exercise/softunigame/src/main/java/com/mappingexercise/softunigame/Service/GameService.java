package com.mappingexercise.softunigame.Service;

import com.mappingexercise.softunigame.domain.dtos.AddGameDto;
import com.mappingexercise.softunigame.domain.dtos.DeleteGameDto;
import com.mappingexercise.softunigame.domain.dtos.EditGameDto;
import com.mappingexercise.softunigame.domain.dtos.UserDto;

public interface GameService {

    String addGame(AddGameDto addgameDto);

    void setLoggedUser(UserDto userDto);

    String deleteGame(DeleteGameDto deleteGameDto);

    String editGame(EditGameDto editGameDto);
}
