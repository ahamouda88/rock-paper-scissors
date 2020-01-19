package com.challenge.game.service;

import static com.challenge.game.model.GameResult.LOST;
import static com.challenge.game.model.GameResult.WON;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.challenge.game.model.GameResult;
import com.challenge.game.model.Score;
import com.google.common.collect.Maps;

@Service
public class GameScoreService {

    private Map<String, Score> scoreMap = Maps.newHashMap();
    private int humanTotalPoints = 0;
    private int machineTotalPoints = 0;

    public Score getGameScore(String userId, GameResult result) {
        int point = LOST.equals(result) ? -1 : WON.equals(result) ? 1 : 0;

        return getFinalScore(userId, point);
    }

    private Score getFinalScore(String userId, int point) {
        int humanPoints = Math.max(0, point);
        int machinePoints = Math.max(0, point * -1);

        humanTotalPoints += humanPoints;
        machineTotalPoints += machinePoints;
        // Return total human/machine score if user is not provided
        if (isBlank(userId)) {
            return new Score(humanTotalPoints, machineTotalPoints);
        }

        return updateAndGetUserScore(userId, humanPoints, machinePoints);
    }

    private Score updateAndGetUserScore(String userId, int humanPoints, int machinePoints) {
        Score score = scoreMap.get(userId);

        Score newScore;
        if (score == null) {
            newScore = new Score(humanPoints, machinePoints);
        } else {
            newScore = new Score(score.getHumanPoints() + humanPoints, score.getMachinePoints() + machinePoints);
        }
        scoreMap.put(userId, newScore);
        return newScore;
    }

}
