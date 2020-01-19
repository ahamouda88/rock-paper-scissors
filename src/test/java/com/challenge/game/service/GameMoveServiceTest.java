package com.challenge.game.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.game.application.MainApplication;
import com.challenge.game.model.GameResult;
import com.challenge.game.model.Hand;
import com.challenge.game.rest.response.GameMoveResponse;
import com.challenge.game.utils.GameMoveTestUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@ComponentScan(basePackages = "com.challenge.game.service")
public class GameMoveServiceTest {

    @Autowired
    private GameMoveService service;

    @Test
    public void testGetGameResult_givenAWinCase_returnWon() {
        GameResult result = service.getGameResult(Hand.ROCK, Hand.SCISSORS);
        assertEquals(GameResult.WON, result);
    }

    @Test
    public void testGetGameResult_givenALossCase_returnLost() {
        GameResult result = service.getGameResult(Hand.ROCK, Hand.PAPER);
        assertEquals(GameResult.LOST, result);
    }

    @Test
    public void testGetGameResult_givenADrawCase_returnDraw() {
        GameResult result = service.getGameResult(Hand.ROCK, Hand.ROCK);
        assertEquals(GameResult.DRAW, result);
    }

    @Test
    public void testCreateGameMoveResponse_givenValidMove_returnResponse() {
        GameMoveResponse response = service.createGameMoveResponse(GameMoveTestUtils.createRequest(null, Hand.ROCK));

        assertNotNull(Hand.valueOf(response.getComputerHand()));
        assertNotNull(response.getScore());
        assertNotNull(GameResult.valueOf(response.getResult()));
    }

    @Test
    public void testCreateGameMoveResponse_givenInvalidMove_returnUnknownResult() {
        GameMoveResponse response = service.createGameMoveResponse(
                GameMoveTestUtils.createRequest(null, "unknownMove"));

        assertEquals(GameResult.UNKNOWN, GameResult.valueOf(response.getResult()));
        assertNull(response.getScore());
        assertNull(response.getComputerHand());
    }
}
