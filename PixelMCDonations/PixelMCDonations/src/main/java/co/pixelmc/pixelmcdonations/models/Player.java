package co.pixelmc.pixelmcdonations.models;

import java.util.UUID;

public class Player {
    private int playerId;
    private UUID playerUuid;

    public Player(int playerId, UUID playerUuid) {
        this.playerId = playerId;
        this.playerUuid = playerUuid;
    }

    public int getPlayerId() {
        return playerId;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }
}
