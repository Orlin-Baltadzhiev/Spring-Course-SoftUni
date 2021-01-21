package com.mappingexercise.softunigame.Service;


import com.mappingexercise.softunigame.domain.dtos.AddGameDto;
import com.mappingexercise.softunigame.domain.dtos.DeleteGameDto;
import com.mappingexercise.softunigame.domain.dtos.EditGameDto;
import com.mappingexercise.softunigame.domain.dtos.UserDto;
import com.mappingexercise.softunigame.domain.entities.Game;
import com.mappingexercise.softunigame.domain.entities.Role;
import com.mappingexercise.softunigame.repositories.GameRepository;
import com.mappingexercise.softunigame.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class   GameServiceImpl implements GameService {

    private final ValidatorUtil validatorUtil;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private UserDto userDto;

    public GameServiceImpl(ValidatorUtil validatorUtil, GameRepository gameRepository, ModelMapper modelMapper) {
        this.validatorUtil = validatorUtil;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public String addGame(AddGameDto addgameDto) {
      StringBuilder sb = new StringBuilder();


      if(this.userDto==null || this.userDto.getRole().equals(Role.USER) ){
            sb.append("Invalid logged in user");
      }
      else if(this.validatorUtil.isValid(addgameDto)){
            Game game = this.modelMapper.map(addgameDto, Game.class);
            this.gameRepository.saveAndFlush(game);
            sb.append(String.format("Added %s",game.getTitle()));
      } else {
          this.validatorUtil.violations(addgameDto)
                  .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));
      }

        return sb.toString();
    }

    @Override
    public void setLoggedUser(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String deleteGame(DeleteGameDto deleteGameDto) {
        StringBuilder sb = new StringBuilder();


        if(this.userDto==null || this.userDto.getRole().equals(Role.USER) ){
            sb.append("Invalid logged in user");
        } else {
            Optional<Game> game = this.gameRepository.findById(deleteGameDto.getId());
            if(game.isPresent()){
                this.gameRepository.delete(game.get());
                sb.append(String.format("Game %s was delete", game.get().getTitle()));
            } else {
                sb.append("Cannot find game");
            }

        }


            return sb.toString();
    }

    @Override
    public String editGame(EditGameDto editGameDto) {
        StringBuilder sb = new StringBuilder();
       Optional<Game> gamee = this.gameRepository.findById(editGameDto.getId());
        if(gamee.isPresent()){
        this.gameRepository.updatePriceInGame(editGameDto.getPrice(),editGameDto.getSize(),editGameDto.getId());
            sb.append(String.format("Edited %s", gamee.get().getTitle()));

        } else {
            sb.append("There is no such game in repository !").append(System.lineSeparator());
        }

        return sb.toString();
    }
}
