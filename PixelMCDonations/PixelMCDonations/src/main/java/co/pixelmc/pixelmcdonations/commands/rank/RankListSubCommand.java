package co.pixelmc.pixelmcdonations.commands.rank;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.repositories.RankRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RankListSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "list";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        RankRepository repository = new RankRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Rank> ranks = repository.getRanks();
        for (Rank rank: ranks) {
            sender.sendMessage(rank.getRankId() + " | " + rank.getRankName() + " | " + rank.getDonationThreshold());
        }
        return true;
    }
}
