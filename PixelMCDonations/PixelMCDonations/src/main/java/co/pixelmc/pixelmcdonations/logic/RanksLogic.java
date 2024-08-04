package co.pixelmc.pixelmcdonations.logic;

import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.stringops.PlaceHolderParser;
import org.bukkit.Bukkit;

public class RanksLogic {
    public boolean claimRanks(Player player, Rank ... ranks){
        PlaceHolderParser placeholders = PlaceHolderParser
                .builder()
                .replaceUUID(player.getPlayerUuid())
                .build();

        for (Rank rank : ranks) {
            String command = placeholders.parse(rank.getCommand());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        }

        return true;
    }
}
