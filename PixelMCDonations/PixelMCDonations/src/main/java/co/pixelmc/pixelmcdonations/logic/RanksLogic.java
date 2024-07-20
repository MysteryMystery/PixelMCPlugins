package co.pixelmc.pixelmcdonations.logic;

import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import org.bukkit.Bukkit;

public class RanksLogic {
    public boolean claimRanks(Player player, Rank ... ranks){
        for (Rank rank : ranks) {
            //TODO placeholders
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), rank.getCommand());
        }

        return true;
    }
}
