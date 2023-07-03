package co.pixelmc.commands;

import co.pixelmc.IPixelMCDonationsPlugin;
import co.pixelmc.PixelMCDonations;
import co.pixelmc.exceptions.InvalidPerkException;
import co.pixelmc.helpers.PlaceholdersParser;
import co.pixelmc.helpers.TextHelpers;
import co.pixelmc.logic.IDonationsLogic;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PixelMCDonationsCommandExecutor implements CommandExecutor {
    private PixelMCDonations plugin;
    private IDonationsLogic donationsLogic;

    public PixelMCDonationsCommandExecutor(PixelMCDonations pixelMCDonations, IDonationsLogic donationsLogic) {
        plugin = pixelMCDonations;
        this.donationsLogic = donationsLogic;
    }

    /**
     *
     * pixelmcdonations applydonation cbe683e8-f284-4bb9-8ba3-61452abff161 fly 10
     * pixelmcdonations status
     * pixelmcdonations status cbe683e8-f284-4bb9-8ba3-61452abff161
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("status")){
            if (args.length == 1 && sender.hasPermission("co.pixelmc.status"))
                return getStatusSelf(sender, command, label, args);
             else if (args.length == 2 && sender.hasPermission("co.pixelmc.status.others"))
                return getStatusOther(sender, command, label, args);

        } else if(args[0].equalsIgnoreCase("applydonation")
                && args.length == 4
                && (sender.hasPermission("co.pixelmc.applydonation") || sender instanceof ConsoleCommandSender)
        )
            return applyDonation(sender, command, label, args);

        return false;
    }

    private boolean applyDonation(CommandSender sender, Command command, String label, String[] args){
        UUID uuid = UUID.fromString(args[1]);
        String perkName = args[2];
        Double amount = Double.parseDouble(args[3]);

        try {
            donationsLogic.addDonation(uuid, perkName, amount);
            List<String> unparsedCommands = donationsLogic.claimDonation(uuid);

            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(uuid);
            String playerName;
            if (offlinePlayer.hasPlayedBefore())
                playerName = offlinePlayer.getName();
            else
                playerName = uuid.toString();

            PlaceholdersParser placeholdersParser = new PlaceholdersParser();
            placeholdersParser
                    .put(PlaceholdersParser.Keys.PLAYER_NAME, playerName)
                    .put(PlaceholdersParser.Keys.UUID, uuid.toString());

            for (String unparsedCommand: unparsedCommands) {
                String parsedCommand = placeholdersParser.parse(unparsedCommand);
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), parsedCommand);
            }

            return true;
        } catch (InvalidPerkException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean getStatusSelf(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof ConsoleCommandSender)
            return false;

        Player player = (Player) sender;
        UUID targetUuid = player.getUniqueId();
        return getStatus(targetUuid, sender);
    }

    private boolean getStatusOther(CommandSender sender, Command command, String label, String[] args){
        UUID uuid = UUID.fromString(args[1]);
        return getStatus(uuid, sender);
    }

    private boolean getStatus(UUID targetUuid, CommandSender sender){
        Double amountDonated = donationsLogic.getAmountDonated(targetUuid);
        List<String> purchasedPerks = donationsLogic.getPurchasedPerks(targetUuid);

        List<String> msgLines = new ArrayList<>(Arrays.asList(
                "&3&lAmount Donated: $" + amountDonated,
                "&6&lYour acquired perks: "
        ));
        purchasedPerks.forEach(x -> msgLines.add("&5- " + x));

        msgLines.forEach(x -> sender.sendMessage(TextHelpers.replaceCodes(x)));
        return true;
    }
}
