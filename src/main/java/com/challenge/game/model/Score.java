package com.challenge.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {

    private int humanPoints;
    private int machinePoints;
}
