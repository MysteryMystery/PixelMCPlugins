package co.pixelmc.pixelmcdonations.commands.server;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ServerListSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "list";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Server> servers = repository.GetServers();
        for (Server server: servers) {
            sender.sendMessage(server.getId() + " | " + server.getServerName() + " | " + server.getServerDescription());
        }
        return true;
    }
}
