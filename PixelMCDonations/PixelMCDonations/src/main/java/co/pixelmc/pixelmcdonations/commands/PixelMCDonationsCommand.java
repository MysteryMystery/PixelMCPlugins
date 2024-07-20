package co.pixelmc.pixelmcdonations.commands;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.models.Player;
import co.pixelmc.pixelmcdonations.models.Rank;
import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.repositories.DonationRepository;
import co.pixelmc.pixelmcdonations.repositories.PlayerRepository;
import co.pixelmc.pixelmcdonations.repositories.RankRepository;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class PixelMCDonationsCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    //TODO abstract commands into their own subcommand handlers
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
          return false;
        }

        // server management
        if (args[0].equalsIgnoreCase("server")){
            if(args[1].equalsIgnoreCase("add") && args.length == 4) {
                return addServer(sender, args[2], args[3]);
            } else if (args[1].equalsIgnoreCase("list")) {
                return listServers(sender);
            }
        }

        // rank management
        if(args[0].equalsIgnoreCase(("rank"))){
            if (args[1].equalsIgnoreCase("add") && args.length == 6){
                return addRank(sender, args[2], Integer.parseInt(args[3]), Double.parseDouble(args[4]), args[5]);
            } else if (args[1].equalsIgnoreCase("list")){
                return listRanks(sender);
            }
        }

        // donation management
        if(args[0].equalsIgnoreCase(("donation"))){
            if (args[1].equalsIgnoreCase("add") && args.length == 4){
                return addDonation(sender, args[2], Double.parseDouble(args[3]));
            }
        }

        if(args[0].equalsIgnoreCase(("claim"))){
            return claimRanks(sender);
        }

        return false;
    }

    private boolean addServer(CommandSender sender, String name, String description){
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = repository.AddServer(name, description);

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }

    private boolean listServers(CommandSender sender){
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Server> servers = repository.GetServers();
        for (Server server: servers) {
            sender.sendMessage(server.getId() + " | " + server.getServerName() + " | " + server.getServerDescription());
        }
        return true;
    }

    private boolean addRank(CommandSender sender, String name, int serverId, double amount, String command){
        RankRepository repository = new RankRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = repository.addRank(name, serverId, amount, command);

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }

    private boolean listRanks(CommandSender sender){
        RankRepository repository = new RankRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Rank> ranks = repository.getRanks();
        for (Rank rank: ranks) {
            sender.sendMessage(rank.getRankId() + " | " + rank.getRankName() + " | " + rank.getDonationThreshold());
        }
        return true;
    }

    private boolean addDonation(CommandSender sender, String playerUuidStr, double amount){
        UUID playerUuid = UUID.fromString(playerUuidStr);

        PlayerRepository repository = new PlayerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Player> playerLst = repository.getPlayer(playerUuid);
        Player player;

        if (playerLst.size() > 0){
            player = playerLst.get(0);
        } else {
            repository.addPlayer(playerUuid);
            player = repository.getPlayer(playerUuid).get(0);
        }

        DonationRepository donationRepository = new DonationRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = donationRepository.addDonation(player.getPlayerId(), amount);

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }

    private boolean claimRanks(CommandSender sender){
        if (! (sender instanceof org.bukkit.entity.Player bukkitPlayer))
            return false;

        DonationRepository donationRepository = new DonationRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        PlayerRepository playerRepository = new PlayerRepository(PixelMCDonations.getInstance().getDatabaseOptions());

        List<Player> playerLst = playerRepository.getPlayer(bukkitPlayer.getUniqueId());
        if (playerLst.size() == 0)
            return false;

        Player player = playerLst.get(0);
        donationRepository.claimAvailableRanks(player);

        return true;
    }
}