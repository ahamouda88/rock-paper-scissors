package com.challenge.game.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.game.application.MainApplication;
import com.challenge.game.model.GameResult;
import com.challenge.game.model.Score;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@ComponentScan(basePackages = "com.challenge.game.service")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class GameScoreServiceTest {

    @Autowired
    private GameScoreService service;

    @Test
    public void testGetGameScore_withoutUserAndWinAndLostCases_returnTotalGameScore() {
        // Human wins this round
        GameResult result = GameResult.WON;
        Score score = service.getGameScore(null, result);
        assertEquals(1, score.getHumanPoints());
        assertEquals(0, score.getMachinePoints());

        // Human wins again this round
        Score anotherHandScore = service.getGameScore(null, result);
        assertEquals(2, anotherHandScore.getHumanPoints());
        assertEquals(0, anotherHandScore.getMachinePoints());

        // Human loses this round
        Score lostHand = service.getGameScore(null, GameResult.LOST);
        assertEquals(2, lostHand.getHumanPoints());
        assertEquals(1, lostHand.getMachinePoints());
    }

    @Test
    public void testGetGameScore_withoutUserAndDrawCase_returnZeroZeroScore() {
        GameResult result = GameResult.DRAW;
        Score score = service.getGameScore(null, result);
        assertEquals(0, score.getHumanPoints());
        assertEquals(0, score.getMachinePoints());
    }

    @Test
    public void testGetGameScore_withMultiUsersAndWinAndLostCases_returnScorePerUser() {
        String user1 = "user1";
        String user2 = "user2";
        Score user1Score = service.getGameScore(user1, GameResult.WON);
        assertEquals(1, user1Score.getHumanPoints());
        assertEquals(0, user1Score.getMachinePoints());

        Score user2Score = service.getGameScore(user2, GameResult.LOST);
        assertEquals(0, user2Score.getHumanPoints());
        assertEquals(1, user2Score.getMachinePoints());

        // Get total score
        Score totalScore = service.getGameScore(null, GameResult.DRAW);
        assertEquals(1, totalScore.getHumanPoints());
        assertEquals(1, totalScore.getMachinePoints());
    }

}
