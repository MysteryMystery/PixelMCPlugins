package co.pixelmc.pixelmcdonations.logic;

import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.stringops.PlaceHolderStringHandler;
import org.bukkit.Bukkit;

public class RanksLogic {
    public boolean claimRanks(Player player, Rank ... ranks){
        PlaceHolderStringHandler placeholders = new PlaceHolderStringHandler()
                .replaceUUID(player.getPlayerUuid());

        for (Rank rank : ranks) {
            String command = placeholders.getString(rank.getCommand());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        }

        return true;
    }
}
