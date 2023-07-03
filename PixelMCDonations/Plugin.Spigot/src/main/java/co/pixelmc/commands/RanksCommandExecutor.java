package co.pixelmc.commands;

import co.pixelmc.IPixelMCDonationsPlugin;
import co.pixelmc.PixelMCDonations;
import co.pixelmc.logic.IDonationsLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import co.pixelmc.helpers.TextHelpers;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RanksCommandExecutor implements CommandExecutor {
    private IPixelMCDonationsPlugin plugin;
    private IDonationsLogic donationsLogic;

    public RanksCommandExecutor(IPixelMCDonationsPlugin pixelMCDonations, IDonationsLogic donationsLogic){
        plugin = pixelMCDonations;
        this.donationsLogic = donationsLogic;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> ranks = donationsLogic.getFormattedRanks();

        List<String> msgLines = new ArrayList<>(Arrays.asList(
                "&3&lWhen your &6&ltotal exceeds each rank&3&l, you gain it!",
                "&3&lHere's a sneak peak of an &3&litem from each kit!",
                "&7-------------------------------------------------------"
        ));
        msgLines.addAll(ranks);
        msgLines.add("&7-------------------------------------------------------");

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID targetUuid = player.getUniqueId();
            Double amountDonated = donationsLogic.getAmountDonated(targetUuid);
            msgLines.add("&b&lYour current total: &a&l$" + amountDonated);
        }

        msgLines.forEach(x -> sender.sendMessage(TextHelpers.replaceCodes(x)));
        return true;
    }
}
