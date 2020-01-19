package com.challenge.game.rest.response;

import com.challenge.game.model.Score;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(Include.NON_NULL)
public class GameMoveResponse {

    private String result;
    private String computerHand;
    private Score score;
}
