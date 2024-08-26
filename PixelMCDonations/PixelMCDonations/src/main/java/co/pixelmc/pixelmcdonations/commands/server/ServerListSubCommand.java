package co.pixelmc.pixelmcdonations.commands.server;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.models.Server;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class ServerListSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "list";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        List<Server> servers = repository.GetServers();

        List<String> lines = new ArrayList<>();
        Formatter formatter = new Formatter();

        formatter.format("%-10s | %-20s | %-30s%n", "ID", "Server Name", "Server Description");
        lines.add(formatter.toString());

        for (Server server: servers) {
            formatter = new Formatter();
            formatter.format("%-10d | %-20s | %-30s%n", server.getId(), server.getServerName(), server.getServerDescription());
            lines.add(formatter.toString());
        }

        formatter.close();

        sender.sendMessage(lines.toArray(String[]::new));

        return true;
    }
}
