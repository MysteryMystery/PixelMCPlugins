package co.pixelmc.pixelmcdonations.commands.claim;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.repositories.DonationRepository;
import co.pixelmc.pixelmcdonations.repositories.PlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ClaimSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "claim";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (! (sender instanceof org.bukkit.entity.Player bukkitPlayer))
            return false;

        DonationRepository donationRepository = new DonationRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        PlayerRepository playerRepository = new PlayerRepository(PixelMCDonations.getInstance().getDatabaseOptions());

        Player player = playerRepository.getPlayer(bukkitPlayer.getUniqueId());
        if (player == null)
            return false;

        donationRepository.claimAvailableRanks(player);

        return true;
    }
}
