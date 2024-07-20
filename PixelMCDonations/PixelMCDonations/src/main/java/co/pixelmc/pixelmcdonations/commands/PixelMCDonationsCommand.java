package co.pixelmc.pixelmcdonations.commands;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class PixelMCDonationsCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
          return false;
        }

        if (args[0].equalsIgnoreCase("server")){
            if(args[1].equalsIgnoreCase("add") && args.length == 4) {
                return addServer(args[2], args[3]);
            } else if (args[1].equalsIgnoreCase("list")) {
                return listServers(sender);
            }
        }


        return false;
    }

    private boolean addServer(String name, String description){
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        return repository.AddServer(name, description);
    }

    private boolean listServers(CommandSender sender){
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Server> servers = repository.GetServers();
        for (Server server: servers) {
            sender.sendMessage(server.getId() + " | " + server.getServerName() + " | " + server.getServerDescription());
        }
        return true;
    }
}