package co.pixelmc.pixelmcdonations.commands.server;

import co.pixelmc.pixelmcdonations.commands.AbstractSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ServerSubCommand extends AbstractSubCommand {

    public ServerSubCommand(){
        this.addSubcommand(new ServerAddSubCommand());
        this.addSubcommand(new ServerListSubCommand());
    }

    @Override
    protected String getLabel() {
        return "server";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
