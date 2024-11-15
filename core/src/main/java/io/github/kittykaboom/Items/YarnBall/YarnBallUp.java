package io.github.kittykaboom.Items.YarnBall;

import io.github.kittykaboom.Players.CatPlayer;

public class YarnBallUp {
    public void applyEffect(CatPlayer player) {
        player.increaseMaxYarnBalls();
    }
}
