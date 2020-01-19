package com.challenge.game.utils;

import com.challenge.game.model.Hand;
import com.challenge.game.rest.request.GameMoveRequest;

public interface GameMoveTestUtils {

    static GameMoveRequest createRequest(String userId, Hand inputHand) {
        return createRequest(userId, inputHand.name());
    }

    static GameMoveRequest createRequest(String userId, String inputHand) {
        GameMoveRequest request = new GameMoveRequest();
        request.setHand(inputHand);
        request.setUserId(userId);
        return request;
    }
}
