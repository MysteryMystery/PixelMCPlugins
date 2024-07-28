package co.pixelmc.pixelmcdonations.commands.donation;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.repositories.DonationRepository;
import co.pixelmc.pixelmcdonations.repositories.PlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class DonationAddSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "add";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2){
            return false;
        }

        UUID playerUuid = UUID.fromString(args[0]);

        PlayerRepository repository = new PlayerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        Player player = repository.getPlayer(playerUuid);

        if (player == null){
            repository.addPlayer(playerUuid);
            player = repository.getPlayer(playerUuid);
        }

        DonationRepository donationRepository = new DonationRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = donationRepository.addDonation(player.getPlayerId(), Double.parseDouble(args[1]));

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }
}
