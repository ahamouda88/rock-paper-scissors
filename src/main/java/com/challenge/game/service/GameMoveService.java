package com.challenge.game.service;

import static com.challenge.game.model.Hand.PAPER;
import static com.challenge.game.model.Hand.ROCK;
import static com.challenge.game.model.Hand.SCISSORS;
import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.game.model.GameResult;
import com.challenge.game.model.Hand;
import com.challenge.game.model.Score;
import com.challenge.game.rest.request.GameMoveRequest;
import com.challenge.game.rest.response.GameMoveResponse;
import com.challenge.game.rest.response.GameMoveResponse.GameMoveResponseBuilder;
import com.google.common.annotations.VisibleForTesting;

@Service
public class GameMoveService {
    
    @Autowired
    private GameScoreService scoreService;

    // This maps a hand with the set of hands thats are stronger it
    private Map<Hand, Set<Hand>> strongerMap = of(PAPER, newHashSet(SCISSORS),
                                                  SCISSORS, newHashSet(ROCK),
                                                  ROCK, newHashSet(PAPER));

    public GameMoveResponse createGameMoveResponse(GameMoveRequest request) {
        GameMoveResponseBuilder responseBuilder = GameMoveResponse.builder();
        Hand inputHand = EnumUtils.getEnum(Hand.class, request.getHand());
        if (inputHand == null) {
            responseBuilder.result(GameResult.UNKNOWN.name());
            return responseBuilder.build();
        }

        Hand computerHand = Hand.getRandomHand();
        GameResult result = getGameResult(inputHand, computerHand);
        Score score = scoreService.getGameScore(request.getUserId(), result);

        return responseBuilder.computerHand(computerHand.name())
                              .result(result.name())
                              .score(score)
                              .build();
    }

    @VisibleForTesting
    GameResult getGameResult(Hand inputHand, Hand computerHand) {
        if (inputHand.equals(computerHand)) {
            return GameResult.DRAW;
        }

        Set<Hand> strongerHands = strongerMap.get(inputHand);
        return strongerHands.contains(computerHand) ? GameResult.LOST : GameResult.WON;
    }

}
