package co.pixelmc.pixelmcdonations.commands.server;

import co.pixelmc.pixelmcdonations.PixelMCDonations;
import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import co.pixelmc.pixelmcdonations.repositories.ServerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ServerAddSubCommand extends AbstractSubCommand {
    @Override
    protected String getLabel() {
        return "add";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2){
            return false;
        }

        ServerRepository repository = new ServerRepository(PixelMCDonations.getInstance().getDatabaseOptions());
        boolean success = repository.AddServer(args[0], args[1]);

        sender.sendMessage(success ? "Success!" : "Failure :(");

        return success;
    }
}
