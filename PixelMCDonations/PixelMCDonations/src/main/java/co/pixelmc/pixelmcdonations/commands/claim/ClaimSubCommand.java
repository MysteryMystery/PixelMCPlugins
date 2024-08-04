package co.pixelmc.pixelmcdonations.commands.claim;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.logic.RanksLogic;
import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.repositories.DonationRepository;
import co.pixelmc.pixelmcdonations.repositories.PlayerRepository;
import co.pixelmc.pixelmcdonations.stringops.MinecraftColour;
import co.pixelmc.pixelmcdonations.stringops.MinecraftTextBuilder;
import co.pixelmc.pixelmcdonations.stringops.MinecraftTextBuilderLine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ClaimSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "claim";
    }

    private void sendSuccessMsg(CommandSender sender, Rank[] ranks){
        MinecraftTextBuilder builder = new MinecraftTextBuilder()
                .addLine(MinecraftTextBuilderLine.of(MinecraftColour.GOLD, "You have claimed the following ranks: "));

        Arrays.stream(ranks)
                .forEach(x ->
                        builder.addLine(
                                MinecraftTextBuilderLine.of(MinecraftColour.AQUA, " - " + x.getRankName())
                        )
                );

        String[] msg = builder.getLines();
        sender.sendMessage(msg);
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

        Rank[] ranks = donationRepository.claimAvailableRanks(player).toArray(Rank[]::new);

        RanksLogic ranksLogic = new RanksLogic();
        ranksLogic.claimRanks(player, ranks);

        sendSuccessMsg(sender, ranks);

        return true;
    }
}
