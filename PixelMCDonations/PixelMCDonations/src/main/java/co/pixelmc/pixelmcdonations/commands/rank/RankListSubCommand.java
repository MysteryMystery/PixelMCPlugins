package co.pixelmc.pixelmcdonations.commands.rank;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.repositories.RankRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Formatter;
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

        List<String> lines = new ArrayList<>();
        Formatter formatter = new Formatter();

        formatter.format("%-10s | %-20s | %-30s%n", "ID", "Rank Name", "Donation Threshold");
        lines.add(formatter.toString());

        for (Rank rank: ranks) {
            formatter = new Formatter();
            formatter.format("%-10d | %-20s | %-30s%n", rank.getRankId() + " | " + rank.getRankName() + " | " + Double.toString(rank.getDonationThreshold()));
            lines.add(formatter.toString());
        }

        formatter.close();

        sender.sendMessage(lines.toArray(String[]::new));

        return true;
    }
}
