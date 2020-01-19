package com.challenge.game.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.game.exception.InvalidInputException;
import com.challenge.game.rest.constant.PathConstant;
import com.challenge.game.rest.request.GameMoveRequest;
import com.challenge.game.rest.response.GameMoveResponse;
import com.challenge.game.service.GameMoveService;

@RestController
@RequestMapping(value = PathConstant.MAIN_PATH)
public class GameController {

    @Autowired
    private GameMoveService service;

    @RequestMapping(method = RequestMethod.POST)
    public GameMoveResponse handleUserGameMoveRequest(@RequestBody GameMoveRequest request) {
        validateRequest(request);

        return service.createGameMoveResponse(request);
    }

    private void validateRequest(GameMoveRequest request) {
        if (StringUtils.isBlank(request.getHand())) {
            throw new InvalidInputException("User input is mandatory");
        }
    }

}
